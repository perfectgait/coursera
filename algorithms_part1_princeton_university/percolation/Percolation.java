/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *  Dependencies: WeightedQuickUnionUF.java StdOut.java
 *
 *  Implements percolation grid
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private int virtualTopIndex;
    private int virtualBottomIndex;
    private int openCount;
    private boolean[] openSites;
    private WeightedQuickUnionUF union;
    private WeightedQuickUnionUF unionNotUsingVirtualBottom;

    /**
     * @param n grid size
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        this.openCount = 0;
        this.virtualTopIndex = n * n;
        this.virtualBottomIndex = n * n + 1;
        this.union = new WeightedQuickUnionUF(n * n + 2);
        this.unionNotUsingVirtualBottom = new WeightedQuickUnionUF(n * n + 1);
        this.openSites = new boolean[n * n + 2];
        this.openSites[virtualTopIndex] = true;
        this.openSites[virtualBottomIndex] = true;

        for (int i = 0; i < n * n; i++) {
            this.openSites[i] = false;
        }
    }

    /**
     * Open a site in the system
     *
     * @param row row in the grid
     * @param col column in the grid
     */
    public void open(int row, int col) {
        if (!this.indexesAreValid(row, col)) {
            throw new IllegalArgumentException();
        }

        int flattenedIndex = this.xyTo1D(row, col);

        if (!openSites[flattenedIndex]) {
            this.openSites[flattenedIndex] = true;
            this.openCount++;

            // Union with the virtual top if the top row is referenced
            if (row == 1) {
                this.union.union(flattenedIndex, this.virtualTopIndex);
                this.unionNotUsingVirtualBottom.union(flattenedIndex, this.virtualTopIndex);
            }

            // Union with the virtual bottom if the bottom row is referenced
            if (row == this.n) {
                this.union.union(flattenedIndex, this.virtualBottomIndex);
            }

            if (this.indexesAreValid(row - 1, col) && this.isOpen(row - 1, col)) {
                this.union.union(flattenedIndex, this.xyTo1D(row - 1, col));
                this.unionNotUsingVirtualBottom.union(flattenedIndex, this.xyTo1D(row - 1, col));
            }

            if (this.indexesAreValid(row, col - 1) && this.isOpen(row, col - 1)) {
                this.union.union(flattenedIndex, this.xyTo1D(row, col - 1));
                this.unionNotUsingVirtualBottom.union(flattenedIndex, this.xyTo1D(row, col - 1));
            }

            if (this.indexesAreValid(row, col + 1) && this.isOpen(row, col + 1)) {
                this.union.union(flattenedIndex, this.xyTo1D(row, col + 1));
                this.unionNotUsingVirtualBottom.union(flattenedIndex, this.xyTo1D(row, col + 1));
            }

            if (this.indexesAreValid(row + 1, col) && this.isOpen(row + 1, col)) {
                this.union.union(flattenedIndex, this.xyTo1D(row + 1, col));
                this.unionNotUsingVirtualBottom.union(flattenedIndex, this.xyTo1D(row + 1, col));
            }
        }
    }

    /**
     * Determine if a site in the system is open
     *
     * @param row row in the grid
     * @param col column in the grid
     * @return whether or not the site is open
     */
    public boolean isOpen(int row, int col) {
        if (!this.indexesAreValid(row, col)) {
            throw new IllegalArgumentException();
        }

        return openSites[this.xyTo1D(row, col)];
    }

    /**
     * Determine if a site in the system is full.  A full site is one which is connected to an open site in the top row.
     *
     * @param row row in the grid
     * @param col column in the grid
     * @return whether or not the site is full
     */
    public boolean isFull(int row, int col) {
        if (!this.indexesAreValid(row, col)) {
            throw new IllegalArgumentException();
        }

        return this.isOpen(row, col)
            && this.unionNotUsingVirtualBottom.connected(this.xyTo1D(row, col), this.virtualTopIndex);
    }

    /**
     * @return the number of open sites
     */
    public int numberOfOpenSites() {
        return this.openCount;
    }

    /**
     * Determine if the system percolates.  The system is said to percolate if there is an open site in the bottom row
     * which is connected to an open site in the top row.
     *
     * @return whether or not the system percolates
     */
    public boolean percolates() {
        return this.union.connected(this.virtualTopIndex, this.virtualBottomIndex);
    }

    /**
     * Convert a 2D coordinate to a 1D representation
     *
     * @param row row in the grid
     * @param column column in the grid
     * @return a 1D representation of the 2D coordinate
     */
    private int xyTo1D(int row, int column) {
        if (!this.indexesAreValid(row, column)) {
            throw new IllegalArgumentException();
        }

        return this.n * (row - 1) + (column - 1);
    }

    /**
     * Determine if the coordinate is valid
     *
     * @param row row in the grid
     * @param column column in the grid
     * @return whether or not the coordinate is valid
     */
    private boolean indexesAreValid(int row, int column) {
        return row >= 1 && column >= 1 && row <= this.n && column <= this.n;
    }

    public static void main(String[] args) {
        // Tests can go here
    }
}
