/******************************************************************************
 *  Compilation:  javac Solver.java
 *  Execution:    java Solver puzzle4.txt
 *  Dependencies: java.util.LinkedList
 *
 *  8 puzzle board solver
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Solver {
    private int numberOfMoves;
    private LinkedList<Board> solution;

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int numberOfMoves;
        private final SearchNode previous;
        private final int manhattan;

        /**
         * @param board The board
         * @param numberOfMoves The number of moves
         * @param previous The previous board
         */
        private SearchNode(Board board, int numberOfMoves, SearchNode previous) {
            this.board = board;
            this.numberOfMoves = numberOfMoves;
            this.previous = previous;
            this.manhattan = board.manhattan();
        }

        /**
         * @param that The search node
         * @return -1 if this board is < that board, 1 if this board is > that board and 0 if both boards are equal
         */
        public int compareTo(SearchNode that) {
            if (this.manhattan + this.numberOfMoves < that.manhattan + that.numberOfMoves) {
                return -1;
            } else if (this.manhattan + this.numberOfMoves > that.manhattan + that.numberOfMoves) {
                return 1;
            }

            return 0;
        }
    }

    /**
     * @param initial The initial board
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("You may not pass a null board");
        }

        // If the initial board is in the goal state
        if (initial.isGoal()) {
            this.numberOfMoves = 0;
            this.solution = new LinkedList<>();
            this.solution.addFirst(initial);

            return;
        }

        boolean solved = false;

        Board twin = initial.twin();

        MinPQ<SearchNode> originalBoardQueue = new MinPQ<>();
        MinPQ<SearchNode> twinBoardQueue = new MinPQ<>();
        SearchNode originalBoardNode;
        SearchNode twinBoardNode;

        originalBoardQueue.insert(new SearchNode(initial, 0, null));
        twinBoardQueue.insert(new SearchNode(twin, 0, null));

        while (!solved && !originalBoardQueue.isEmpty() && !twinBoardQueue.isEmpty()) {
            originalBoardNode = this.runIterationOfAStarAlgorithm(
                originalBoardQueue
            );
            twinBoardNode = this.runIterationOfAStarAlgorithm(
                twinBoardQueue
            );

            if (originalBoardNode.board.isGoal()) {
                this.numberOfMoves = originalBoardNode.numberOfMoves;
                this.solution = new LinkedList<>();

                while (originalBoardNode != null) {
                    this.solution.addFirst(originalBoardNode.board);
                    originalBoardNode = originalBoardNode.previous;
                }

                solved = true;
            }

            if (twinBoardNode.board.isGoal()) {
                this.numberOfMoves = -1;

                solved = true;
            }
        }
    }

    /**
     * @return Whether or not the initial board is solvable
     */
    public boolean isSolvable() {
        return this.numberOfMoves >= 0;
    }

    /**
     * @return The number of moves to solve the initial board or -1 if it's unsolvable
     */
    public int moves() {
        return this.numberOfMoves;
    }

    public Iterable<Board> solution() {
        if (this.numberOfMoves < 0) {
            return null;
        }

        return this.solution;
    }

    /**
     * @param queue The priority queue
     * @return The last de-queued node
     */
    private SearchNode runIterationOfAStarAlgorithm(MinPQ<SearchNode> queue) {
        SearchNode node = queue.delMin();
        Iterable<Board> neighbors = node.board.neighbors();

        for (Board neighbor : neighbors) {
            if (node.previous == null || !node.previous.board.equals(neighbor)) {
                queue.insert(new SearchNode(neighbor, node.numberOfMoves + 1, node));
            }
        }

        return node;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        }
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
