import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OutcastTest {

    @Test
    void outcast() {
        WordNet wordNet = new WordNet(
            System.getProperty("user.dir") + "/algorithms_part2_princeton_university/test/synsets.txt",
            System.getProperty("user.dir") + "/algorithms_part2_princeton_university/test/hypernyms.txt"
        );
        Outcast outcast = new Outcast(wordNet);
        String[] firstSet = new String[5];
        firstSet[0] = "horse";
        firstSet[1] = "zebra";
        firstSet[2] = "cat";
        firstSet[3] = "bear";
        firstSet[4] = "table";

        assertEquals("table", outcast.outcast(firstSet));

        String[] secondSet = new String[8];
        secondSet[0] = "water";
        secondSet[1] = "soda";
        secondSet[2] = "bed";
        secondSet[3] = "orange_juice";
        secondSet[4] = "milk";
        secondSet[5] = "apple_juice";
        secondSet[6] = "tea";
        secondSet[7] = "coffee";

        assertEquals("bed", outcast.outcast(secondSet));

        String[] thirdSet = new String[11];
        thirdSet[0] = "apple";
        thirdSet[1] = "pear";
        thirdSet[2] = "peach";
        thirdSet[3] = "banana";
        thirdSet[4] = "lime";
        thirdSet[5] = "lemon";
        thirdSet[6] = "blueberry";
        thirdSet[7] = "strawberry";
        thirdSet[8] = "mango";
        thirdSet[9] = "watermelon";
        thirdSet[10] = "potato";

        assertEquals("potato", outcast.outcast(thirdSet));
    }
}
