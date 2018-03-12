import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordNetTest {
    private String directory;

    @BeforeEach
    void setup() {
        this.directory = System.getProperty("user.dir");
    }

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
            new WordNet(
                this.directory + "/algorithms_part2_princeton_university/test/synsets6.txt",
                this.directory + "/algorithms_part2_princeton_university/test/hypernyms6InvalidCycle.txt"
            );
        });
    }

    @Test
    void Wordnet_multipleRoots() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WordNet(
                this.directory + "/algorithms_part2_princeton_university/test/synsets3.txt",
                this.directory + "/algorithms_part2_princeton_university/test/hypernyms3InvalidTwoRoots.txt"
            );
        });
    }

    @Test
    void nouns() {
        WordNet wordnet = new WordNet(
            this.directory + "/algorithms_part2_princeton_university/test/synsets3.txt",
            this.directory + "/algorithms_part2_princeton_university/test/hypernyms3.txt"
        );

        ArrayList<String> expectedNouns = new ArrayList<>(3);
        expectedNouns.add("a");
        expectedNouns.add("b");
        expectedNouns.add("c");
        Iterable<String> nouns = wordnet.nouns();

        assertIterableEquals(expectedNouns, nouns);
    }

    @Test
    void isNoun_nullInput() {
        WordNet wordnet = new WordNet(
            this.directory + "/algorithms_part2_princeton_university/test/synsets3.txt",
            this.directory + "/algorithms_part2_princeton_university/test/hypernyms3.txt"
        );

        assertThrows(IllegalArgumentException.class, () -> {
            wordnet.isNoun(null);
        });
    }

    @Test
    void isNoun() {
        WordNet wordnet = new WordNet(
            this.directory + "/algorithms_part2_princeton_university/test/synsets3.txt",
            this.directory + "/algorithms_part2_princeton_university/test/hypernyms3.txt"
        );

        assertTrue(wordnet.isNoun("a"));
        assertFalse(wordnet.isNoun("not a noun"));
    }

    @Test
    void distance_nullInputs() {
        WordNet wordnet = new WordNet(
            this.directory + "/algorithms_part2_princeton_university/test/synsets3.txt",
            this.directory + "/algorithms_part2_princeton_university/test/hypernyms3.txt"
        );

        assertThrows(IllegalArgumentException.class, () -> {
            wordnet.distance(null, "a");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            wordnet.distance("a", null);
        });
    }

    @Test
    void distance_invalidWords() {
        WordNet wordnet = new WordNet(
            this.directory + "/algorithms_part2_princeton_university/test/synsets3.txt",
            this.directory + "/algorithms_part2_princeton_university/test/hypernyms3.txt"
        );

        assertThrows(IllegalArgumentException.class, () -> {
            wordnet.distance("a", "invalid");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            wordnet.distance("invalid", "a");
        });
    }

    @Test
    void distance() {
        WordNet wordnet = new WordNet(
            this.directory + "/algorithms_part2_princeton_university/test/synsets6.txt",
            this.directory + "/algorithms_part2_princeton_university/test/hypernyms6.txt"
        );

        assertEquals(2, wordnet.distance("b", "f"));

        wordnet = new WordNet(
            this.directory + "/algorithms_part2_princeton_university/test/synsets12.txt",
            this.directory + "/algorithms_part2_princeton_university/test/hypernyms12.txt"
        );

        assertEquals(4, wordnet.distance("d", "l"));

        wordnet = new WordNet(
            this.directory + "/algorithms_part2_princeton_university/test/synsets.txt",
            this.directory + "/algorithms_part2_princeton_university/test/hypernyms.txt"
        );

        assertEquals(14, wordnet.distance("genus_Arenaria", "breadfruit"));
    }

    @Test
    void sap_nullInputs() {
        WordNet wordnet = new WordNet(
            this.directory + "/algorithms_part2_princeton_university/test/synsets3.txt",
            this.directory + "/algorithms_part2_princeton_university/test/hypernyms3.txt"
        );

        assertThrows(IllegalArgumentException.class, () -> {
            wordnet.sap(null, "a");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            wordnet.sap("a", null);
        });
    }

    @Test
    void sap_invalidWords() {
        WordNet wordnet = new WordNet(
            this.directory + "/algorithms_part2_princeton_university/test/synsets3.txt",
            this.directory + "/algorithms_part2_princeton_university/test/hypernyms3.txt"
        );

        assertThrows(IllegalArgumentException.class, () -> {
            wordnet.sap("a", "invalid");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            wordnet.sap("invalid", "a");
        });
    }

    @Test
    void sap() {
        WordNet wordnet = new WordNet(
            this.directory + "/algorithms_part2_princeton_university/test/synsets6.txt",
            this.directory + "/algorithms_part2_princeton_university/test/hypernyms6.txt"
        );

        assertEquals("a", wordnet.sap("b", "f"));

        wordnet = new WordNet(
            this.directory + "/algorithms_part2_princeton_university/test/synsets12.txt",
            this.directory + "/algorithms_part2_princeton_university/test/hypernyms12.txt"
        );

        assertEquals("b", wordnet.sap("d", "l"));

        wordnet = new WordNet(
            this.directory + "/algorithms_part2_princeton_university/test/synsets.txt",
            this.directory + "/algorithms_part2_princeton_university/test/hypernyms.txt"
        );

        assertEquals("whole unit", wordnet.sap("stapling_machine", "songbird"));
    }
}
