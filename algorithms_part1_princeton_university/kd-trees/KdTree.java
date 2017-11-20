/******************************************************************************
 *  Compilation:  javac KdTree.java
 *  Execution:    java KdTree
 *  Dependencies: java.util.ArrayList, java.util.Comparator
 *
 *  Kd Tree range and nearest solver for a set of points
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Comparator;

public class KdTree {
    private Node root;
    private int size;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node leftBottom;
        private Node rightTop;
    }

    public KdTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * @return whether or not the tree is empty
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    /**
     * @return the size of the tree
     */
    public int size() {
        return this.size;
    }

    /**
     * @param p The point to be inserted
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        // insert the point into the tree if it doesn't already exist
        if (!this.contains(p)) {
            Node node = new Node();
            node.p = p;

            this.root = this.insert(this.root, node, null, 0, 0);
            this.size++;
        }
    }

    /**
     * @param x The node to check against
     * @param node The node to insert
     * @param previousParent The previous parent
     * @param previousCmp The previous comparison
     * @param orientation The current orientation
     * @return The inserted node
     */
    private Node insert(Node x, Node node, Node previousParent, int previousCmp, int orientation) {
        if (x == null) {
            // x is null so make sure to use the previous orientation
            node.rect = this.createRectHV(previousParent, previousCmp, (orientation + 1) & 1);

            return node;
        }

        int cmp = this.getComparatorFromDepth(orientation).compare(node.p, x.p);

        if (cmp < 0) {
            x.leftBottom = this.insert(x.leftBottom, node, x, cmp, (orientation + 1) & 1);
        } else {
            // If the point matches but it's not the same (i.e. the x coordinate or y coordinate match based on the
            // comparator but the other coordinate does not), always go right.
            x.rightTop = this.insert(x.rightTop, node, x, cmp, (orientation + 1) & 1);
        }

        return x;
    }

    /**
     * @param p The point to check
     * @return whether or not the point is found
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (this.isEmpty()) {
            return false;
        }

        return this.get(p) != null;
    }

    /**
     * @param p The point to search for
     * @return The node that holds p or null
     */
    private Node get(Point2D p) {
        return get(this.root, p, 0);
    }

    /**
     * @param x The node the search
     * @param p The point to search for
     * @param orientation The current orientation
     * @return The node that holds p or null
     */
    private Node get(Node x, Point2D p, int orientation) {
        if (x == null) {
            return null;
        }

        if (x.p.equals(p)) {
            return x;
        }

        int cmp = this.getComparatorFromDepth(orientation).compare(p, x.p);

        if (cmp < 0) {
            return this.get(x.leftBottom, p, (orientation + 1) & 1);
        }

        // If the point matches but it's not the same (i.e. the x coordinate or y coordinate match based on the
        // comparator but the other coordinate does not), always go right.
        return this.get(x.rightTop, p, (orientation + 1) & 1);
    }

    public void draw() {
        this.draw(this.root, 0);
    }

    /**
     * @param x The node
     * @param orientation The orientation
     */
    private void draw(Node x, int orientation) {
        if (x == null) {
            return;
        }

        // Draw the point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(x.p.x(), x.p.y());

        StdDraw.setPenRadius();

        // Draw the line
        if (orientation == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }

        this.draw(x.rightTop, (orientation + 1) & 1);
        this.draw(x.leftBottom, (orientation + 1) & 1);
    }

    /**
     * @param rect The rectangle to search for points in
     * @return The points found inside of the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        // all points that are inside the rectangle (or on the boundary)
        ArrayList<Point2D> points = new ArrayList<>();
        points = this.range(rect, this.root, points);

        return points;
    }

    /**
     * @param rect The rectangle to search for points in
     * @param node The current node in the tree
     * @param points The points found inside of the rectangle
     * @return The points found inside of the rectangle
     */
    private ArrayList<Point2D> range(RectHV rect, Node node, ArrayList<Point2D> points) {
        if (node == null) {
            return points;
        }

        if (node.rect.intersects(rect)) {
            if (rect.contains(node.p)) {
                points.add(node.p);
            }

            points = this.range(rect, node.leftBottom, points);
            points = this.range(rect, node.rightTop, points);
        }

        return points;
    }

    /**
     * @param p The query point
     * @return The closest point
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (this.isEmpty()) {
            return null;
        }

        return this.nearest(this.root, p, null, 0);
    }

    /**
     * @param node The node that contains the current point to compare against
     * @param p The query point
     * @param closest The closest point found so far
     * @param orientation The orientation
     * @return The closest point
     */
    private Point2D nearest(Node node, Point2D p, Point2D closest, int orientation) {
        if (node == null) {
            return closest;
        }

        // If the node's point is the query point, go down both subtrees.
        if (node.p == p) {
            closest = this.nearest(node.leftBottom, p, closest, (orientation + 1) & 1);
            closest = this.nearest(node.rightTop, p, closest, (orientation + 1) & 1);
        } else {
            double distanceToRectangle = node.rect.distanceSquaredTo(p);
            double closestDistance = closest != null ? closest.distanceSquaredTo(p) : Double.POSITIVE_INFINITY;

            // if the closest point discovered so far is closer than the distance between the query point and the
            // rectangle corresponding to a node, there is no need to explore that node (or its subtrees)
            if (distanceToRectangle <= closestDistance) {
                double distanceToPoint = node.p.distanceSquaredTo(p);

                if (distanceToPoint < closestDistance) {
                    closest = node.p;
                }

                // When there are two possible subtrees to go down, you always choose the subtree that is on the same
                // side of the splitting line as the query point as the first subtree to explore.
                if (node.leftBottom != null && node.rightTop != null) {
                    if (orientation == 0 && node.p.x() > p.x()
                        || orientation == 1 && node.p.y() > p.y()
                    ) {
                        // If the current node's point runs parallel to the x-axis and the current node's point's
                        // y-value is > the query point's y-value, the query point falls below the splitting line.
                        // If the current node's point runs parallel to the y-axis and the current node's point's
                        // x-value is > the query point's x-value, the query point falls to the left of the splitting
                        // line.
                        closest = this.nearest(node.leftBottom, p, closest, (orientation + 1) & 1);
                        closest = this.nearest(node.rightTop, p, closest, (orientation + 1) & 1);
                    } else {
                        // If the current node's point runs parallel to the x-axis and the current node's point's
                        // y-value is <= the query point's y-value, the query point falls above the splitting line.
                        // If the current node's point runs parallel to the y-axis and the current node's point's
                        // x-value is <= the query point's x-value, the query point falls to the right of the splitting
                        // line.
                        closest = this.nearest(node.rightTop, p, closest, (orientation + 1) & 1);
                        closest = this.nearest(node.leftBottom, p, closest, (orientation + 1) & 1);
                    }
                } else {
                    // Even though we know that leftBottom or rightTop is empty we will let the recursive call handle
                    // the null case to simplify this if/else condition.
                    if (node.leftBottom != null) {
                        closest = this.nearest(node.leftBottom, p, closest, (orientation + 1) & 1);
                    }

                    if (node.rightTop != null) {
                        closest = this.nearest(node.rightTop, p, closest, (orientation + 1) & 1);
                    }
                }
            }
        }

        return closest;
    }

    /**
     * @param orientation The orientation
     * @return The comparator
     */
    private Comparator<Point2D> getComparatorFromDepth(int orientation) {
        if (orientation == 0) {
            return Point2D.X_ORDER;
        }

        return Point2D.Y_ORDER;
    }

    /**
     * @param parent The parent node
     * @param previousCmp The previous comparison
     * @param orientation The orientation
     * @return
     */
    private RectHV createRectHV(Node parent, int previousCmp, int orientation) {
        // Root rectangle
        if (parent == null) {
            return new RectHV(0, 0, 1, 1);
        }

        RectHV parentRectangle = parent.rect;
        Point2D parentPoint = parent.p;

        if (orientation == 0 && previousCmp < 0) {
            // The new point falls to the left of its parent, which means the parent runs parallel to the y-axis.
            // Modify the new rectangles max x value to be the parent point's x value.
            return new RectHV(parentRectangle.xmin(), parentRectangle.ymin(), parentPoint.x(), parentRectangle.ymax());
        } else if (orientation == 0) {
            // This covers both when the point is truly to the right of its parent and also when its x-value matches
            // that of its parent.  In the case where both x values match, always go right.
            // The new point falls to the right of its parent, which means the parent runs parallel to the y-axis.
            // Modify the new rectangles min x value to be the parent point's x value.
            return new RectHV(parentPoint.x(), parentRectangle.ymin(), parentRectangle.xmax(), parentRectangle.ymax());
        } else if (orientation == 1 && previousCmp < 0) {
            // The new point falls below its parent, which means the parent runs parallel to the x-axis.
            // Modify the max y value to be the parent point's y value.
            return new RectHV(parentRectangle.xmin(), parentRectangle.ymin(), parentRectangle.xmax(), parentPoint.y());
        } else {
            // The new point falls above its parent, which means the parent runs parallel to the x-axis.
            // Modify the min y value to be the parent point's y value.
            return new RectHV(parentRectangle.xmin(), parentPoint.y(), parentRectangle.xmax(), parentRectangle.ymax());
        }
    }
}
