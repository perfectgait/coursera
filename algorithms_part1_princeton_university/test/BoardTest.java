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

        assertFalse(board.equals(twin));
        assertFalse(twin.equals(board));
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
        blocks2[0][0] = 8;
        blocks2[0][1] = 1;
        blocks2[0][2] = 2;
        blocks2[1][0] = 4;
        blocks2[1][1] = 0;
        blocks2[1][2] = 3;
        blocks2[2][0] = 7;
        blocks2[2][1] = 6;
        blocks2[2][2] = 5;

        Board board2 = new Board(blocks2);

        assertFalse(board.equals(board2));
        assertFalse(board2.equals(board));
    }

    @Test
    void equalsWithBoardsOfDifferentDimensions() {
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

        int[][] blocks2 = new int[4][4];
        blocks2[0][0] = 8;
        blocks2[0][1] = 1;
        blocks2[0][2] = 2;
        blocks2[0][3] = 9;
        blocks2[1][0] = 4;
        blocks2[1][1] = 0;
        blocks2[1][2] = 3;
        blocks2[1][3] = 10;
        blocks2[2][0] = 7;
        blocks2[2][1] = 6;
        blocks2[2][2] = 5;
        blocks2[2][3] = 11;

        Board board2 = new Board(blocks2);

        assertFalse(board.equals(board2));
        assertFalse(board2.equals(board));
    }

    @Test
    void neighborsWhenBlankSpaceIsInTheMiddle() {
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

        this.verifyNeighborAssertions(board, 4);
    }

    @Test
    void neighborsWhenBlankSpaceIsInTheTopRow() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 8;
        blocks[0][1] = 0;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 1;
        blocks[1][2] = 2;
        blocks[2][0] = 7;
        blocks[2][1] = 6;
        blocks[2][2] = 5;

        Board board = new Board(blocks);

        this.verifyNeighborAssertions(board, 3);
    }

    @Test
    void neighborsWhenBlankSpaceIsInTheRightColumn() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 8;
        blocks[0][1] = 3;
        blocks[0][2] = 2;
        blocks[1][0] = 4;
        blocks[1][1] = 1;
        blocks[1][2] = 0;
        blocks[2][0] = 7;
        blocks[2][1] = 6;
        blocks[2][2] = 5;

        Board board = new Board(blocks);

        this.verifyNeighborAssertions(board, 3);
    }

    @Test
    void neighborsWhenBlankSpaceIsInTheBottomRow() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 8;
        blocks[0][1] = 3;
        blocks[0][2] = 2;
        blocks[1][0] = 4;
        blocks[1][1] = 1;
        blocks[1][2] = 6;
        blocks[2][0] = 7;
        blocks[2][1] = 0;
        blocks[2][2] = 5;

        Board board = new Board(blocks);

        this.verifyNeighborAssertions(board, 3);
    }

    @Test
    void neighborsWhenBlankSpaceIsInTheLeftColumn() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 8;
        blocks[0][1] = 3;
        blocks[0][2] = 2;
        blocks[1][0] = 0;
        blocks[1][1] = 1;
        blocks[1][2] = 6;
        blocks[2][0] = 7;
        blocks[2][1] = 4;
        blocks[2][2] = 5;

        Board board = new Board(blocks);

        this.verifyNeighborAssertions(board, 3);
    }

    @Test
    void neighborsWhenBlankSpaceIsInTheTopLeftSpace() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 0;
        blocks[0][1] = 3;
        blocks[0][2] = 2;
        blocks[1][0] = 4;
        blocks[1][1] = 1;
        blocks[1][2] = 6;
        blocks[2][0] = 7;
        blocks[2][1] = 8;
        blocks[2][2] = 5;

        Board board = new Board(blocks);

        this.verifyNeighborAssertions(board, 2);
    }

    @Test
    void neighborsWhenBlankSpaceIsInTheTopRightSpace() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 2;
        blocks[0][1] = 3;
        blocks[0][2] = 0;
        blocks[1][0] = 4;
        blocks[1][1] = 1;
        blocks[1][2] = 6;
        blocks[2][0] = 7;
        blocks[2][1] = 8;
        blocks[2][2] = 5;

        Board board = new Board(blocks);

        this.verifyNeighborAssertions(board, 2);
    }

    @Test
    void neighborsWhenBlankSpaceIsInTheBottomRightSpace() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 5;
        blocks[0][1] = 3;
        blocks[0][2] = 2;
        blocks[1][0] = 4;
        blocks[1][1] = 1;
        blocks[1][2] = 6;
        blocks[2][0] = 7;
        blocks[2][1] = 8;
        blocks[2][2] = 0;

        Board board = new Board(blocks);

        this.verifyNeighborAssertions(board, 2);
    }

    @Test
    void neighborsWhenBlankSpaceIsInTheBottomLeftSpace() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 7;
        blocks[0][1] = 3;
        blocks[0][2] = 2;
        blocks[1][0] = 4;
        blocks[1][1] = 1;
        blocks[1][2] = 6;
        blocks[2][0] = 0;
        blocks[2][1] = 8;
        blocks[2][2] = 5;

        Board board = new Board(blocks);

        this.verifyNeighborAssertions(board, 2);
    }

    private void verifyNeighborAssertions(Board board, int expectedNumberOfNeighbors) {
        // @TODO Verify the expected neighbor boards

        Iterable<Board> neighbors = board.neighbors();
        int numberOfNeighbors = 0;

        for (Board neighbor : neighbors) {
            numberOfNeighbors++;
        }

        assertEquals(expectedNumberOfNeighbors, numberOfNeighbors);
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
            "8 1 3 " + System.lineSeparator() +
            "4 0 2 " + System.lineSeparator() +
            "7 6 5 " + System.lineSeparator(),
            board.toString()
        );
    }
}