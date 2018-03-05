/******************************************************************************
 *  Compilation:  javac Outcast.java
 *  Execution:    java Outcase
 *  Dependencies:
 *
 *  Implements outcast
 ******************************************************************************/

public class Outcast {
    private WordNet wordnet;

    /**
     * @param wordnet The WordNet
     */
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    /**
     * @param nouns The set of nouns
     * @return The outcast
     */
    public String outcast(String[] nouns) {
        int maximumDistance = -1;
        int currentDistance;
        String outcast = "";

        for (String noun : nouns) {
            currentDistance = 0;

            for (String secondNoun : nouns) {
                if (!noun.equals(secondNoun)) {
                    currentDistance += this.wordnet.distance(noun, secondNoun);
                }
            }

            if (currentDistance > maximumDistance) {
                maximumDistance = currentDistance;
                outcast = noun;
            }
        }

        return outcast;
    }

    public static void main(String[] args) {

    }
}
