/******************************************************************************
 *  Compilation:  javac WordNet.java
 *  Execution:    java WordNet
 *  Dependencies: Digraph.java DirectedCycle.java In.java
 *
 *  Implements WordNet
 ******************************************************************************/

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.LinkedList;

public class WordNet {
    private HashMap<Integer, String> synsets;
    private HashMap<String, LinkedList<Integer>> nouns;
    private Digraph hypernyms;
    private int totalSynsets;
    private final SAP sap;

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

        this.sap = new SAP(this.hypernyms);
    }

    /**
     * @param synsetsFilename The filename of the synsets file
     */
    private void parseSynsets(String synsetsFilename) {
        // Example synset line from file
        // 36,AND_circuit AND_gate,a circuit in a computer that fires only when all of its inputs fire
        // 36 is the id, AND_circuit AND_gate are the nouns in the synset, a circuit in a computer that fires only when
        // all of its inputs fire is the gloss (definition)

        this.synsets = new HashMap<>();
        this.nouns = new HashMap<>();
        this.totalSynsets = 0;

        In in = new In(synsetsFilename);
        int synsetId;

        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] pieces = line.split(",");
            String[] parsedNouns = pieces[1].split(" ");
            synsetId = Integer.parseInt(pieces[0]);

            this.synsets.put(synsetId, pieces[1]);

            for (String noun : parsedNouns) {
                if (this.nouns.containsKey(noun)) {
                    this.nouns.get(noun).add(synsetId);
                } else {
                    LinkedList<Integer> synsetIds = new LinkedList<>();
                    synsetIds.add(synsetId);
                    this.nouns.put(noun, synsetIds);
                }
            }

            this.totalSynsets++;
        }

        in.close();
    }

    /**
     * @param hypernymsFilename The filename of the hypernym file
     */
    private void parseHypernyms(String hypernymsFilename) {
        // Example hypernym line from file
        // 164,21012,56099
        // 164 is the synset id, 21012 and 56099 are the id numbers of the synsets hypernyms

        In in = new In(hypernymsFilename);
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

    /**
     * @return all WordNet nouns
     */
    public Iterable<String> nouns() {
        return this.nouns.keySet();
    }

    /**
     * @param word The word to search for
     * @return whether or not the word is a WordNet noun
     */
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }

        return this.nouns.containsKey(word);
    }

    /**
     * @param nounA The first noun
     * @param nounB The second noun
     * @return The shortest distance between the two nouns
     */
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null || !this.isNoun(nounA) || !this.isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        LinkedList<Integer> synsetAIds = this.nouns.get(nounA);
        LinkedList<Integer> synsetBIds = this.nouns.get(nounB);

        return this.sap.length(synsetAIds, synsetBIds);
    }

    /**
     * @param nounA The first noun
     * @param nounB The second noun
     * @return A synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
     *         in a shortest ancestral path (defined below)
     */
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !this.isNoun(nounA) || !this.isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        LinkedList<Integer> synsetAIds = this.nouns.get(nounA);
        LinkedList<Integer> synsetBIds = this.nouns.get(nounB);
        int ancestor = this.sap.ancestor(synsetAIds, synsetBIds);

        return this.synsets.get(ancestor);
    }

    public static void main(String[] args) {
        // Tests can be found in WordNetTest
    }
}
