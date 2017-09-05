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

        private SearchNode(Board board, int numberOfMoves, SearchNode previous) {
            this.board = board;
            this.numberOfMoves = numberOfMoves;
            this.previous = previous;
            this.manhattan = board.manhattan();
        }

        public int compareTo(SearchNode that) {
            if (this.manhattan + this.numberOfMoves < that.manhattan + that.numberOfMoves) {
                return -1;
            } else if (this.manhattan + this.numberOfMoves > that.manhattan + that.numberOfMoves) {
                return 1;
            }

            return 0;
        }
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("You may not pass a null board");
        }

        boolean solved = false;

        Board twin = initial.twin();

        MinPQ<SearchNode> originalBoardQueue = new MinPQ<>();
        MinPQ<SearchNode> twinBoardQueue = new MinPQ<>();
        SearchNode originalBoardNode = new SearchNode(initial, 0, null);
        SearchNode twinBoardNode = new SearchNode(twin, 0, null);

        originalBoardQueue.insert(originalBoardNode);
        twinBoardQueue.insert(twinBoardNode);

        while (!solved && !originalBoardQueue.isEmpty() && !twinBoardQueue.isEmpty()) {
            originalBoardNode = this.runIterationOfAStarAlgorithm(
                originalBoardQueue,
                originalBoardNode
            );
            twinBoardNode = this.runIterationOfAStarAlgorithm(
                twinBoardQueue,
                twinBoardNode
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

    public boolean isSolvable() {
        return this.numberOfMoves >= 0;
    }

    public int moves() {
        return this.numberOfMoves;
    }

    public Iterable<Board> solution() {
        if (this.numberOfMoves < 0) {
            return null;
        }

        return this.solution;
    }

    private SearchNode runIterationOfAStarAlgorithm(MinPQ<SearchNode> queue, SearchNode previousNode) {
        SearchNode node = queue.delMin();
        Iterable<Board> neighbors = node.board.neighbors();

        for (Board neighbor : neighbors) {
            if (previousNode == null || !previousNode.board.equals(neighbor)) {
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
