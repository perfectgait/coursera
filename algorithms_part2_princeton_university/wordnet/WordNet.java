import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;

import java.util.ArrayList;

public class WordNet {

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        Out out = new Out();

        In in = new In(synsets);
        String[] synsetLines = in.readAllLines();
        in.close();

        for (String synsetLine : synsetLines) {
            String[] pieces = synsetLine.split(",");

            for (String piece : pieces) {
                out.print(piece);
                out.print(" ");
            }

            out.println();
        }

        in = new In(hypernyms);
        String[] hypernymLines = in.readAllLines();
        in.close();

        for (String hypernymLine : hypernymLines) {
            String[] pieces = hypernymLine.split(",");

            for (String piece : pieces) {
                out.print(piece);
                out.print(" ");
            }

            out.println();
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
