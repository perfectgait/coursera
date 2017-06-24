/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *  Dependencies: StdDraw.java StdOut.java
 *
 *  Implements percolation grid
 ******************************************************************************/
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private int virtualTopIndex;
    private int virtualBottomIndex;
    private int openCount;
    private boolean[] openSites;
    private WeightedQuickUnionUF union;

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
        this.openSites = new boolean[n * n + 2];

        java.util.Arrays.fill(this.openSites, false);

        this.openSites[virtualTopIndex] = true;
        this.openSites[virtualBottomIndex] = true;
    }

    /**
     * Open a site in the system
     *
     * @param row row in the grid
     * @param col column in the grid
     */
    public void open(int row, int col) {
        if (!this.indexesAreValid(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        int flattenedIndex = this.xyTo1D(row, col);

        if (!openSites[flattenedIndex]) {
            openSites[flattenedIndex] = true;
            openCount++;

            // Union with the virtual top if the top row is referenced
            if (row == 0) {
                this.union.union(flattenedIndex, this.virtualTopIndex);
            }

            // Union with the virtual bottom if the bottom row is referenced
            if (row == n - 1) {
                this.union.union(flattenedIndex, this.virtualBottomIndex);
            }

            if (this.indexesAreValid(row - 1, col - 1) && this.isOpen(row - 1, col - 1)) {
                this.union.union(flattenedIndex, this.xyTo1D(row - 1, col - 1));
            }

            if (this.indexesAreValid(row - 1, col) && this.isOpen(row - 1, col)) {
                this.union.union(flattenedIndex, this.xyTo1D(row - 1, col));
            }

            if (this.indexesAreValid(row - 1, col + 1) && this.isOpen(row - 1, col + 1)) {
                this.union.union(flattenedIndex, this.xyTo1D(row - 1, col + 1));
            }

            if (this.indexesAreValid(row, col - 1) && this.isOpen(row, col - 1)) {
                this.union.union(flattenedIndex, this.xyTo1D(row, col - 1));
            }

            if (this.indexesAreValid(row, col + 1) && this.isOpen(row, col + 1)) {
                this.union.union(flattenedIndex, this.xyTo1D(row, col + 1));
            }

            if (this.indexesAreValid(row + 1, col - 1) && this.isOpen(row + 1, col - 1)) {
                this.union.union(flattenedIndex, this.xyTo1D(row + 1, col - 1));
            }

            if (this.indexesAreValid(row + 1, col) && this.isOpen(row + 1, col)) {
                this.union.union(flattenedIndex, this.xyTo1D(row + 1, col));
            }

            if (this.indexesAreValid(row + 1, col + 1) && this.isOpen(row + 1, col + 1)) {
                this.union.union(flattenedIndex, this.xyTo1D(row + 1, col + 1));
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
            throw new IndexOutOfBoundsException();
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
            throw new IndexOutOfBoundsException();
        }

        return this.isOpen(row, col) && this.union.connected(this.xyTo1D(row, col), this.virtualTopIndex);
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
            throw new IndexOutOfBoundsException();
        }

        return this.n * row + column;
    }

    /**
     * Determine if the coordinate is valid
     *
     * @param row row in the grid
     * @param column column in the grid
     * @return whether or not the coordinate is valid
     */
    private boolean indexesAreValid(int row, int column) {
        return row >= 0 && column >= 0 && row < this.n && column < this.n;
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);

        // Tests for indexesAreValid
        if (percolation.indexesAreValid(0, 0)) {
            StdOut.println("The index (0, 0) is valid");
        }
        else {
            StdOut.println("The index (0, 0) is not valid and it should be");
        }

        if (percolation.indexesAreValid(2, 2)) {
            StdOut.println("The index (2, 2) is valid");
        }
        else {
            StdOut.println("The index (2, 2) is not valid and it should be");
        }

        if (percolation.indexesAreValid(4, 4)) {
            StdOut.println("The index (4, 4) is valid");
        }
        else {
            StdOut.println("The index (4, 4) is not valid and it should be");
        }

        if (percolation.indexesAreValid(-1, 0)) {
            StdOut.println("The index (-1, 0) is valid and it should not be");
        }
        else {
            StdOut.println("The index (-1, 0) is not valid");
        }

        if (percolation.indexesAreValid(5, 0)) {
            StdOut.println("The index (5, 0) is valid and it should not be");
        }
        else {
            StdOut.println("The index (5, 0) is not valid");
        }

        if (percolation.indexesAreValid(0, 5)) {
            StdOut.println("The index (0, 5) is valid and it should not be");
        }
        else {
            StdOut.println("The index (0, 5) is not valid");
        }

        if (percolation.indexesAreValid(5, 5)) {
            StdOut.println("The index (5, 5) is valid and it should not be");
        }
        else {
            StdOut.println("The index (5, 5) is not valid");
        }

        // Tests for xyTo1D()
        StdOut.println("First position of the first row: " + percolation.xyTo1D(0, 0));
        StdOut.println("Last position of the first row: " + percolation.xyTo1D(0, 4));
        StdOut.println("First position of the second row: " + percolation.xyTo1D(1, 0));
        StdOut.println("Last position of the second row: " + percolation.xyTo1D(1, 4));
        StdOut.println("First position of the third row: " + percolation.xyTo1D(2, 0));
        StdOut.println("Last position of the third row: " + percolation.xyTo1D(2, 4));
        StdOut.println("First position of the fourth row: " + percolation.xyTo1D(3, 0));
        StdOut.println("Last position of the fourth row: " + percolation.xyTo1D(3, 4));
        StdOut.println("First position of the fifth row: " + percolation.xyTo1D(4, 0));
        StdOut.println("Last position of the fifth row: " + percolation.xyTo1D(4, 4));

        // Tests for open()
        percolation.open(0, 1);
        percolation.open(1, 1);
        percolation.open(1, 2);
        percolation.open(4, 3);

        if (percolation.union.connected(percolation.xyTo1D(0, 1), percolation.virtualTopIndex)) {
            StdOut.println("Site (0, 1) is connected to the virtual top");
        }
        else {
            StdOut.println("Site (0, 1) is NOT connected to the virtual top and it should be");
        }

        if (percolation.union.connected(percolation.xyTo1D(1, 1), percolation.xyTo1D(1, 2))) {
            StdOut.println("Sites (1, 1) and (1, 2) are connected");
        }
        else {
            StdOut.println("Sites (1, 1) and (1, 2) are NOT connected and they should be");
        }

        if (percolation.union.connected(percolation.xyTo1D(4, 3), percolation.virtualBottomIndex)) {
            StdOut.println("Site (4, 3) is connected to the virtual bottom");
        }
        else {
            StdOut.println("Site (4, 3) is NOT connected to the virtual bottom and it should be");
        }

        // Tests for isOpen()
        if (percolation.isOpen(1, 1)) {
            StdOut.println("Site (1, 1) is open");
        }
        else {
            StdOut.println("Site (1, 1) is NOT open and it should be");
        }

        if (percolation.isOpen(1, 2)) {
            StdOut.println("Site (1, 2) is open");
        }
        else {
            StdOut.println("Site (1, 2) is NOT open and it should be");
        }

        // Tests for isFull()
        if (percolation.isFull(1, 1)) {
            StdOut.println("Site (1, 1) is full");
        }
        else {
            StdOut.println("Site (1, 1) is not full and it should be");
        }

        // Tests for numberOfOpenSites()
        StdOut.println("There are " + percolation.numberOfOpenSites() + " open sites and there should be 4");

        // Tests for percolates
        percolation.open(2, 2);
        percolation.open(3, 2);
        percolation.open(4, 2);

        if (percolation.percolates()) {
            StdOut.println("The system percolates");
        }
        else {
            StdOut.println("The system does not percolate and it should");
        }
    }
}
