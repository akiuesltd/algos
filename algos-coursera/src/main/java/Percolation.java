import java.lang.IllegalArgumentException;import java.lang.String;import java.lang.System; /**
 * n^2 + 2 nodes. i=0 is a special node that serves as root of top row,
 * i=n^2+1 is special node that serves as root of bottom row.
 * These nodes are mapped to a one-dimensional array used by WeightedQuickUnionUF.
 * A node is full if it's connected to top.
 * Grid percolates if bottom is connected to top.
 *
 * @author aks
 * @since 15/08/15
 */
public class Percolation {
    private final WeightedQuickUnionUF grid;

    // size of matrix (n x n)
    private final int n;

    // n^2 grid, plus special nodes for top & bottom.
    // can be calculated on the fly, but keeping for ease and readability.
    private final int totalNodes;

    // WeightedQuickUnionUF doesn't provide a way to check if a node is open.
    // We maintain that separately here.
    private final int[] openStatus;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        totalNodes = n * n + 2;
        grid = new WeightedQuickUnionUF(totalNodes);
        openStatus = new int[totalNodes];

        // the special nodes are marked open upfront
        // so that other nodes can connect to them.
        openStatus[0] = 1;
        openStatus[totalNodes - 1] = 1;
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(2);
        System.out.println(percolation.isOpen(1, 1));
        System.out.println(percolation.isOpen(1, 2));
        System.out.println(percolation.isOpen(2, 1));
        System.out.println(percolation.isOpen(2, 2));
        System.out.println(percolation.isFull(2, 2));
        System.out.println("---");
        percolation.open(1, 1);
        percolation.open(2, 2);
        System.out.println(percolation.isOpen(1, 1));
        System.out.println(percolation.isOpen(2, 2));
        System.out.println(percolation.isFull(2, 2));
        System.out.println(percolation.percolates());
        System.out.println("---");
        percolation.open(1, 2);
        System.out.println(percolation.isFull(2, 2));
        System.out.println(percolation.percolates());
    }

    public void open(int i, int j) {
        validate(i, j);
        if (isOpen(i, j)) {
            return;
        }

        // set open status
        openStatus[getPosition(i, j)] = 1;

        // connect with open neighbours
        connectUp(i, j);
        connectDown(i, j);
        connectLeft(i, j);
        connectRight(i, j);
    }

    public boolean isOpen(int i, int j) {
        validate(i, j);

        return isOpen(getPosition(i, j));
    }

    public boolean isFull(int i, int j) {
        validate(i, j);
        return isOpen(i, j) && grid.connected(0, getPosition(i, j));
    }

    public boolean percolates() {
        return grid.connected(0, totalNodes - 1);
    }

    // get position of node(i, j) on the one dimensional array.
    private int getPosition(int i, int j) {
        if (i < 1) { //before the first row
            return 0;
        } else if (i > n) { // after the last row
            return totalNodes - 1;
        }

        return n * (i - 1) + (j - 1) + 1;
    }

    private void validate(int i, int j) {
        if (i < 1 || i > n || j < 1 || j > n) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isOpen(int x) {
        return openStatus[x] == 1;
    }

    private void connectUp(int i, int j) {
        int up = getPosition(i - 1, j);
        if (isOpen(up)) {
            grid.union(up, getPosition(i, j));
        }
    }

    private void connectDown(int i, int j) {
        int down = getPosition(i + 1, j);
        if (isOpen(down)) {
            grid.union(down, getPosition(i, j));
        }
    }

    private void connectLeft(int i, int j) {
        if (j > 1) {
            int left = getPosition(i, j - 1);
            if (isOpen(left)) {
                grid.union(left, getPosition(i, j));
            }
        }
    }

    private void connectRight(int i, int j) {
        if (j < n) {
            int right = getPosition(i, j + 1);
            if (isOpen(right)) {
                grid.union(right, getPosition(i, j));
            }
        }
    }


}
