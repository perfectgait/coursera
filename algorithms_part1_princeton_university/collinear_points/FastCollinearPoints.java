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
        Point[] copyOfPoints = Arrays.copyOf(points, points.length);
        int j;

        for (int i = 0; i < points.length; i++) {
            Point currentPoint = points[i];

            StdOut.println("=============================");
            StdOut.println("currentPoint: " + currentPoint);

            Arrays.sort(copyOfPoints, currentPoint.slopeOrder());
            int numberOfCollinearPoints = 0;
            double previousSlope = Double.NEGATIVE_INFINITY;
            double ignoredSlope = Double.NEGATIVE_INFINITY;

            for (j = 0; j < copyOfPoints.length; j++) {
                if (copyOfPoints[j] != currentPoint) {
                    StdOut.print(copyOfPoints[j]);

                    // If currentPoint is larger than copyOfPoints[j], currentPoint is not the first point in this
                    // segment.

                    // @TODO If currentPoint is larger than copyOfPoints[j], any other points that have the same slope should also be skipped
                    if (currentPoint.compareTo(copyOfPoints[j]) >= 0) {
                        numberOfCollinearPoints = 0;
                        ignoredSlope = currentPoint.slopeTo(copyOfPoints[j]);
                        StdOut.print("\n");
                    } else {
                        StdOut.print(" slope: " + currentPoint.slopeTo(copyOfPoints[j]));

                        if ((previousSlope == Double.NEGATIVE_INFINITY || currentPoint.slopeTo(copyOfPoints[j]) == previousSlope)
                                && (ignoredSlope == Double.NEGATIVE_INFINITY || currentPoint.slopeTo(copyOfPoints[j]) != ignoredSlope)) {
                            numberOfCollinearPoints++;
                            StdOut.print(" slope matched and point less\n");
                        } else {
                            StdOut.print(" slope did not match and numberOfCollinearPoints is: " + numberOfCollinearPoints + "\n");
                            if (numberOfCollinearPoints >= 3) {
                                StdOut.println("Adding line segment from " + currentPoint + " to " + copyOfPoints[j - 1]);
                                this.segments.add(new LineSegment(currentPoint, copyOfPoints[j - 1]));
                                this.segmentCount++;
                            }

                            // This point is collinear with the currentPoint by itself
                            numberOfCollinearPoints = 1;
                        }
                    }

                    previousSlope = currentPoint.slopeTo(copyOfPoints[j]);
                }
            }

            // If the counter never reset and we reached the end, don't forget to add the final segment.
            if (numberOfCollinearPoints >= 3) {
                StdOut.println("Adding line segment from " + currentPoint + " to " + copyOfPoints[j - 1]);
                this.segments.add(new LineSegment(currentPoint, copyOfPoints[j - 1]));
            }
        }

        StdOut.println("===============DONE==============");
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
