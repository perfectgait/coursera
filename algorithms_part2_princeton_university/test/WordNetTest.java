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
    void Wordnet_cycle() {
        assertThrows(IllegalArgumentException.class, () -> {
            // @TODO Dynamically point to these files
            new WordNet(
                "/Users/mrathbun/Projects/coursera/algorithms_part2_princeton_university/test/synsets6.txt",
                "/Users/mrathbun/Projects/coursera/algorithms_part2_princeton_university/test/hypernyms6InvalidCycle.txt"
            );
        });
    }

    @Test
    void Wordnet_multipleRoots() {
        assertThrows(IllegalArgumentException.class, () -> {
            // @TODO Dynamically point to these files
            new WordNet(
                "/Users/mrathbun/Projects/coursera/algorithms_part2_princeton_university/test/synsets3.txt",
                "/Users/mrathbun/Projects/coursera/algorithms_part2_princeton_university/test/hypernyms3InvalidTwoRoots.txt"
            );
        });
    }

    @Test
    void Wordnet() {
        // @TODO Setup test files for this
        // @TODO Remove this test if there is nothing to verify after construction
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
