import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.List;

public class PointSET {
    private final SET<Point2D> pointSET;

    public PointSET() {
        pointSET = new SET<>();
    }

    public boolean isEmpty() {
        return pointSET.isEmpty();
    }

    public int size() {
        return pointSET.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        pointSET.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return pointSET.contains(p);
    }

    public void draw() {
        for (Point2D point : pointSET) point.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        List<Point2D> pointList = new ArrayList<>();
        for (Point2D point: pointSET)
            if (rect.contains(point)) pointList.add(point);
        return pointList;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (pointSET.isEmpty()) return null;
        Point2D result = null;
        for (Point2D point : pointSET)
            if (result == null || p.distanceSquaredTo(point) < p.distanceSquaredTo(result)) result = point;
        return result;
    }
}