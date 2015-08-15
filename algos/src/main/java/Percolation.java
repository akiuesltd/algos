/**
 * n^2 + 2 nodes. i=0 is a special node that serves as root of top row,
 * i=n^2+1 is special node that serves as root of bottom row.
 * A node is full if it's connected to top.
 * Grid percolates if bottom is connected to top.
 *
 * @author aks
 * @since 15/08/15
 */
public class Percolation {
    private final WeightedQuickUnionUF grid;
    private final int n;
    private final int totalNodes;
    private final int[] openStatus;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        totalNodes = n * n + 2;
        // n^2 grid, plus special nodes for top & bottom.
        grid = new WeightedQuickUnionUF(totalNodes);
        openStatus = new int[totalNodes];
        openStatus[0] = openStatus[totalNodes - 1] = 1;
    }

    public void open(int i, int j) {
        validate(i, j);
        // find all open neighbours and connect to them.
        if (isOpen(i, j)) {
            return;
        }

        // set open status
        openStatus[getPosition(i, j)] = 1;

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
        return isOpen(i, j) && grid.find(getPosition(i, j)) == 0;
    }

    public boolean percolates() {
        return grid.find(totalNodes - 1) == 0;
    }

    private int getPosition(int i, int j) {
        if (i < 0) { //before the first row
            return 0;
        } else if (i >= n) { // after the last row
            return totalNodes - 1;
        }

        return n * i + j + 1;
    }

    private void validate(int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n) {
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
        if (j > 0) {
            int left = getPosition(i, j - 1);
            if (isOpen(left)) {
                grid.union(left, getPosition(i, j));
            }
        }
    }

    private void connectRight(int i, int j) {
        if (j < n - 1) {
            int right = getPosition(i, j + 1);
            if (isOpen(right)) {
                grid.union(right, getPosition(i, j));
            }
        }
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(2);
        System.out.println(percolation.isOpen(0, 0));
        System.out.println(percolation.isOpen(0, 1));
        System.out.println(percolation.isOpen(1, 0));
        System.out.println(percolation.isOpen(1, 1));
        System.out.println(percolation.isFull(1, 1));
        System.out.println("---");
        percolation.open(0, 0);
        percolation.open(1, 1);
        System.out.println(percolation.isOpen(0, 0));
        System.out.println(percolation.isOpen(1, 1));
        System.out.println(percolation.isFull(1, 1));
        System.out.println(percolation.percolates());
        System.out.println("---");
        percolation.open(0, 1);
        System.out.println(percolation.isFull(1, 1));
        System.out.println(percolation.percolates());
    }

}
