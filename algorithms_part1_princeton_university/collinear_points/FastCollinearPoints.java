/******************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints
 *  Dependencies: none
 *
 *  Find collinear points using a faster, sorting based method.
 ******************************************************************************/

// import edu.princeton.cs.algs4.StdOut;

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

        Point[] copyOfPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(copyOfPoints);

        for (int i = 1; i < copyOfPoints.length; i++) {
            if (copyOfPoints[i].compareTo(copyOfPoints[i - 1]) == 0) {
                throw new IllegalArgumentException("duplicate points are not allowed");
            }
        }

        this.segments = new ArrayList<>();
        Point[] secondCopyOfPoints = Arrays.copyOf(copyOfPoints, points.length);
        int j;

        for (Point currentPoint : copyOfPoints) {
            Arrays.sort(secondCopyOfPoints, currentPoint.slopeOrder());
            int numberOfCollinearPoints = 0;
            double previousSlope = Double.NEGATIVE_INFINITY;
            double ignoredSlope = Double.NEGATIVE_INFINITY;
            Point largestPointInSequence = null;

            for (j = 0; j < secondCopyOfPoints.length; j++) {
                double currentSlope = currentPoint.slopeTo(secondCopyOfPoints[j]);

                if (currentPoint.compareTo(secondCopyOfPoints[j]) < 0) {
                    // If the slope matches and should not be ignored
                    if ((previousSlope == Double.NEGATIVE_INFINITY || Double.compare(currentSlope, previousSlope) == 0)
                            && (ignoredSlope == Double.NEGATIVE_INFINITY || Double.compare(currentSlope, ignoredSlope) != 0)) {
                        numberOfCollinearPoints++;

                        if (largestPointInSequence == null || secondCopyOfPoints[j].compareTo(largestPointInSequence) > 0) {
                            largestPointInSequence = secondCopyOfPoints[j];
                        }
                    } else {
                        if (numberOfCollinearPoints >= 3) {
                            this.addLineSegment(currentPoint, largestPointInSequence);
                        }

                        // This point is collinear with the currentPoint by itself
                        numberOfCollinearPoints = 1;
                        largestPointInSequence = secondCopyOfPoints[j];
                    }

                    previousSlope = currentPoint.slopeTo(secondCopyOfPoints[j]);
                } else if (currentPoint.compareTo(secondCopyOfPoints[j]) > 0) {
                    // Ignore any other points that share this slope because we know that currentPoint is >
                    // secondCopyOfPoints[j] so points that share the same slope cannot belong to the maximal segment.
                    ignoredSlope = currentSlope;

                    // Only add the line segment if the slope between currentPoint and secondCopyOfPoints[j] doesn't match.
                    // If it does match then the previous line segment shares the same slope but currentPoint is >
                    // secondCopyOfPoints[j] so this cannot be the maximal segment.
                    if (numberOfCollinearPoints >= 3 && Double.compare(currentSlope, previousSlope) != 0) {
                        this.addLineSegment(currentPoint, largestPointInSequence);
                    }

                    numberOfCollinearPoints = 0;
                    largestPointInSequence = null;
                }
            }

            // If the counter never reset and we reached the end, don't forget to add the final segment.
            if (numberOfCollinearPoints >= 3) {
                if (secondCopyOfPoints[j - 1] != currentPoint && secondCopyOfPoints[j - 1].compareTo(largestPointInSequence) > 0) {
                    largestPointInSequence = secondCopyOfPoints[j - 1];
                }

                this.addLineSegment(currentPoint, largestPointInSequence);
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

    /**
     * Add a line segment from start to end
     *
     * @param start the starting point
     * @param end the ending point
     */
    private void addLineSegment(Point start, Point end) {
        this.segments.add(new LineSegment(start, end));
    }
}
