import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    @Test
    void dimension() {
        Board board = new Board(new int[3][3]);

        assertEquals(3, board.dimension());
    }

    @Test
    void hamming() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 8;
        blocks[0][1] = 1;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 0;
        blocks[1][2] = 2;
        blocks[2][0] = 7;
        blocks[2][1] = 6;
        blocks[2][2] = 5;

        Board board = new Board(blocks);

        assertEquals(5, board.hamming());
    }

    @Test
    void manhattan() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 8;
        blocks[0][1] = 1;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 0;
        blocks[1][2] = 2;
        blocks[2][0] = 7;
        blocks[2][1] = 6;
        blocks[2][2] = 5;

        Board board = new Board(blocks);

        assertEquals(10, board.manhattan());
    }

    @Test
    void isGoal() {
        int[][] nonGoalBlocks = new int[3][3];
        nonGoalBlocks[0][0] = 8;
        nonGoalBlocks[0][1] = 1;
        nonGoalBlocks[0][2] = 3;
        nonGoalBlocks[1][0] = 4;
        nonGoalBlocks[1][1] = 0;
        nonGoalBlocks[1][2] = 2;
        nonGoalBlocks[2][0] = 7;
        nonGoalBlocks[2][1] = 6;
        nonGoalBlocks[2][2] = 5;

        Board nonGoalBoard = new Board(nonGoalBlocks);

        assertFalse(nonGoalBoard.isGoal());

        int[][] goalBlocks = new int[3][3];
        nonGoalBlocks[0][0] = 1;
        nonGoalBlocks[0][1] = 2;
        nonGoalBlocks[0][2] = 3;
        nonGoalBlocks[1][0] = 4;
        nonGoalBlocks[1][1] = 5;
        nonGoalBlocks[1][2] = 6;
        nonGoalBlocks[2][0] = 7;
        nonGoalBlocks[2][1] = 8;
        nonGoalBlocks[2][2] = 0;

        Board goalBoard = new Board(goalBlocks);

        assertTrue(goalBoard.isGoal());
    }

    @Test
    void twin() {
        int numberOfDifferences = 0;
        int[][] blocks = new int[3][3];
        blocks[0][0] = 8;
        blocks[0][1] = 1;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 0;
        blocks[1][2] = 2;
        blocks[2][0] = 7;
        blocks[2][1] = 6;
        blocks[2][2] = 5;

        Board board = new Board(blocks);
        Board twin = board.twin();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.blocks[i][j] != twin.blocks[i][j]) {
                    numberOfDifferences++;
                }
            }
        }

        assertEquals(2, numberOfDifferences);
    }

    @Test
    void equalsWithSameBoard() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 8;
        blocks[0][1] = 1;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 0;
        blocks[1][2] = 2;
        blocks[2][0] = 7;
        blocks[2][1] = 6;
        blocks[2][2] = 5;

        Board board = new Board(blocks);

        assertTrue(board.equals(board));
    }

    @Test
    void equalsWithNullBoard() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 8;
        blocks[0][1] = 1;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 0;
        blocks[1][2] = 2;
        blocks[2][0] = 7;
        blocks[2][1] = 6;
        blocks[2][2] = 5;

        Board board = new Board(blocks);

        assertFalse(board.equals(null));
    }

    @Test
    void equalsWithDifferentObjectType() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 8;
        blocks[0][1] = 1;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 0;
        blocks[1][2] = 2;
        blocks[2][0] = 7;
        blocks[2][1] = 6;
        blocks[2][2] = 5;

        Board board = new Board(blocks);

        assertFalse(board.equals("hey"));
    }

    @Test
    void equalsWithDifferentBoardThatHasTheSameBlocks() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 8;
        blocks[0][1] = 1;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 0;
        blocks[1][2] = 2;
        blocks[2][0] = 7;
        blocks[2][1] = 6;
        blocks[2][2] = 5;

        Board board = new Board(blocks);
        Board board2 = new Board(blocks);

        assertTrue(board.equals(board2));
        assertTrue(board2.equals(board));
    }

    @Test
    void equalsWithDifferentBoardThatHasDifferentBlocks() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 8;
        blocks[0][1] = 1;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 0;
        blocks[1][2] = 2;
        blocks[2][0] = 7;
        blocks[2][1] = 6;
        blocks[2][2] = 5;

        Board board = new Board(blocks);

        int[][] blocks2 = new int[3][3];
        blocks[0][0] = 8;
        blocks[0][1] = 1;
        blocks[0][2] = 2;
        blocks[1][0] = 4;
        blocks[1][1] = 0;
        blocks[1][2] = 3;
        blocks[2][0] = 7;
        blocks[2][1] = 6;
        blocks[2][2] = 5;

        Board board2 = new Board(blocks2);

        assertFalse(board.equals(board2));
        assertFalse(board2.equals(board));
    }

    @Test
    void neighbors() {
    }

    @Test
    void testToString() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 8;
        blocks[0][1] = 1;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 0;
        blocks[1][2] = 2;
        blocks[2][0] = 7;
        blocks[2][1] = 6;
        blocks[2][2] = 5;

        Board board = new Board(blocks);

        assertEquals(
            "3" + System.lineSeparator() +
            " 8 1 3" + System.lineSeparator() +
            " 4 0 2" + System.lineSeparator() +
            " 7 6 5",
            board.toString()
        );
    }
}