import edu.princeton.cs.algs4.Digraph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SAPTest {

    @Test
    void SAP_nullDigraph() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SAP(null);
        });
    }

    @Test
    void length_vOrWOutOfBounds() {
        Digraph digraph = new Digraph(13);
        SAP sap = new SAP(digraph);

        assertThrows(IllegalArgumentException.class, () -> {
            sap.length(-1, 5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            sap.length(13, 5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            sap.length(5, -1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            sap.length(5, 13);
        });
    }

    @Test
    void length() {
        Digraph digraph = new Digraph(13);
        digraph.addEdge(12, 10);
        digraph.addEdge(11, 10);
        digraph.addEdge(10, 5);
        digraph.addEdge(9, 5);
        digraph.addEdge(8, 3);
        digraph.addEdge(7, 3);
        digraph.addEdge(5, 1);
        digraph.addEdge(4, 1);
        digraph.addEdge(3, 1);
        digraph.addEdge(2, 0);
        digraph.addEdge(1, 0);

        SAP sap = new SAP(digraph);

        assertEquals(4, sap.length(3, 11));

        digraph = new Digraph(6);
        digraph.addEdge(1, 2);
        digraph.addEdge(1, 0);
        digraph.addEdge(2, 3);
        digraph.addEdge(3, 4);
        digraph.addEdge(4, 5);
        digraph.addEdge(5, 0);

        sap = new SAP(digraph);

        assertEquals(2, sap.length(1, 5));

        // No common ancestor
        digraph = new Digraph(5);
        digraph.addEdge(2, 1);
        digraph.addEdge(1, 0);
        digraph.addEdge(3, 4);

        sap = new SAP(digraph);

        assertEquals(-1, sap.length(2, 3));

        // A vertex is an ancestor of itself
        digraph = new Digraph(1);
        sap = new SAP(digraph);

        assertEquals(0, sap.length(0, 0));
    }

    @Test
    void length_multipleSources_vOrWOutOfBounds() {
        ArrayList<Integer> v = new ArrayList<Integer>();
        ArrayList<Integer> w = new ArrayList<Integer>();

        Digraph digraph = new Digraph(13);
        SAP sap = new SAP(digraph);
        v.add(-1);
        w.add(5);

        assertThrows(IllegalArgumentException.class, () -> {
            sap.length(v, w);
        });

        v.remove(0);
        v.add(13);

        assertThrows(IllegalArgumentException.class, () -> {
            sap.length(v, w);
        });

        v.remove(0);
        v.add(5);
        w.remove(0);
        w.add(-1);

        assertThrows(IllegalArgumentException.class, () -> {
            sap.length(v, w);
        });

        w.remove(0);
        w.add(13);

        assertThrows(IllegalArgumentException.class, () -> {
            sap.length(v, w);
        });
    }

    @Test
    void length_multipleSources() {
        ArrayList<Integer> v = new ArrayList<Integer>();
        ArrayList<Integer> w = new ArrayList<Integer>();

        Digraph digraph = new Digraph(13);
        digraph.addEdge(12, 10);
        digraph.addEdge(11, 10);
        digraph.addEdge(10, 5);
        digraph.addEdge(9, 5);
        digraph.addEdge(8, 3);
        digraph.addEdge(7, 3);
        digraph.addEdge(5, 1);
        digraph.addEdge(4, 1);
        digraph.addEdge(3, 1);
        digraph.addEdge(2, 0);
        digraph.addEdge(1, 0);

        SAP sap = new SAP(digraph);
        v.add(3);
        v.add(1);
        v.add(12);
        w.add(2);
        w.add(10);

        assertEquals(1, sap.length(v, w));
    }

    @Test
    void ancestor_vOrWOutOfBounds() {
        Digraph digraph = new Digraph(13);
        SAP sap = new SAP(digraph);

        assertThrows(IllegalArgumentException.class, () -> {
            sap.ancestor(-1, 5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            sap.ancestor(13, 5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            sap.ancestor(5, -1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            sap.ancestor(5, 13);
        });
    }

    @Test
    void ancestor() {
        Digraph digraph = new Digraph(13);
        digraph.addEdge(12, 10);
        digraph.addEdge(11, 10);
        digraph.addEdge(10, 5);
        digraph.addEdge(9, 5);
        digraph.addEdge(8, 3);
        digraph.addEdge(7, 3);
        digraph.addEdge(5, 1);
        digraph.addEdge(4, 1);
        digraph.addEdge(3, 1);
        digraph.addEdge(2, 0);
        digraph.addEdge(1, 0);

        SAP sap = new SAP(digraph);

        assertEquals(1, sap.ancestor(3, 11));

        digraph = new Digraph(6);
        digraph.addEdge(1, 2);
        digraph.addEdge(1, 0);
        digraph.addEdge(2, 3);
        digraph.addEdge(3, 4);
        digraph.addEdge(4, 5);
        digraph.addEdge(5, 0);

        sap = new SAP(digraph);

        assertEquals(0, sap.ancestor(1, 5));

        // No common ancestor
        digraph = new Digraph(5);
        digraph.addEdge(2, 1);
        digraph.addEdge(1, 0);
        digraph.addEdge(3, 4);

        sap = new SAP(digraph);

        assertEquals(-1, sap.ancestor(2, 3));

        // A vertex is an ancestor of itself
        digraph = new Digraph(1);
        sap = new SAP(digraph);

        assertEquals(0, sap.ancestor(0, 0));
    }

    @Test
    void ancestor_multipleSources_vOrWOutOfBounds() {
        ArrayList<Integer> v = new ArrayList<Integer>();
        ArrayList<Integer> w = new ArrayList<Integer>();

        Digraph digraph = new Digraph(13);
        SAP sap = new SAP(digraph);
        v.add(-1);
        w.add(5);

        assertThrows(IllegalArgumentException.class, () -> {
            sap.ancestor(v, w);
        });

        v.remove(0);
        v.add(13);

        assertThrows(IllegalArgumentException.class, () -> {
            sap.ancestor(v, w);
        });

        v.remove(0);
        v.add(5);
        w.remove(0);
        w.add(-1);

        assertThrows(IllegalArgumentException.class, () -> {
            sap.ancestor(v, w);
        });

        w.remove(0);
        w.add(13);

        assertThrows(IllegalArgumentException.class, () -> {
            sap.ancestor(v, w);
        });
    }

    @Test
    void ancestor_multipleSources() {
        ArrayList<Integer> v = new ArrayList<Integer>();
        ArrayList<Integer> w = new ArrayList<Integer>();

        Digraph digraph = new Digraph(13);
        digraph.addEdge(12, 10);
        digraph.addEdge(11, 10);
        digraph.addEdge(10, 5);
        digraph.addEdge(9, 5);
        digraph.addEdge(8, 3);
        digraph.addEdge(7, 3);
        digraph.addEdge(5, 1);
        digraph.addEdge(4, 1);
        digraph.addEdge(3, 1);
        digraph.addEdge(2, 0);
        digraph.addEdge(1, 0);

        SAP sap = new SAP(digraph);
        v.add(3);
        v.add(1);
        v.add(12);
        w.add(2);
        w.add(10);

        assertEquals(10, sap.ancestor(v, w));
    }
}
