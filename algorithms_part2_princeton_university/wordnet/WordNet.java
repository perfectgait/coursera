import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;

public class WordNet {
    private HashMap<Integer, String[]> synsets;
    private HashMap<String, Boolean> nouns;
    private Digraph hypernyms;
    private int totalSynsets;

    /**
     * @param synsets The filename of the synsets file
     * @param hypernyms The filename of the hypernyms file
     */
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }

        this.parseSynsets(synsets);
        this.parseHypernyms(hypernyms);

        // Test for cycle
        DirectedCycle directedCycle = new DirectedCycle(this.hypernyms);

        // Test for cycle
        if (directedCycle.hasCycle()) {
            throw new IllegalArgumentException();
        }

        int numberOfRoots = 0;

        // Test for single root
        for (int i = 0; i < this.hypernyms.V(); i++) {
            if (this.hypernyms.outdegree(i) == 0) {
                numberOfRoots++;
            }

            if (numberOfRoots > 1) {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * @param synsets The filename of the synsets file
     */
    private void parseSynsets(String synsets) {
        // Example synset line from file
        // 36,AND_circuit AND_gate,a circuit in a computer that fires only when all of its inputs fire
        // 36 is the id, AND_circuit AND_gate are the nouns in the synset, a circuit in a computer that fires only when
        // all of its inputs fire is the gloss (definition)

        this.synsets = new HashMap<>();
        this.nouns = new HashMap<>();
        this.totalSynsets = 0;

        In in = new In(synsets);

        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] pieces = line.split(",");
            String[] nouns = pieces[1].split(" ");

            this.synsets.put(Integer.parseInt(pieces[0]), nouns);

            for (String noun : nouns) {
                // This will allow constant time lookup for isNoun()
                this.nouns.put(noun, true);
            }

            this.totalSynsets++;
        }

        in.close();
    }

    /**
     * @param hypernyms The filename of the hypernym file
     */
    private void parseHypernyms(String hypernyms) {
        // Example hypernym line from file
        // 164,21012,56099
        // 164 is the synset id, 21012 and 56099 are the id numbers of the synsets hypernyms

        In in = new In(hypernyms);
        String[] lines = in.readAllLines();
        in.close();
        this.hypernyms = new Digraph(this.totalSynsets);

        for (String line : lines) {
            String[] pieces = line.split(",");
            int synsetId = -1;

            for (String id : pieces) {
                if (synsetId == -1) {
                    synsetId = Integer.parseInt(id);
                } else {
                    this.hypernyms.addEdge(synsetId, Integer.parseInt(id));
                }
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        ArrayList<String> nouns = new ArrayList<>();

        return nouns;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return -1;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return "banana";
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
