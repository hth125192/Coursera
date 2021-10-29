public class LineSegment {
    private final Point p;
    private final Point q;

    public LineSegment(Point p, Point q) {
        if (p == null || q == null) throw new IllegalArgumentException("argument to LineSegment constructor is null");
        if (p.equals(q)) throw new IllegalArgumentException("both arguments to LineSegment constructor are the same point: " + p);
        this.p = p;
        this.q = q;
    }

    public void draw() {
        p.drawTo(q);
    }

    public String toString() {
        return p + " -> " + q;
    }

    public int hashCode() {
        throw new UnsupportedOperationException("hashCode() is not supported");
    }
}