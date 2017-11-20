import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointSETTest {
    private PointSET pointSET;

    @BeforeEach
    void setUp() {
        this.pointSET = new PointSET();
    }

    @Test
    void isEmpty() {
        assertTrue(this.pointSET.isEmpty());

        this.pointSET.insert(new Point2D(0.5, 0.5));

        assertFalse(this.pointSET.isEmpty());
    }

    @Test
    void size() {
        assertEquals(0, this.pointSET.size());

        this.pointSET.insert(new Point2D(0.5, 0.5));

        assertEquals(1, this.pointSET.size());
    }

    @Test
    void insertDoesNotAllowNullPoint() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.pointSET.insert(null);
        });
    }

    @Test
    void insert() {
        Point2D point = new Point2D(0.5, 0.5);

        assertFalse(this.pointSET.contains(point));

        this.pointSET.insert(point);

        assertTrue(this.pointSET.contains(point));
    }

    @Test
    void containsDoesNotAllowNullPoint() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.pointSET.contains(null);
        });
    }

    @Test
    void contains() {
        Point2D point = new Point2D(0.5, 0.5);

        assertFalse(this.pointSET.contains(point));

        this.pointSET.insert(point);

        assertTrue(this.pointSET.contains(point));
    }

    @Test
    void rangeDoesNotAllowNullRectangle() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.pointSET.range(null);
        });
    }

    @Test
    void rangeReturnsAnEmptyIteratorIfNoPointsAreFound() {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        PointSET set = new PointSET();
        Iterable<Point2D> points = set.range(rect);
        int numberOfPointsFound = 0;

        for (Point2D point : points) {
            numberOfPointsFound++;
        }

        assertEquals(0, numberOfPointsFound);
    }

    @Test
    void range() {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        Point2D topEdgePoint = new Point2D(0.5, 1.0);
        Point2D rightEdgePoint = new Point2D(1.0, 0.5);
        Point2D bottomEdgePoint = new Point2D(0.5, 0);
        Point2D leftEdgePoint = new Point2D(0.0, 0.5);
        Point2D insidePoint = new Point2D(0.5, 0.5);
        Point2D outsidePoint = new Point2D(1.5, 1.5);
        PointSET set = new PointSET();
        set.insert(topEdgePoint);
        set.insert(rightEdgePoint);
        set.insert(bottomEdgePoint);
        set.insert(leftEdgePoint);
        set.insert(insidePoint);
        set.insert(outsidePoint);

        Iterable<Point2D> points = set.range(rect);
        int numberOfPointsFound = 0;

        for (Point2D point : points) {
            numberOfPointsFound++;

            if (point != topEdgePoint
                && point != rightEdgePoint
                && point != bottomEdgePoint
                && point != leftEdgePoint
                && point != insidePoint
            ) {
                fail("Invalid point found in the set");
            }
        }

        assertEquals(5, numberOfPointsFound);
    }

    @Test
    void nearestDoesNotAllowNullPoint() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.pointSET.nearest(null);
        });
    }

    @Test
    void nearestReturnsNullIfTheSetIsEmpty() {
        PointSET set = new PointSET();

        assertNull(set.nearest(new Point2D(0.5, 0.5)));
    }

    @Test
    void nearest() {
        PointSET set = new PointSET();
        Point2D expectedNearestPoint = new Point2D(0.5, 0.5);
        set.insert(new Point2D(0.0, 0.0));
        set.insert(new Point2D(0.25, 0.25));
        set.insert(expectedNearestPoint);
        set.insert(new Point2D(0.75, 0.75));
        set.insert(new Point2D(1.0, 1.0));

        assertEquals(expectedNearestPoint, set.nearest(new Point2D(0.51, 0.51)));
    }

}
