/******************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution:    java PointSET
 *  Dependencies: java.util.ArrayList, java.util.TreeSet
 *
 *  Brute force range and nearest solver for a set of points
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> points;

    public PointSET() {
        // create an empty set of points
        this.points = new TreeSet<>();
    }

    /**
     * @return whether or not the set is empty
     */
    public boolean isEmpty() {
        // is the set empty?
        return this.points.isEmpty();
    }

    /**
     * @return The size
     */
    public int size() {
        return this.points.size();
    }

    /**
     * @param p the point to insert into the set
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        // insert the point into the set if it doesn't already exist
        if (!this.points.contains(p)) {
            this.points.add(p);
        }
    }

    /**
     * @param p the point to check for
     * @return whether or not the set contains the point to check for
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return this.points.contains(p);
    }

    public void draw() {
        // @TODO I don't think this is correct
        if (!this.points.isEmpty()) {
            for (Point2D point : this.points) {
                StdDraw.point(point.x(), point.y());
            }
        }
    }

    /**
     * @param rect the rectangle to use as the search area
     * @return the points found that lie in the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        // all points that are inside the rectangle (or on the boundary)
        ArrayList<Point2D> rangePoints = new ArrayList<>();

        if (this.points.isEmpty()) {
            return rangePoints;
        }

        for (Point2D point : this.points) {
            if (rect.contains(point)) {
                rangePoints.add(point);
            }
        }

        return rangePoints;
    }

    /**
     * @param p the origin point to use to check for the nearest
     * @return the nearest point to the origin
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (this.points.isEmpty()) {
            return null;
        }

        double nearestDistance = Double.MAX_VALUE;
        Point2D nearestPoint = null;

        for (Point2D point : this.points) {
            if (p.distanceSquaredTo(point) < nearestDistance) {
                nearestPoint = point;
                nearestDistance = p.distanceSquaredTo(point);
            }
        }

        return nearestPoint;
    }
}
