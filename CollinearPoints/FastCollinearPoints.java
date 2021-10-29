import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final List<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        int n = points.length;
        checkNullEntries(points);
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        checkDuplicatedEntries(sortedPoints);

        lineSegments = new ArrayList<LineSegment>();
        Point[] slopes;
        for (int i = 0; i < n; i++) {
            slopes = sortedPoints.clone();
            Point root = slopes[i];
            Arrays.sort(slopes, sortedPoints[i].slopeOrder());
            int lo = 0;
            while (lo < n) {
                double s = sortedPoints[i].slopeTo(slopes[lo]);
                int hi = lo;
                boolean check = (sortedPoints[i].compareTo(slopes[hi]) < 0);
                while (hi + 1 < n && s == sortedPoints[i].slopeTo(slopes[hi + 1])) {
                    hi++;
                    if (sortedPoints[i].compareTo(slopes[hi]) > 0) check = false;
                }
                if (check && hi - lo + 1 >= 3) lineSegments.add(new LineSegment(root, slopes[hi]));
                lo = hi + 1;
            }
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