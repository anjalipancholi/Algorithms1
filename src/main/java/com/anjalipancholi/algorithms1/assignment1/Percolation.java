package com.anjalipancholi.algorithms1.assignment1;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] matrix;
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final WeightedQuickUnionUF weightedQuickUnionUF1;
    private final int size;
    private final int virtualTop;
    private final int virtualBottom;
    private int openSites;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Illegal Argument");
        size = n;
        int matrixSize = n * n;
        matrix = new boolean[size][size];
        weightedQuickUnionUF = new WeightedQuickUnionUF(matrixSize + 2); // includes virtual top bottom
        weightedQuickUnionUF1 = new WeightedQuickUnionUF(matrixSize + 1); // includes virtual top
        virtualBottom = matrixSize + 1;
        virtualTop = matrixSize;
        openSites = 0;

    }

    // Test: open site (row, col) if it is not open already
    public void open(int row, int col) {
        if(row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException("Index out of bound");
        }

        int i = row - 1;
        int j = col - 1;
        //convert to 1D array
        int index = array(row, col) - 1;

        if (isOpen(row, col)) {
            return;
        }

        matrix[i][j] = true;
        openSites++;

        if (row == 1) {  // Top Row
            weightedQuickUnionUF.union(virtualTop, index);
            weightedQuickUnionUF1.union(virtualTop, index);
        }

        if (row == size) {  // Bottom Row
            weightedQuickUnionUF.union(virtualBottom, index);
        }

        // Left
        if (outbounds(row, col - 1) && isOpen(row, col - 1)) {
            weightedQuickUnionUF.union(index, array(row, col - 1) - 1);
            weightedQuickUnionUF1.union(index, array(row, col - 1) - 1);
        }

        // Right
        if (outbounds(row, col + 1) && isOpen(row, col + 1)) {
            weightedQuickUnionUF.union(index, array(row, col + 1) - 1);
            weightedQuickUnionUF1.union(index, array(row, col + 1) - 1);
        }

        // Open Up
        if (outbounds(row - 1, col) && isOpen(row - 1, col)) {
            weightedQuickUnionUF.union(index, array(row - 1, col) - 1);
            weightedQuickUnionUF1.union(index, array(row - 1, col) - 1);
        }

        // Open Down
        if (outbounds(row + 1, col) && isOpen(row + 1, col)) {
            weightedQuickUnionUF.union(index, array(row + 1, col) - 1);
            weightedQuickUnionUF1.union(index, array(row + 1, col) - 1);
        }

    }

    // Test: is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBound(row, col);
        return matrix[row - 1][col - 1];
    }

    // Test: is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkBound(row, col);
        return weightedQuickUnionUF1.find(virtualTop) == weightedQuickUnionUF.find(array(row, col) - 1);
    }

    // Test: number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // Test: does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.find(virtualTop) == weightedQuickUnionUF.find(virtualBottom);
    }

    // test client
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);

        Percolation percolation = new Percolation(size);
        int argCount = args.length;
        for (int i = 1; argCount >= 2; i += 2) {
            int row = Integer.parseInt(args[i]);
            int col = Integer.parseInt(args[i + 1]);
            StdOut.printf("Adding row: %d  col: %d %n", row, col);
            percolation.open(row, col);
            if (percolation.percolates()) {
                StdOut.printf("%nThe System percolates %n");
            }
            argCount -= 2;
        }
        if (!percolation.percolates()) {
            StdOut.print("Does not percolate %n");
        }
    }

    private int array(int row, int col) {
        return size * (row - 1) + col;
    }

    private void checkBound(int row, int col) {
        if (!outbounds(row, col)) {
            throw new IllegalArgumentException("Index out of bounds");
        }
    }

    private boolean outbounds(int row, int col) {
        int shiftRow = row - 1;
        int shiftCol = col - 1;
        return (shiftRow >= 0 && shiftCol >= 0 && shiftRow < size && shiftCol < size);
    }
}
