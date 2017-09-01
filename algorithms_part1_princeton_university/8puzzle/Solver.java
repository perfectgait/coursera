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
    private final Board initial;
    private SearchNode goal;
    private boolean isSolvableHasRun;
    private boolean isSolvable;

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
            int thatManhattan = that.board.manhattan();

            if (this.manhattan + this.numberOfMoves < thatManhattan + that.numberOfMoves) {
                return -1;
            } else if (this.manhattan + this.numberOfMoves > thatManhattan + that.numberOfMoves) {
                return 1;
            }

            return 0;
        }
    }

    public Solver(Board initial) {
        this.initial = initial;

        if (this.isSolvable()) {
            MinPQ<SearchNode> queue = new MinPQ<>();
            SearchNode node = new SearchNode(initial, 0, null);
            queue.insert(node);

            while (!queue.isEmpty() && !node.board.isGoal()) {
                node = this.runIterationOfAStarAlgorithm(queue);
            }

            // @TODO Is there a better way to do this?
            if (node.board.isGoal()) {
                this.goal = node;
            }
        }
    }

    public boolean isSolvable() {
        if (this.isSolvableHasRun) {
            return this.isSolvable;
        }

        this.isSolvableHasRun = true;
        this.isSolvable = false;

        Board twin = this.initial.twin();

        MinPQ<SearchNode> originalBoardQueue = new MinPQ<>();
        MinPQ<SearchNode> twinBoardQueue = new MinPQ<>();
        SearchNode originalBoardNode = new SearchNode(this.initial, 0, null);
        SearchNode twinBoardNode = new SearchNode(twin, 0, null);

        originalBoardQueue.insert(originalBoardNode);
        twinBoardQueue.insert(twinBoardNode);

        while (!originalBoardQueue.isEmpty() && !twinBoardQueue.isEmpty()) {
            originalBoardNode = this.runIterationOfAStarAlgorithm(originalBoardQueue);
            twinBoardNode = this.runIterationOfAStarAlgorithm(twinBoardQueue);

            if (originalBoardNode.board.isGoal()) {
                this.isSolvable = true;

                return true;
            }

            if (twinBoardNode.board.isGoal()) {
                this.isSolvable = false;

                return false;
            }
        }

        return false;
    }

    public int moves() {
        if (this.goal != null) {
            return this.goal.numberOfMoves;
        }

        return -1;
    }

    public Iterable<Board> solution() {
        if (this.goal != null) {
            SearchNode node = this.goal;
            LinkedList<Board> path = new LinkedList<>();

            while (node != null) {
                path.addFirst(node.board);
                node = node.previous;
            }

            return path;
        }

        return null;
    }

    private SearchNode runIterationOfAStarAlgorithm(MinPQ<SearchNode> queue) {
        SearchNode node = queue.delMin();
        Iterable<Board> neighbors = node.board.neighbors();

        for (Board neighbor : neighbors) {
            if (!node.board.equals(neighbor)) {
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
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}