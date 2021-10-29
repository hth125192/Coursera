import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int size;
    private int numOpenSites;
    private final boolean[][] isOpen;
    private final WeightedQuickUnionUF percolate;
    private final WeightedQuickUnionUF full;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.size = n;
        this.numOpenSites = 0;
        this.isOpen = new boolean[n][n];
        this.full = new WeightedQuickUnionUF(n * n + 1);
        this.percolate = new WeightedQuickUnionUF(n * n + 2);
    }

    public void open(int row, int col) {
        checkSites(row, col);
        if (isOpen(row, col)) return;
        else isOpen[row - 1][col - 1] = true;
        numOpenSites++;
        int currentCode = encode(row - 1, col - 1);
        int[] dx = {-1, 0, 0, +1};
        int[] dy = {0, -1, +1, 0};
        for (int i = 0; i < dx.length; i++)
            if (isOnGrid(row + dx[i], col + dy[i]) && isOpen(row + dx[i], col + dy[i])) {
                int adjCode = encode(row - 1 + dx[i], col - 1 + dy[i]);
                if (full.find(currentCode) != full.find(adjCode)) full.union(currentCode, adjCode);
                if (percolate.find(currentCode) != percolate.find(adjCode)) percolate.union(currentCode, adjCode);
            }
        if (row == 1) full.union(currentCode, size * size);
        if (row == 1) percolate.union(currentCode, size * size);
        if (row == size) percolate.union(currentCode, size * size + 1);
    }

    public boolean isOpen(int row, int col) {
        checkSites(row, col);
        return isOpen[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        checkSites(row, col);
        return isOpen(row, col) && full.find(size * size) == full.find(encode(row - 1, col - 1));
    }

    public int numberOfOpenSites() {
        return numOpenSites;
    }

    public boolean percolates() {
        return percolate.find(size * size) == percolate.find(size * size + 1);
    }

    private boolean isOnGrid(int row, int col) {
        return (1 <= row && row <= size && 1 <= col && col <= size);
    }

    private void checkSites(int row, int col) {
        if (!isOnGrid(row, col)) throw new IllegalArgumentException();
    }

    private int encode(int row, int col) {
        return row * size + col;
    }
}