/******************************************************************************
 *  Compilation:  javac Board.java
 *  Execution:    java Board
 *  Dependencies: java.util.Stack
 *
 *  8 puzzle board
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
    public int[][] blocks;
    private int dimension;

    public Board(int[][] blocks) {
        this.blocks = blocks;
        dimension = this.blocks.length;
    }

    /**
     * @return the dimension of the board
     */
    public int dimension() {
        return dimension;
    }

    /**
     * @return the number of blocks in the wrong position
     */
    public int hamming() {
        int outOfPlaceBlocks = 0;

        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                if (blocks[row][col] != 0) {
                    int expectedRow = (blocks[row][col] - 1) / dimension;
                    int expectedCol = (blocks[row][col] - 1) % dimension;

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

        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                if (blocks[row][col] != 0) {
                    int expectedRow = (blocks[row][col] - 1) / dimension;
                    int expectedCol = (blocks[row][col] - 1) % dimension;

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
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                if (blocks[row][col] != 0) {
                    int expectedRow = (blocks[row][col] - 1) / dimension;
                    int expectedCol = (blocks[row][col] - 1) % dimension;

                    if (expectedRow != row || expectedCol != col) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * @return A copy of this board with 2 random blocks swapped
     */
    public Board twin() {
        int[][] copyOfBlocks = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                copyOfBlocks[i][j] = blocks[i][j];
            }
        }

        // The random values should be exclusive of the dimension because it is not 0 based.
        int randomRow1 = ThreadLocalRandom.current().nextInt(0, dimension);
        int randomCol1 = ThreadLocalRandom.current().nextInt(0, dimension);

        // Don't choose the empty space
        while (copyOfBlocks[randomRow1][randomCol1] == 0) {
            randomRow1 = ThreadLocalRandom.current().nextInt(0, dimension);
            randomCol1 = ThreadLocalRandom.current().nextInt(0, dimension);
        }

        int randomRow2 = ThreadLocalRandom.current().nextInt(0, dimension);
        int randomCol2 = ThreadLocalRandom.current().nextInt(0, dimension);

        // Don't choose the same block as above or the empty space
        while ((randomRow2 == randomRow1 && randomCol2 == randomCol1) || copyOfBlocks[randomRow2][randomCol2] == 0) {
            randomRow2 = ThreadLocalRandom.current().nextInt(0, dimension);
            randomCol2 = ThreadLocalRandom.current().nextInt(0, dimension);
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

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        return new Stack<Board>();
    }

    /**
     * @return the string representation of the board
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(dimension);
        builder.append(System.lineSeparator());

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                builder.append(" ");
                builder.append(blocks[i][j]);
            }

            if (i < 2) {
                builder.append(System.lineSeparator());
            }
        }

        return builder.toString();
    }

    public static void main(String[] args) {

    }
}