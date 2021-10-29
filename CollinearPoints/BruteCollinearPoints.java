import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final List<LineSegment> lineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        int n = points.length;
        checkNullEntries(points);
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        checkDuplicatedEntries(sortedPoints);

        lineSegments = new ArrayList<LineSegment>();
        for (int p1 = 0; p1 < n; p1++)
            for (int p2 = p1 + 1; p2 < n; p2++) {
                double s = sortedPoints[p1].slopeTo(sortedPoints[p2]);
                for (int p3 = p2 + 1; p3 < n; p3++)
                    if (s == sortedPoints[p1].slopeTo(sortedPoints[p3]))
                        for (int p4 = p3 + 1; p4 < n; p4++)
                            if (s == sortedPoints[p1].slopeTo(sortedPoints[p4])) lineSegments.add(new LineSegment(sortedPoints[p1], sortedPoints[p4]));
            }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    private void checkNullEntries(Point[] points) {
        for (int i = 0; i < points.length; i++)
            if (points[i] == null) throw new IllegalArgumentException();
    }

    private void checkDuplicatedEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++)
            if (points[i].compareTo(points[i + 1]) == 0) throw new IllegalArgumentException();
    }
}