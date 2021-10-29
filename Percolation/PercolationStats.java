import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int trials;
    private final double threshold;
    private final double sharpness;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        this.trials = trials;
        double[] keys = new double[trials];
        int[] blockSites = new int[n * n];
        for (int i = 0; i < n * n; i++) blockSites[i] = i;

        Percolation percolation;
        for (int i = 0; i < trials; i++) {
            StdRandom.shuffle(blockSites);
            percolation = new Percolation(n);
            for (int j = 0; j < n * n; j++) {
                percolation.open(blockSites[j] / n + 1, blockSites[j] % n + 1);
                if (percolation.percolates()) break;
            }
            keys[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
        threshold = StdStats.mean(keys);
        sharpness = StdStats.stddev(keys);
    }

    public double mean() {
        return threshold;
    }

    public double stddev() {
        return sharpness;
    }

    public double confidenceLo() {
        return threshold - CONFIDENCE_95 * sharpness / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return threshold + CONFIDENCE_95 * sharpness / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, t);
        StdOut.printf("%s%f\n", "mean                    = ", percolationStats.mean());
        StdOut.printf("%s%f\n", "stddev                  = ", percolationStats.stddev());
        StdOut.printf("%s%f%s%f%s\n", "95% confidence interval = [", percolationStats.confidenceLo(), ", ", percolationStats.confidenceHi(), "]");
    }
}
