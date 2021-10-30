import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {
    private final boolean solvable;
    private final int solutionMoves;
    private Node solutionBoard;

    private static class Node implements Comparable<Node> {
        private final Board board;
        private final int moves;
        private final Node previousNode;
        private final int priority;

        public Node(Board board, int moves, Node previousNode) {
            this.board = board;
            this.moves = moves;
            this.previousNode = previousNode;
            priority = moves + board.manhattan();
        }

        @Override
        public int compareTo(Node other) {
            if (this.priority > other.priority) return 1;
            if (this.priority < other.priority) return -1;
            return 0;
        }
    }

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<Node> searchNodeQueue = new MinPQ<>();
        MinPQ<Node> twinSearchNodeQueue = new MinPQ<>();

        Node node = new Node(initial, 0, null);
        Node twinNode = new Node(initial.twin(), 0, null);

        searchNodeQueue.insert(node);
        twinSearchNodeQueue.insert(twinNode);

        while (true) {
            Node minimumNode = searchNodeQueue.delMin();
            Node minimumTwinNode = twinSearchNodeQueue.delMin();
            if (minimumNode.board.isGoal()) {
                solutionBoard = minimumNode;
                solvable = true;
                solutionMoves = minimumNode.moves;
                break;
            }

            if (minimumTwinNode.board.isGoal()) {
                solvable = false;
                solutionMoves = -1;
                break;
            }

            insertNeighbors(minimumNode, searchNodeQueue);
            insertNeighbors(minimumTwinNode, twinSearchNodeQueue);
        }
    }

    private void insertNeighbors(Node searchNode, MinPQ<Node> queue) {
        Iterable<Board> neighbors = searchNode.board.neighbors();
        for (Board nextBoard : neighbors) {
            if ((searchNode.previousNode == null) || (!nextBoard.equals(searchNode.previousNode.board))) queue.insert(new Node(nextBoard, searchNode.moves + 1, searchNode));
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        return solutionMoves;
    }

    public Iterable<Board> solution() {
        if (!solvable) return null;
        List<Board> solutionList = new ArrayList<>();
        Node board = solutionBoard;
        while (board != null) {
            solutionList.add(board.board);
            board = board.previousNode;
        }
        Collections.reverse(solutionList);
        return solutionList;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        if (!solver.isSolvable()) StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) StdOut.println(board);
        }
    }
}
