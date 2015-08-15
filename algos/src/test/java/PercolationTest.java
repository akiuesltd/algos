import junit.framework.Assert;
import org.junit.Test;

/**
 * @author aks
 * @since 15/08/15
 */
public class PercolationTest {

    @Test
    public void testInput() {
        In in = new In(this.getClass().getResource("percolation/input20.txt"));
        int n = in.readInt();
        int[] all = in.readAllInts();
        Percolation percolation = new Percolation(n);
        for (int i = 0; i < all.length; ) {
            if (all[i] == 2 && all[i+1] == 1) {
                percolation.open(all[i], all[i + 1]);
            } else {
                percolation.open(all[i], all[i + 1]);
            }
            Assert.assertTrue(percolation.isOpen(all[i], all[i + 1]));
            if (all[i] == 18 && all[i+1] == 1) {
                System.out.println("open? " + percolation.isOpen(all[i], all[i + 1]));
                System.out.println("full?" + percolation.isFull(all[i], all[i + 1]));
                printGrid(percolation, n);
                break;
            }
            i = i + 2;
        }
    }

    private void printGrid(Percolation percolation, int n) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                System.out.print(marker(percolation, i, j) + " ");
            }
            System.out.println();
        }
    }

    private String marker(Percolation percolation, int i, int j) {
        if (percolation.isOpen(i, j)) {
            return "x";
        } else {
            return " ";
        }
    }
}
