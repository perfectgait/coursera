/******************************************************************************
 *  Compilation:  javac PercolationStats.java
 *  Execution:    java PercolationStats <n> <T> (Performs T experiments on an nxn grid)
 *  Dependencies: StdRandom.java StdStats.java StdOut.java
 *
 *  Implements experiments on percolation grid
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials;
    private double mean;
    private double stdDev;

    /**
     * Perform trials on a grid of size n x n
     *
     * @param n grid size
     * @param trials number of trials to perform
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be > 0");
        }

        double[] thresholds = new double[trials];
        this.trials = trials;
        this.mean = 0;
        this.stdDev = 0;

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            int numberOfOpenSites = 0;

            while (!percolation.percolates()) {
                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);

                if (!percolation.isOpen(randomRow, randomCol)) {
                    percolation.open(randomRow, randomCol);
                    numberOfOpenSites++;
                }
            }

            thresholds[i] = (double) numberOfOpenSites / (n * n);
        }

        this.mean = StdStats.mean(thresholds);
        this.stdDev = StdStats.stddev(thresholds);
    }

    /**
     * @return the mean
     */
    public double mean() {
        return this.mean;
    }

    /**
     * @return the standard deviation
     */
    public double stddev() {
        return this.stdDev;
    }

    /**
     * @return lower bound of the 95% confidence interval
     */
    public double confidenceLo() {
        return this.mean() - (1.96 * this.stddev() / Math.sqrt(this.trials));
    }

    /**
     * @return upper bound of the 95% confidence interval
     */
    public double confidenceHi() {
        return this.mean() + (1.96 * this.stddev() / Math.sqrt(this.trials));
    }

    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println(
            "95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]"
        );
    }
}
