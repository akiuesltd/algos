/**
 * @author aks
 * @since 15/08/15
 */
public class PercolationStats {

    private final double[] thresholds;

    public PercolationStats(int n, int t) {
        this.thresholds = new double[t];

        // execute findThreshold() t times, and collect results
        for (int i = 0; i < t; i++) {
            int threshold = findThreshold(n);
            thresholds[i] = (double) threshold / (n * n);
//            System.out.println("Run: " + i + ",  threshold: " + threshold + ", double: " + thresholds[i]);
        }
    }

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }

    private int findThreshold(int n) {
        Percolation percolation = new Percolation(n);
        int opened = 0;
        while (!percolation.percolates() && opened < n * n) {
            // +1 on x & y because the stupid assignment uses 1 indexed arrays in test cases.
            int x = StdRandom.uniform(n) + 1;
            int y = StdRandom.uniform(n) + 1;
            if (!percolation.isOpen(x, y)) {
                percolation.open(x, y);
                opened++;
            }
        }
        return opened;
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(thresholds.length));
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(thresholds.length));
    }
}
