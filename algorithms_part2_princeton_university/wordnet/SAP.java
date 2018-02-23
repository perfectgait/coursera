/******************************************************************************
 *  Compilation:  javac SAP.java
 *  Execution:    java SAP
 *  Dependencies: Digraph.java BreadthFirstDirectedPaths.java
 *
 *  Implements shortest ancestral path
 ******************************************************************************/

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {
    private Digraph digraph;

    /**
     * @param G The Digraph
     */
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }

        this.digraph = G;
    }

    /**
     * @param v First node # to start from
     * @param w Second node # to start from
     * @return The length of the shortest ancestral path between v and w; -1 if no such path
     */
    public int length(int v, int w) {
        if (v < 0 || v > this.digraph.V() - 1 || w < 0 || w > this.digraph.V() - 1) {
            throw new IllegalArgumentException();
        }

        // @TODO Optimize this using BFS that runs from both vertices
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(this.digraph, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(this.digraph, w);

        return this.lengthFromBfs(bfs1, bfs2);
    }



    /**
     * @param v First node # to start from
     * @param w Second node # to start from
     * @return The length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        int vertices = this.digraph.V() - 1;

        for (Integer vertex : v) {
            if (vertex < 0 || vertex > vertices) {
                throw new IllegalArgumentException();
            }
        }

        for (Integer vertex : w) {
            if (vertex < 0 || vertex > vertices) {
                throw new IllegalArgumentException();
            }
        }

        // @TODO Optimize this using BFS that runs from both vertices
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(this.digraph, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(this.digraph, w);

        return this.lengthFromBfs(bfs1, bfs2);
    }

    /**
     * @param bfs1 The first BFS
     * @param bfs2 The second BFS
     * @return The length of a shortest ancestral path; -1 if no such path
     */
    private int lengthFromBfs(BreadthFirstDirectedPaths bfs1, BreadthFirstDirectedPaths bfs2) {
        int length = -1;

        for (int i = 0; i < this.digraph.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                if (length == -1 || bfs1.distTo(i) + bfs2.distTo(i) < length) {
                    length = bfs1.distTo(i) + bfs2.distTo(i);
                }
            }
        }

        return length;
    }

    /**
     * @param v First node # to start from
     * @param w Second node # to start from
     * @return A common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
     */
    public int ancestor(int v, int w) {

        if (v < 0 || v > this.digraph.V() - 1 || w < 0 || w > this.digraph.V() - 1) {
            throw new IllegalArgumentException();
        }
        // @TODO Optimize this using BFS that runs from both vertices
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(this.digraph, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(this.digraph, w);

        return this.ancestorFromBfs(bfs1, bfs2);
    }

    //
    /**
     * @param v First node # to start from
     * @param w Second node # to start from
     * @return A common ancestor that participates in a shortest ancestral path; -1 if no such path
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        int vertices = this.digraph.V() - 1;

        for (Integer vertex : v) {
            if (vertex < 0 || vertex > vertices) {
                throw new IllegalArgumentException();
            }
        }

        for (Integer vertex : w) {
            if (vertex < 0 || vertex > vertices) {
                throw new IllegalArgumentException();
            }
        }

        // @TODO Optimize this using BFS that runs from both vertices
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(this.digraph, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(this.digraph, w);

        return this.ancestorFromBfs(bfs1, bfs2);
    }

    /**
     * @param bfs1 The first BFS
     * @param bfs2 The second BFS
     * @return A common ancestor that participates in a shortest ancestral path; -1 if no such path
     */
    private int ancestorFromBfs(BreadthFirstDirectedPaths bfs1, BreadthFirstDirectedPaths bfs2) {
        int length = -1;
        int ancestor = -1;

        for (int i = 0; i < this.digraph.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                if (ancestor == -1 || bfs1.distTo(i) + bfs2.distTo(i) < length) {
                    length = bfs1.distTo(i) + bfs2.distTo(i);
                    ancestor = i;
                }
            }
        }

        return ancestor;
    }

    public static void main(String[] args) {

    }
}
