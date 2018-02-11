import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {
    private Digraph digraph;

    /**
     * @param G The Digraph
     */
    public SAP(Digraph G) {
        this.digraph = G;
    }

    /**
     * @param v First node # to start from
     * @param w Second node # to start from
     * @return The length of the shortest ancestral path between v and w; -1 if no such path
     */
    public int length(int v, int w) {
        // @TODO Optimize this using BFS that runs from both vertices
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(this.digraph, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(this.digraph, w);

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
        // @TODO Optimize this using BFS that runs from both vertices
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(this.digraph, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(this.digraph, w);

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

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return -1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return -1;
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}