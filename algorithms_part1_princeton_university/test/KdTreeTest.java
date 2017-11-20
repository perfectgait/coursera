import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KdTreeTest {
    private KdTree kdTree;

    @BeforeEach
    void setUp() {
        this.kdTree = new KdTree();
    }

    @Test
    void isEmpty() {
        assertTrue(this.kdTree.isEmpty());

        this.kdTree.insert(new Point2D(0.5, 0.5));

        assertFalse(this.kdTree.isEmpty());
    }

    @Test
    void sizeIsNotAffectedByDuplicates() {
        assertEquals(0, this.kdTree.size());

        this.kdTree.insert(new Point2D(0.5, 0.5));

        assertEquals(1, this.kdTree.size());

        this.kdTree.insert(new Point2D(0.5, 0.5));

        assertEquals(1, this.kdTree.size());
    }

    @Test
    void size() {
        assertEquals(0, this.kdTree.size());

        this.kdTree.insert(new Point2D(0.875, 0.5625));
        this.kdTree.insert(new Point2D(0.1875, 1.0));
        this.kdTree.insert(new Point2D(0.875, 0.1875));

        assertEquals(3, this.kdTree.size());
    }

    @Test
    void insertDoesNotAllowNullPoint() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.kdTree.insert(null);
        });
    }

    @Test
    void insert() {
        Point2D point = new Point2D(0.7, 0.2);
        Point2D point2 = new Point2D(0.5, 0.4);
        Point2D point3 = new Point2D(0.2, 0.3);
        Point2D point4 = new Point2D(0.4, 0.7);
        Point2D point5 = new Point2D(0.9, 0.6);
        this.kdTree.insert(point);
        this.kdTree.insert(point2);
        this.kdTree.insert(point3);
        this.kdTree.insert(point4);
        this.kdTree.insert(point5);

        assertTrue(this.kdTree.contains(point));
        assertTrue(this.kdTree.contains(point2));
        assertTrue(this.kdTree.contains(point3));
        assertTrue(this.kdTree.contains(point4));
        assertTrue(this.kdTree.contains(point5));
    }

    @Test
    void containsDoesNotAllowNullPoint() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.kdTree.contains(null);
        });
    }

    @Test
    void contains() {
        this.kdTree.insert(new Point2D(0.25, 1.0));
        this.kdTree.insert(new Point2D(0.0, 0.0));
        this.kdTree.insert(new Point2D(0.5, 0.75));
        this.kdTree.insert(new Point2D(0.5, 0.5));
        this.kdTree.insert(new Point2D(0.5, 1.0));
        this.kdTree.insert(new Point2D(0.0, 0.25));
        this.kdTree.insert(new Point2D(0.0, 0.5));
        this.kdTree.insert(new Point2D(0.0, 0.75));
        this.kdTree.insert(new Point2D(1.0, 0.0));
        this.kdTree.insert(new Point2D(0.5, 0.25));

        assertTrue(this.kdTree.contains(new Point2D(0.0, 0.5)));
    }

    @Test
    void rangeDoesNotAllowNullRectangle() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.kdTree.range(null);
        });
    }

    @Test
    void rangeReturnsAnEmptyIteratorIfNoPointsAreFound() {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        KdTree tree = new KdTree();
        Iterable<Point2D> points = tree.range(rect);
        int numberOfPointsFound = 0;

        for (Point2D point : points) {
            numberOfPointsFound++;
        }

        assertEquals(0, numberOfPointsFound);
    }

    @Test
    void range() {
        Point2D point1 = new Point2D(0.7, 0.2);
        Point2D point2 = new Point2D(0.5, 0.4);
        Point2D point3 = new Point2D(0.2, 0.3);
        Point2D point4 = new Point2D(0.4, 0.7);
        Point2D point5 = new Point2D(0.9, 0.6);
        this.kdTree.insert(point1);
        this.kdTree.insert(point2);
        this.kdTree.insert(point3);
        this.kdTree.insert(point4);
        this.kdTree.insert(point5);

        Iterable<Point2D> points;
        RectHV rectangle;

        // Rectangle containing all the points
        RectHV allPointsRectangle = new RectHV(0.0, 0.0, 1.0, 1.0);
        this.assertNumberOfPointsMatch(5, this.kdTree.range(allPointsRectangle));

        // Rectangle containing a single point - contains the point (0.2, 0.3)
        rectangle = new RectHV(0.1, 0.2, 0.3, 0.4);
        points = this.kdTree.range(rectangle);
        this.assertNumberOfPointsMatch(1, points);

        for (Point2D point : points) {
            assertSame(point, point3);
        }

        // Rectangle containing some but not all points (0.7, 0.2) and (0.9, 0.6)
        rectangle = new RectHV(0.6, 0.1, 1.0, 0.7);
        points = this.kdTree.range(rectangle);
        this.assertNumberOfPointsMatch(2, points);

        for (Point2D point : points) {
            if (point != point1 && point != point5) {
                fail("Invalid point found");
            }
        }

        // Rectangle containing no points
        rectangle = new RectHV(0.0, 0.0, 0.1, 0.1);
        this.assertNumberOfPointsMatch(0, this.kdTree.range(rectangle));
    }

    @Test
    void nearestDoesNotAllowNullPoint() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.kdTree.nearest(null);
        });
    }

    @Test
    void nearestReturnsNullIfTheSetIsEmpty() {
        KdTree tree = new KdTree();

        assertNull(tree.nearest(new Point2D(0.5, 0.5)));
    }

    @Test
    void nearest() {
        Point2D point1 = new Point2D(0.7, 0.2);
        Point2D point2 = new Point2D(0.5, 0.4);
        Point2D point3 = new Point2D(0.2, 0.3);
        Point2D point4 = new Point2D(0.4, 0.7);
        Point2D point5 = new Point2D(0.9, 0.6);
        this.kdTree.insert(point1);
        this.kdTree.insert(point2);
        this.kdTree.insert(point3);
        this.kdTree.insert(point4);
        this.kdTree.insert(point5);

        assertEquals(point2, this.kdTree.nearest(point1));
        assertEquals(point1, this.kdTree.nearest(point2));
        assertEquals(point2, this.kdTree.nearest(point3));
        assertEquals(point2, this.kdTree.nearest(point4));
        assertEquals(point1, this.kdTree.nearest(point5));
    }

    /**
     * @param expectedCount The expected number of points
     * @param points The points
     */
    private void assertNumberOfPointsMatch(int expectedCount, Iterable<Point2D> points) {
        int numberOfPoints = 0;

        for (Point2D point : points) {
            numberOfPoints++;
        }

        assertEquals(expectedCount, numberOfPoints);
    }
}
