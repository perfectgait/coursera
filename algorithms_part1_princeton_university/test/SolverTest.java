import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {
    @Test
    void doesNotAllowNullBoard() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Solver(null);
        });
    }

    @Test
    void checksIfInitialBoardIsGoal() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 1;
        blocks[0][1] = 2;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 5;
        blocks[1][2] = 6;
        blocks[2][0] = 7;
        blocks[2][1] = 8;
        blocks[2][2] = 0;

        Board board = new Board(blocks);
        Solver solver = new Solver(board);
        Iterable<Board> solution = solver.solution();
        int numberOfBoardsInSolution = 0;

        assertTrue(solver.isSolvable());
        assertEquals(0, solver.moves());

        for (Board step : solution) {
            numberOfBoardsInSolution++;
        }

        assertEquals(1, numberOfBoardsInSolution);
    }

    @Test
    void detectsUnsolvableBoard() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 1;
        blocks[0][1] = 2;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 5;
        blocks[1][2] = 6;
        blocks[2][0] = 8;
        blocks[2][1] = 7;
        blocks[2][2] = 0;

        Board board = new Board(blocks);
        Solver solver = new Solver(board);

        assertFalse(solver.isSolvable());
        assertEquals(-1, solver.moves());
        assertNull(solver.solution());
    }

    @Test
    void detectsSolvableBoard() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 0;
        blocks[0][1] = 1;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 2;
        blocks[1][2] = 5;
        blocks[2][0] = 7;
        blocks[2][1] = 8;
        blocks[2][2] = 6;

        Board board = new Board(blocks);
        Solver solver = new Solver(board);
        Iterable<Board> solution = solver.solution();
        int numberOfBoardsInSolution = 0;

        assertTrue(solver.isSolvable());
        assertEquals(4, solver.moves());

        for (Board step : solution) {
            numberOfBoardsInSolution++;
        }

        assertEquals(5, numberOfBoardsInSolution);
    }
}