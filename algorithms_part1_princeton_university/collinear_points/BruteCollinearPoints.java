import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int segmentCount;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points may not be null");
        }

        Point[] foundPoints = new Point[points.length];
        int i = 0;

        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("no points may be null");
            }

            if (Arrays.asList(foundPoints).contains(point)) {
                throw new IllegalArgumentException("duplicate points are not allowed");
            }

            foundPoints[i++] = point;
        }

        this.segments = new LineSegment[2];
        this.segmentCount = 0;
        int j = 0;

        // @TODO Sort the points MAYBE
        // @TODO Remember the indexes of the points that were used and don't look at them again.  MAYBE


        for (Point point1 : points) {
//            outerloop:

            for (Point point2 : points) {
                if (point1.compareTo(point2) < 0) {
                    double slope1 = point1.slopeTo(point2);

                    for (Point point3 : points) {
                        if (point2.compareTo(point3) < 0) {
                            // If the first two slopes are different there is no need to continue evaluation
                            if (slope1 == point1.slopeTo(point3)) {
                                for (Point point4 : points) {
                                    if (point3.compareTo(point4) < 0) {
                                        if (slope1 == point1.slopeTo(point4)) {
                                            this.segments[j++] = new LineSegment(point1, point4);
                                            StdOut.println(point1 + " " + point2 + " " + point3 + " " + point4);
                                            StdOut.println(point1 + " -> " + point2 + " with slope: " + slope1);
                                            StdOut.println(point1 + " -> " + point3 + " with slope: " + point1.slopeTo(point3));
                                            StdOut.println(point1 + " -> " + point4 + " with slope: " + point1.slopeTo(point4));
//                                            StdOut.println(lineSegment);

                                            // @TODO Since this first point is used as a starting point here, break out of the outer most for loop here.
//                                            break outerloop;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return this.segmentCount;
    }

    public LineSegment[] segments() {
        return this.segments;
    }
}