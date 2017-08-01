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
    private ArrayList<LineSegment> segments;
    private int segmentCount;

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

        Arrays.sort(points);

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("no points may be null");
            }

            if (i > 0 && points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("duplicate points are not allowed");
            }
        }

        this.segments = new ArrayList<>();
        this.segmentCount = 0;

        for (int j = 0; j < points.length; j++) {
            for (int k = j + 1; k < points.length; k++) {
                double slope1 = points[j].slopeTo(points[k]);

                for (int l = k + 1; l < points.length; l++) {
                    // If the first two slopes don't match, do nothing
                    if (slope1 == points[j].slopeTo(points[l])) {
                        for (int m = l + 1; m < points.length; m++) {
                            // All of the slopes match and there are 4 points
                            if (slope1 == points[j].slopeTo(points[m])) {
                                this.segments.add(new LineSegment(points[j], points[m]));
                                this.segmentCount++;
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
        return this.segmentCount;
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
