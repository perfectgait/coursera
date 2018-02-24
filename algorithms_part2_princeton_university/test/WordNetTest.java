import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordNetTest {

    @Test
    void Wordnet_nullInputs() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WordNet(null, "hypernyms.txt");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new WordNet("synsets.txt", null);
        });
    }

    @Test
    void Wordnet() {
        // @TODO Setup test files for this
        WordNet wordnet = new WordNet(
            "/Users/mrathbun/Projects/coursera/algorithms_part2_princeton_university/wordnet/synsets.txt",
            "/Users/mrathbun/Projects/coursera/algorithms_part2_princeton_university/wordnet/hypernyms.txt"
        );
    }

    @Test
    void nouns() {
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
