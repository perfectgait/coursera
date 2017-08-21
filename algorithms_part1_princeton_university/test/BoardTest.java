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
    }

    @Test
    void equals() {
    }

    @Test
    void neighbors() {
    }
}