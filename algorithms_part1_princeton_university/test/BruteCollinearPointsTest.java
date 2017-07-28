import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BruteCollinearPointsTest {
    @Test
    void pointsCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new BruteCollinearPoints(null);
        });
    }

    @Test
    void noSinglePointCanBeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            Point[] pointsWithNull = new Point[1];

            new BruteCollinearPoints(pointsWithNull);
        });
    }

    @Test
    void duplicatePointsAreNotAllowed() {
        assertThrows(IllegalArgumentException.class, () -> {
            Point[] duplicatePoints = new Point[2];
            duplicatePoints[0] = new Point(10, 10);
            duplicatePoints[1] = new Point(10, 10);

            new BruteCollinearPoints(duplicatePoints);
        });
    }

    @Test
    void noCollinearPoints() {
        Point[] points = new Point[4];
        points[0] = new Point(5, 6);
        points[1] = new Point(10, 12);
        points[2] = new Point(8, 8);
        points[3] = new Point(-7, -6);

        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);

        assertEquals(0, bruteCollinearPoints.numberOfSegments());
        assertEquals(0, bruteCollinearPoints.segments().length);
    }

    @Test
    void collinearPointsWithSameOrigin() {
        Point origin = new Point(5, 6);
        Point destination = new Point(8, 9);
        Point[] points = new Point[5];
        points[0] = origin;
        points[1] = new Point(6, 7);
        points[2] = new Point(7, 8);
        points[3] = destination;
        points[4] = new Point(15, -3);

        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);
        LineSegment[] segments = bruteCollinearPoints.segments();

        assertEquals(1, bruteCollinearPoints.numberOfSegments());
        assertEquals(1, segments.length);
        assertEquals(origin + " -> " + destination, segments[0].toString());
    }

    @Test
    void collinearPointsWithDifferentOrigins() {

    }

    @Test
    void numberOfSegments() {
    }

    @Test
    void segments() {
    }
}