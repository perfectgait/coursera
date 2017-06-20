import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private boolean[] openSites;
    private int openCount;
//    private

//    private boolean[][] grid;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
//                this.grid[i][j] = false;
            }
        }
    }

    public void open(int row, int col) {
        if (!this.indexesAreValid(row, col)) {
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean isOpen(int row, int col) {
        if (!this.indexesAreValid(row, col)) {
            throw new IndexOutOfBoundsException();
        }

//        return this.grid[row][col];
        return true;
    }

    public boolean isFull(int row, int col) {
        if (!this.indexesAreValid(row, col)) {
            throw new IndexOutOfBoundsException();
        }

//        return !this.grid[row][col];
        return true;
    }

    public int numberOfOpenSites() {
        return 1;
    }

    public boolean percolates() {
        return true;
    }

    private int xyTo1D(int row, int column) {
        if (!this.indexesAreValid(row, column)) {
            throw new IndexOutOfBoundsException();
        }

        return n * row + column;
    }
    
    private boolean indexesAreValid(int row, int column) {
        return row >= 0 && column >= 0 && row < n && column < n;
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        System.out.println("First position of the first row: " + percolation.xyTo1D(0, 0));
        System.out.println("Last position of the first row: " + percolation.xyTo1D(0, 4));
        System.out.println("First position of the second row: " + percolation.xyTo1D(1, 0));
        System.out.println("Last position of the second row: " + percolation.xyTo1D(1, 4));
        System.out.println("First position of the third row: " + percolation.xyTo1D(2, 0));
        System.out.println("Last position of the third row: " + percolation.xyTo1D(2, 4));
        System.out.println("First position of the fourth row: " + percolation.xyTo1D(3, 0));
        System.out.println("Last position of the fourth row: " + percolation.xyTo1D(3, 4));
        System.out.println("First position of the fifth row: " + percolation.xyTo1D(4, 0));
        System.out.println("Last position of the fifth row: " + percolation.xyTo1D(4, 4));
    }
}
