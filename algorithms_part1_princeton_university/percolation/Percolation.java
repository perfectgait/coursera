import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;

    private boolean[][] grid;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.grid[i][j] = false;
            }
        }
    }

    public void open(int row, int col) {
        if (row < 1 || row > n) {
            throw new IndexOutOfBoundsException();
        }

        if (col < 1 || col > n) {
            throw new IndexOutOfBoundsException();
        }

        if (!this.isOpen(row, col)) {
            this.grid[row][col] = true;
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n) {
            throw new IndexOutOfBoundsException();
        }

        if (col < 1 || col > n) {
            throw new IndexOutOfBoundsException();
        }

        return this.grid[row][col];
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || row > n) {
            throw new IndexOutOfBoundsException();
        }

        if (col < 1 || col > n) {
            throw new IndexOutOfBoundsException();
        }

        return !this.grid[row][col];
    }

    public int numberOfOpenSites() {
        return 1;
    }

    public boolean percolates() {
        return true;
    }

    public static void main(String[] args) {

    }
}
