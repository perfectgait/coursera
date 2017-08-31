import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Solver {
    private Board initial;
    private SearchNode goal;

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int numberOfMoves;
        private SearchNode previous;
        private int manhattan;

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
        MinPQ<SearchNode> queue = new MinPQ<>();
        SearchNode node = new SearchNode(initial, 0, null);
        queue.insert(node);

        while (!queue.isEmpty() && !node.board.isGoal()) {
            node = queue.delMin();
            Iterable<Board> neighbors = node.board.neighbors();

            for (Board neighbor : neighbors) {
                if (!node.board.equals(neighbor)) {
                    queue.insert(new SearchNode(neighbor, node.numberOfMoves + 1, node));
                }
            }
        }

        // @TODO Is there a better way to do this?
        if (node.board.isGoal()) {
            this.goal = node;
        }
    }

    public boolean isSolvable() {
        Board twin = this.initial.twin();

        MinPQ<SearchNode> queue1 = new MinPQ<>();
        SearchNode node = new SearchNode(this.initial, 0, null);
        queue1.insert(node);

        while (!queue1.isEmpty() && !node.board.isGoal()) {
            node = queue1.delMin();
            Iterable<Board> neighbors = node.board.neighbors();

            for (Board neighbor : neighbors) {
                if (!node.board.equals(neighbor)) {
                    queue1.insert(new SearchNode(neighbor, node.numberOfMoves + 1, node));
                }
            }
        }



        return true;
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