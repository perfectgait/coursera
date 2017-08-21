/******************************************************************************
 *  Compilation:  javac Board.java
 *  Execution:    java Board
 *  Dependencies: java.util.Stack
 *
 *  8 puzzle board
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;

import java.util.Stack;

public class Board {
    private int[][] blocks;
    private int dimension;

    public Board(int[][] blocks) {
        this.blocks = blocks;
        this.dimension = this.blocks.length;
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

    public Board twin() {
        return this;
    }

    public boolean equals(Object y) {
        return false;
    }

    public Iterable<Board> neighbors() {
        return new Stack<Board>();
    }

    public String toString() {
        return "";
    }

    public static void main(String[] args) {

    }
}