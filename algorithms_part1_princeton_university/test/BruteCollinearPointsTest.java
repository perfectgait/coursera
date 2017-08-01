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
            Point[] duplicatePoints = new Point[4];
            duplicatePoints[0] = new Point(10, 10);
            duplicatePoints[1] = new Point(0, 10);
            duplicatePoints[2] = new Point(10, 0);
            duplicatePoints[3] = new Point(10, 10);

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
        Point destination2 = new Point(5, 9);
        Point[] points = new Point[7];
        points[0] = origin;
        points[1] = new Point(6, 7);
        points[2] = new Point(7, 8);
        points[3] = destination;
        points[4] = new Point(5, 7);
        points[5] = new Point(5, 8);
        points[6] = destination2;

        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);
        LineSegment[] segments = bruteCollinearPoints.segments();

        assertEquals(2, bruteCollinearPoints.numberOfSegments());
        assertEquals(2, segments.length);

        for (LineSegment segment : segments) {
            if (!segment.toString().equals(origin + " -> " + destination)
                    && !segment.toString().equals(origin + " -> " + destination2)) {
                fail("Invalid line segment: " + segment);
            }
        }
    }

    @Test
    void collinearPointsWithDifferentOrigins() {
        Point origin = new Point(0, 0);
        Point destination = new Point(0, 3);
        Point origin2 = new Point(10, 10);
        Point destination2 = new Point(10, 7);
        Point[] points = new Point[8];
        points[0] = origin;
        points[1] = new Point(0, 1);
        points[2] = new Point(0, 2);
        points[3] = destination;
        points[4] = origin2;
        points[5] = new Point(10, 9);
        points[6] = new Point(10, 8);
        points[7] = destination2;

        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);
        LineSegment[] segments = bruteCollinearPoints.segments();

        assertEquals(2, bruteCollinearPoints.numberOfSegments());
        assertEquals(2, segments.length);

        for (LineSegment segment : segments) {
            if (!segment.toString().equals(origin + " -> " + destination)
                    // These points should be reversed because destination2 is < origin2
                    && !segment.toString().equals(destination2 + " -> " + origin2)) {
                fail("Invalid line segment: " + segment);
            }
        }
    }

    @Test
    void collinearPointsThatIntersect() {
        Point origin = new Point(-1, 0);
        Point destination = new Point(2, 0);
        Point origin2 = new Point(0, 1);
        Point destination2 = new Point(0, -2);
        Point[] points = new Point[7];
        points[0] = origin;
        // This is the point of intersection
        points[1] = new Point(0, 0);
        points[2] = new Point(1, 0);
        points[3] = destination;
        points[4] = origin2;
        points[5] = new Point(0, -1);
        points[6] = destination2;

        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);
        LineSegment[] segments = bruteCollinearPoints.segments();

        assertEquals(2, bruteCollinearPoints.numberOfSegments());
        assertEquals(2, segments.length);

        for (LineSegment segment : segments) {
            if (!segment.toString().equals(origin + " -> " + destination)
                    // These points should be reversed because destination2 is < origin2
                    && !segment.toString().equals(destination2 + " -> " + origin2)) {
                fail("Invalid line segment: " + segment);
            }
        }
    }
}
