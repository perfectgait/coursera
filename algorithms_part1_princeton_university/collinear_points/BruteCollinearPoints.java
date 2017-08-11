/******************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    java BruteCollinearPoints
 *  Dependencies: none
 *
 *  Find collinear points using brute force.
 ******************************************************************************/

import java.util.Arrays;
import java.util.ArrayList;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> segments;

    /**
     * Examine 4 points at a time and check whether they all lie on the same line segment.  If they do, store the line
     * segment.
     *
     * @param points the points to process
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points may not be null");
        }

        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("no points may be null");
            }
        }

        Point[] copyOfPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(copyOfPoints);

        for (int i = 1; i < copyOfPoints.length; i++) {
            if (copyOfPoints[i].compareTo(copyOfPoints[i - 1]) == 0) {
                throw new IllegalArgumentException("duplicate points are not allowed");
            }
        }

        this.segments = new ArrayList<>();

        for (int j = 0; j < copyOfPoints.length; j++) {
            for (int k = j + 1; k < copyOfPoints.length; k++) {
                double slope1 = copyOfPoints[j].slopeTo(copyOfPoints[k]);

                for (int m = k + 1; m < copyOfPoints.length; m++) {
                    // If the first two slopes don't match, do nothing
                    if (Double.compare(slope1, copyOfPoints[j].slopeTo(copyOfPoints[m])) == 0) {
                        for (int n = m + 1; n < copyOfPoints.length; n++) {
                            // All of the slopes match and there are 4 points
                            if (Double.compare(slope1, copyOfPoints[j].slopeTo(copyOfPoints[n])) == 0) {
                                this.segments.add(new LineSegment(copyOfPoints[j], copyOfPoints[n]));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the number of line segments found
     *
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return this.segments.size();
    }

    /**
     * Get the line segments found
     *
     * @return the line segments
     */
    public LineSegment[] segments() {
        return this.segments.toArray(new LineSegment[this.segments.size()]);
    }
}
