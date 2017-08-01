/******************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints
 *  Dependencies: none
 *
 *  Find collinear points using a faster, sorting based method.
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments;
    private int segmentCount;

    /**
     * Examine each point compared with all other points and sort them based on slope.  Then go through all sorted
     * points and search for sequences of 4 or more collinear points.  Whenever a sequence that is 4 or more is found,
     * a line segment is added.
     *
     * @param points the points to process
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points may not be null");
        }

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
        Point[] copyOfPoints = Arrays.copyOf(points, points.length);

        for (int i = 0; i < points.length; i++) {
            Point currentPoint = points[i];

            StdOut.println("=============================");
            StdOut.println("currentPoint: " + currentPoint);

            Arrays.sort(copyOfPoints, currentPoint.slopeOrder());
            int numberOfCollinearPoints = 0;
            int j;
            double previousSlope = Double.NEGATIVE_INFINITY;

            for (j = 0; j < copyOfPoints.length; j++) {
                if (points[j] != currentPoint) {
                    StdOut.println(points[j] + " slode: " + currentPoint.slopeTo(points[j]));

                    if (previousSlope == Double.NEGATIVE_INFINITY || currentPoint.slopeTo(copyOfPoints[j]) == previousSlope) {
                        numberOfCollinearPoints++;
                    } else {
                        if (numberOfCollinearPoints >= 3) {
                            this.segments.add(new LineSegment(currentPoint, copyOfPoints[j - 1]));
                            this.segmentCount++;
                        }

                        numberOfCollinearPoints = 0;
                    }

                    previousSlope = currentPoint.slopeTo(copyOfPoints[j]);
                }
            }

            // If the counter never reset and we reached the end, don't forget to add the final segment.
            if (numberOfCollinearPoints >= 3) {
                this.segments.add(new LineSegment(currentPoint, points[j - 1]));
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
