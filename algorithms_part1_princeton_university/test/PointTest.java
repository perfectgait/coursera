import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    private Point point;

    @BeforeEach
    void setUp() {
        this.point = new Point(0, 5);
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
    void compareTo() {
    }

    @Test
    void slopeOrder() {
    }
}