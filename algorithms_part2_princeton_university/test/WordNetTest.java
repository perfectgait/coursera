import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordNetTest {

    @Test
    void nouns() {
        WordNet wordnet = new WordNet(
            "/Users/mrathbun/Projects/coursera/algorithms_part2_princeton_university/wordnet/synsets.txt",
            "/Users/mrathbun/Projects/coursera/algorithms_part2_princeton_university/wordnet/hypernyms.txt"
        );
    }

    @Test
    void isNoun() {
    }

    @Test
    void distance() {
    }

    @Test
    void sap() {
    }
}
