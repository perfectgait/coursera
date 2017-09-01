/******************************************************************************
 *  Compilation:  javac Board.java
 *  Execution:    java Board
 *  Dependencies: java.util.Stack
 *
 *  8 puzzle board
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;

import java.util.Stack;

public class Board {
    private final int[][] blocks;
    private final int dimension;

    public Board(int[][] blocks) {
        this.blocks = this.copyBlocks(blocks);
        dimension = this.blocks.length;
    }

    /**
     * @return the dimension of the board
     */
    public int dimension() {
        return this.dimension;
    }

    /**
     * @return the number of blocks in the wrong position
     */
    public int hamming() {
        int outOfPlaceBlocks = 0;

        for (int row = 0; row < this.dimension; row++) {
            for (int col = 0; col < this.dimension; col++) {
                if (this.blocks[row][col] != 0) {
                    int expectedRow = (this.blocks[row][col] - 1) / this.dimension;
                    int expectedCol = (this.blocks[row][col] - 1) % this.dimension;

                    if (expectedRow != row || expectedCol != col) {
                        outOfPlaceBlocks++;
                    }
                }
            }
        }

        return outOfPlaceBlocks;
    }

    /**
     * @return the sum of the vertical and horizontal distances that each out of place block must be moved to be in its final position
     */
    public int manhattan() {
        int distance = 0;

        for (int row = 0; row < this.dimension; row++) {
            for (int col = 0; col < this.dimension; col++) {
                if (this.blocks[row][col] != 0) {
                    int expectedRow = (this.blocks[row][col] - 1) / this.dimension;
                    int expectedCol = (this.blocks[row][col] - 1) % this.dimension;

                    if (expectedRow != row || expectedCol != col) {
                        distance += Math.abs(expectedRow - row) + Math.abs(expectedCol - col);
                    }
                }
            }
        }

        return distance;
    }

    /**
     * @return whether or not the board is in the goal state
     */
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    /**
     * @return A copy of this board with 2 random blocks swapped
     */
    public Board twin() {
        int[][] copyOfBlocks = this.copyBlocks(this.blocks);

        // The random values should be exclusive of the dimension because it is not 0 based.
        int randomRow1 = StdRandom.uniform(this.dimension);
        int randomCol1 = StdRandom.uniform(this.dimension);

        // Don't choose the empty space
        while (copyOfBlocks[randomRow1][randomCol1] == 0) {
            randomRow1 = StdRandom.uniform(this.dimension);
            randomCol1 = StdRandom.uniform(this.dimension);
        }

        int randomRow2 = StdRandom.uniform(this.dimension);
        int randomCol2 = StdRandom.uniform(this.dimension);

        // Don't choose the same block as above or the empty space
        while ((randomRow2 == randomRow1 && randomCol2 == randomCol1) || copyOfBlocks[randomRow2][randomCol2] == 0) {
            randomRow2 = StdRandom.uniform(this.dimension);
            randomCol2 = StdRandom.uniform(this.dimension);
        }

        int tempBlock = copyOfBlocks[randomRow1][randomCol1];
        copyOfBlocks[randomRow1][randomCol1] = copyOfBlocks[randomRow2][randomCol2];
        copyOfBlocks[randomRow2][randomCol2] = tempBlock;

        return new Board(copyOfBlocks);
    }

    /**
     * @param y the board to compare
     * @return whether or not the comparison board is equal to this board
     */
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }

        if (y == null) {
            return false;
        }

        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board) y;

        if (this.dimension != that.dimension()) {
            return false;
        }

        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * @return The boards that can be made as a result of moves using the blank space
     */
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        int i = 0;
        int j = 0;
        boolean blankSpaceFound = false;

        while (i < this.dimension && !blankSpaceFound) {
            j = 0;

            while (j < this.dimension && !blankSpaceFound) {
                if (this.blocks[i][j] == 0) {
                    blankSpaceFound = true;
                }

                if (!blankSpaceFound) {
                    j++;
                }
            }

            if (!blankSpaceFound) {
                i++;
            }
        }

        if (blankSpaceFound) {
            // Top neighbor
            if (i > 0) {
                int[][] topNeighborBlocks = this.copyBlocks(this.blocks);

                topNeighborBlocks[i][j] = topNeighborBlocks[i - 1][j];
                topNeighborBlocks[i - 1][j] = 0;

                neighbors.push(new Board(topNeighborBlocks));
            }

            // Right neighbor
            if (j < dimension - 1) {
                int[][] rightNeighborBlocks = this.copyBlocks(this.blocks);

                rightNeighborBlocks[i][j] = rightNeighborBlocks[i][j + 1];
                rightNeighborBlocks[i][j + 1] = 0;

                neighbors.push(new Board(rightNeighborBlocks));
            }

            // Bottom neighbor
            if (i < dimension - 1) {
                int[][] bottomNeighborBlocks = this.copyBlocks(this.blocks);

                bottomNeighborBlocks[i][j] = bottomNeighborBlocks[i + 1][j];
                bottomNeighborBlocks[i + 1][j] = 0;

                neighbors.push(new Board(bottomNeighborBlocks));
            }

            // Left neighbor
            if (j > 0) {
                int[][] leftNeighborBlocks = this.copyBlocks(this.blocks);

                leftNeighborBlocks[i][j] = leftNeighborBlocks[i][j - 1];
                leftNeighborBlocks[i][j - 1] = 0;

                neighbors.push(new Board(leftNeighborBlocks));
            }
        }

        return neighbors;
    }

    /**
     * @return the string representation of the board
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.dimension);
        builder.append(System.lineSeparator());

        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                builder.append(this.blocks[i][j]);
                builder.append(" ");
            }

            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

    /**
     * @param blocksToCopy the original blocksToCopy
     * @return a copy of the blocksToCopy
     */
    private int[][] copyBlocks(int[][] blocksToCopy) {
        int[][] copyOfBlocks = new int[blocksToCopy.length][blocksToCopy.length];

        for (int i = 0; i < blocksToCopy.length; i++) {
            for (int j = 0; j < blocksToCopy.length; j++) {
                copyOfBlocks[i][j] = blocksToCopy[i][j];
            }
        }

        return copyOfBlocks;
    }
}