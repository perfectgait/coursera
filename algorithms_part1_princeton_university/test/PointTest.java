import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    private Point point;
    private Comparator<Point> comparator;

    @BeforeEach
    void setUp() {
        this.point = new Point(0, 5);
        this.comparator = this.point.slopeOrder();
    }

    @Test
    void slopeTo_horizontalLine() {
        assertEquals(0.0, this.point.slopeTo(new Point(5, 5)));
        assertEquals(0.0, this.point.slopeTo(new Point(-5, 5)));
    }

    @Test
    void slopeTo_verticalLine() {
        assertEquals(Double.POSITIVE_INFINITY, this.point.slopeTo(new Point(0, 10)));
        assertEquals(Double.POSITIVE_INFINITY, this.point.slopeTo(new Point(0, -10)));
    }

    @Test
    void slopeTo_samePoint() {
        assertEquals(Double.NEGATIVE_INFINITY, this.point.slopeTo(this.point));
    }

    @Test
    void slopeTo() {
        Point point1 = new Point(10, 5);
        double slope1 = 0 / 10;
        assertEquals(slope1, this.point.slopeTo(point1));
        Point point2 = new Point(-10, 5);
        double slope2 = 0 / -10;
        assertEquals(slope2, this.point.slopeTo(point2));
        Point point3 = new Point(-10, -5);
        double slope3 = 1;
        assertEquals(slope3, this.point.slopeTo(point3));
        Point point4 = new Point(10, -5);
        double slope4 = -1;
        assertEquals(slope4, this.point.slopeTo(point4));
    }

    @Test
    void compareTo_samePoint() {
        assertEquals(0, this.point.compareTo(this.point));
    }

    @Test
    void compareTo_lessThan() {
        assertEquals(-1, this.point.compareTo(new Point(0, 6)));
        assertEquals(-1, this.point.compareTo(new Point(1, 5)));
    }

    @Test
    void compareTo_greaterThan() {
        assertEquals(1, this.point.compareTo(new Point(0, 4)));
        assertEquals(1, this.point.compareTo(new Point(-1, 5)));
    }

    @Test
    void slopeOrder_oneHorizontalLine() {
        Point horizontal = new Point(5, 5);
        Point nonHorizontal = new Point(10, 10);

        assertEquals(-1, this.comparator.compare(horizontal, nonHorizontal));
    }

    @Test
    void slopeOrder_twoHorizontalLines() {
        Point horizontal1 = new Point(5, 5);
        Point horizontal2 = new Point(10, 5);

        assertEquals(0, this.comparator.compare(horizontal1, horizontal2));
    }

    @Test
    void slopeOrder_oneVerticalLine() {
        Point vertical = new Point(0, 10);
        Point nonVertical = new Point(-4, 8);

        assertEquals(1, this.comparator.compare(vertical, nonVertical));
    }

    @Test
    void slopeOrder_twoVerticalLines() {
        Point vertical1 = new Point(0, 10);
        Point vertical2 = new Point(0, -10);

        assertEquals(0, this.comparator.compare(vertical1, vertical2));
    }

    @Test
    void slopeOrder_oneDegenerateLine() {
        Point nonDegenerate = new Point(-9, 18);

        assertEquals(-1, this.comparator.compare(this.point, nonDegenerate));
    }

    @Test
    void slopeOrder_twoDegenerateLines() {
        assertEquals(0, this.comparator.compare(this.point, this.point));
    }

    @Test
    void slopeOrder() {
        Point smaller = new Point(5, -15);
        Point larger = new Point(5, 10);

        assertEquals(-1, this.comparator.compare(smaller, larger));
        assertEquals(1, this.comparator.compare(larger, smaller));
    }
}