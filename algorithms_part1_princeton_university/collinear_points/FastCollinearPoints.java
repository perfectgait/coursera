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
    private final ArrayList<LineSegment> segments;

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

        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("no points may be null");
            }
        }

        Arrays.sort(points);

        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("duplicate points are not allowed");
            }
        }

        this.segments = new ArrayList<>();
        Point[] copyOfPoints = Arrays.copyOf(points, points.length);
        int j;

//        for (int i = 0; i < points.length; i++) {
        for (Point currentPoint : points) {
//            Point currentPoint = points[i];

            StdOut.println("=============================");
            StdOut.println("currentPoint: " + currentPoint);

            // @TODO If currentPoint is not included in copyOfPoints, the code below can be simplified.

            Arrays.sort(copyOfPoints, currentPoint.slopeOrder());
            int numberOfCollinearPoints = 0;
//            int currentPointIndex = 0;
            double previousSlope = Double.NEGATIVE_INFINITY;
            double ignoredSlope = Double.NEGATIVE_INFINITY;

            for (j = 0; j < copyOfPoints.length; j++) {
                // Ignore the degenerate segment
//                if (copyOfPoints[j] != currentPoint) {

                double currentSlope = currentPoint.slopeTo(copyOfPoints[j]);

                if (currentPoint.compareTo(copyOfPoints[j]) < 0) {
                    StdOut.print(copyOfPoints[j]);
//                    double currentSlope = currentPoint.slopeTo(copyOfPoints[j]);

                    // If currentPoint is larger than copyOfPoints[j], currentPoint is not the first point in this
                    // segment.

                    // @TODO If currentPoint is larger than copyOfPoints[j], any other points that have the same slope should also be skipped
//                    if (currentPoint.compareTo(copyOfPoints[j]) >= 0) {
//                        numberOfCollinearPoints = 0;
//                        ignoredSlope = currentPoint.slopeTo(copyOfPoints[j]);
//                        StdOut.print("\n");
//                    } else {
                        StdOut.print(" slope: " + currentPoint.slopeTo(copyOfPoints[j]));

                        // If the slope matches and should not be ignored
                        if ((previousSlope == Double.NEGATIVE_INFINITY || Double.compare(currentSlope, previousSlope) == 0)
                                && (ignoredSlope == Double.NEGATIVE_INFINITY || Double.compare(currentSlope, ignoredSlope) != 0)) {
                            // If currentPoint is > copyOfPoints[j], currentPoint cannot be the first point in the
                            // segment.  This means that copyOfPoints[j] and all other points that share its slope with
                            // currentPoint should be ignored.  The line segment with that slope is not maximal.
//                            if (currentPoint.compareTo(copyOfPoints[j]) >= 0) {
//                                ignoredSlope = currentSlope;
//                                numberOfCollinearPoints = 0;
//                                StdOut.println(" slope matched but currentPoint is >= copyOfPoints[j]");
//                            } else {
                                numberOfCollinearPoints++;
                                StdOut.println(" slope matched and currentPoint is < copyOfPoints[j]");
//                            }
                        } else {
                            StdOut.println(" slope did not match or was ignored and numberOfCollinearPoints is: " + numberOfCollinearPoints);

                            // @TODO Fix this.  What about the case where the previous point is the currentPoint?
                            if (numberOfCollinearPoints >= 3) {
                                int indexOfEndPoint = copyOfPoints[j - 1] == currentPoint ? j - 2 : j - 1;
                                StdOut.println("Adding line segment from " + currentPoint + " to " + copyOfPoints[indexOfEndPoint]);
                                this.segments.add(new LineSegment(currentPoint, copyOfPoints[indexOfEndPoint]));
                            }

                            // This point is collinear with the currentPoint by itself
                            numberOfCollinearPoints = 1;
                        }
//                    }

                    previousSlope = currentPoint.slopeTo(copyOfPoints[j]);
                } else if (currentPoint.compareTo(copyOfPoints[j]) > 0) {
//                    currentPointIndex = j;
//                } else {
//                    StdOut.println(copyOfPoints[j] + " was ignored");
                    // Ignore any other points that share this slope because we know that currentPoint is >=
                    // copyOfPoints[j] so points that share the same slope cannot belong to the maximal segment.
                    ignoredSlope = currentSlope;

                    // @TODO Fix this.  What about the case where the previous point is the currentPoint?
                    if (numberOfCollinearPoints >= 3) {
                        int indexOfEndPoint = copyOfPoints[j - 1] == currentPoint ? j - 2 : j - 1;
                        StdOut.println("Adding line segment from " + currentPoint + " to " + copyOfPoints[indexOfEndPoint]);
                        this.segments.add(new LineSegment(currentPoint, copyOfPoints[indexOfEndPoint]));
                        numberOfCollinearPoints = 0;
                    }
                }
            }

            // If the counter never reset and we reached the end, don't forget to add the final segment.
            if (numberOfCollinearPoints >= 3) {
                // In case the last point in copyOfPoints is the currentPoint, rewind.
                while (copyOfPoints[j - 1] == currentPoint) {
                    j--;
                }

                StdOut.println("j: " + j);

                StdOut.println("Adding line segment from " + currentPoint + " to " + copyOfPoints[j - 1]);
                this.segments.add(new LineSegment(currentPoint, copyOfPoints[j - 1]));
            }
        }

//        StdOut.println("===============DONE==============");
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
