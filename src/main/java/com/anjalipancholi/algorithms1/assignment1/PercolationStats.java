package com.anjalipancholi.algorithms1.assignment1;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int counts;
    private final double[] trialResult;


    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        counts = trials;
        trialResult = new double[counts];

        for (int i = 0; i < counts; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                p.open(row, col);
            }
            int openSites = p.numberOfOpenSites();
            double result = (double) openSites / (n * n);
            trialResult[i] = result;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trialResult);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trialResult);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(counts));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(counts));
    }

    // test client (described below)
    public static void main(String[] args) {
    }
}
