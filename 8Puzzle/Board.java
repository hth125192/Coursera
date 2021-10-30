import java.util.Arrays;
import edu.princeton.cs.algs4.Stack;

public class Board {
    private final int[][] tiles;
    private final int dimension;
    private int hammingDistance;
    private int manhattanDistance;
    private int iIndex = 0;
    private int jIndex = 0;

    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException();
        this.dimension = tiles.length;
        this.tiles = new int[dimension][dimension];
        for (int i = 0; i < tiles.length; i++)
            for (int j = 0; j < tiles[i].length; j++) this.tiles[i][j] = tiles[i][j];
        calculateDistances();
    }

    private void calculateDistances() {
        int calcHamming = 0;
        int calcManhattan = 0;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] == 0) {
                    this.iIndex = i;
                    this.jIndex = j;
                }
                if ((tiles[i][j] != 0) && tiles[i][j] != ((i * dimension) + j + 1)) {
                    calcHamming++;
                    calcManhattan += Math.abs(i - (tiles[i][j] - 1) / dimension) + Math.abs(j - (tiles[i][j] - 1) % dimension);
                }
            }
        this.hammingDistance = calcHamming;
        this.manhattanDistance = calcManhattan;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dimension).append("\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) stringBuilder.append(tiles[i][j]).append("  ");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public int dimension() {
        return dimension;
    }

    public int hamming() {
        return hammingDistance;
    }

    public int manhattan() {
        return manhattanDistance;
    }

    public boolean isGoal() {
        return hammingDistance == 0;
    }

    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (getClass() != y.getClass()) return false;
        Board other = (Board) y;
        return Arrays.deepEquals(tiles, other.tiles);
    }

    public Iterable<Board> neighbors() {
        Stack<Board> boards = new Stack<>();

        if (iIndex > 0) {
            int[][] blocksCopy = copyBlocks(tiles, dimension);
            blocksCopy[iIndex][jIndex] = tiles[iIndex - 1][jIndex];
            blocksCopy[iIndex - 1][jIndex] = tiles[iIndex][jIndex];
            boards.push(new Board(blocksCopy));
        }

        if (jIndex > 0) {
            int[][] blocksCopy = copyBlocks(tiles, dimension);
            blocksCopy[iIndex][jIndex] = tiles[iIndex][jIndex - 1];
            blocksCopy[iIndex][jIndex - 1] = tiles[iIndex][jIndex];
            boards.push(new Board(blocksCopy));
        }

        if (iIndex < dimension - 1) {
            int[][] blocksCopy = copyBlocks(tiles, dimension);
            blocksCopy[iIndex][jIndex] = tiles[iIndex + 1][jIndex];
            blocksCopy[iIndex + 1][jIndex] = tiles[iIndex][jIndex];
            boards.push(new Board(blocksCopy));
        }

        if (jIndex < dimension - 1)
        {
            int[][] blocksCopy = copyBlocks(tiles, dimension);
            blocksCopy[iIndex][jIndex] = tiles[iIndex][jIndex + 1];
            blocksCopy[iIndex][jIndex + 1] = tiles[iIndex][jIndex];
            boards.push(new Board(blocksCopy));
        }

        return boards;
    }

    private int[][] copyBlocks(int[][] blocks, int dimens) {
        int[][] copy = new int[dimens][dimens];
        for (int i = 0; i < dimens; i++)
            for (int j = 0; j < dimens; j++) copy[i][j] = blocks[i][j];
        return copy;
    }

    public Board twin() {
        int[][] copy = copyBlocks(tiles, dimension);
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension - 1; j++)
                if (copy[i][j] != 0 && copy[i][j + 1] != 0) {
                    int swap = copy[i][j];
                    copy[i][j] = copy[i][j + 1];
                    copy[i][j + 1] = swap;
                    return new Board(copy);
                }
        return null;
    }
}
