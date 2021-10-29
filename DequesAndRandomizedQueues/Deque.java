import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    private Node first;
    private Node last;
    private int numItems;

    public Deque() {
        this.first = null;
        this.last = null;
        this.numItems = 0;
    }

    public boolean isEmpty() {
        return numItems == 0;
    }

    public int size() {
        return numItems;
    }

    public void addFirst(Item item) {
        checkItems(item);
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldFirst;
        if (isEmpty()) last = first;
        else oldFirst.prev = first;
        numItems++;
    }

    public void addLast(Item item) {
        checkItems(item);
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        last.next = null;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        numItems++;
    }

    public Item removeFirst() {
        checkEmpty();
        Item item = first.item;
        first = first.next;
        numItems--;
        if (isEmpty()) last = null;
        else first.prev = null;
        return item;
    }

    public Item removeLast() {
        checkEmpty();
        Item item = last.item;
        last = last.prev;
        numItems--;
        if (isEmpty()) first = null;
        else last.next = null;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private void checkItems(Item item) {
        if (item == null) throw new IllegalArgumentException();
    }

    private void checkEmpty() {
        if (isEmpty()) throw new NoSuchElementException();
    }

    public static void main(String[] args) {
        StringBuilder expected, actual;

        Deque<Double> deque = new Deque<Double>();
        Double lastNum = StdRandom.uniform() * 100;
        StdOut.printf("%s%.2f%s\n\n", "Adding ", lastNum, " to the rear of the deque...");
        deque.addLast(lastNum);

        Double firstNum = StdRandom.uniform() * 100;
        StdOut.printf("%s%.2f%s\n\n", "Adding ", firstNum, " to the front of the deque...");
        deque.addFirst(firstNum);

        expected = new StringBuilder();
        expected.append(String.format("%.2f %.2f", firstNum, lastNum));
        actual = new StringBuilder();
        for (Double aDouble : deque) actual.append(String.format("%.2f ", aDouble));
        actual.deleteCharAt(actual.length() - 1);
        StdOut.printf("%s\n", "Listing all elements in the deque using interator: ");
        StdOut.printf("%s%s\n", "Expected: ", expected.toString());
        StdOut.printf("%s%s\n", "Actual: ", actual.toString());
        StdOut.printf("%s\n\n", expected.compareTo(actual) == 0 ? "PASSED" : "FAILED");

        StdOut.printf("%s%.2f%s\n", "Removing ", firstNum, " from the deque: ");
        Double popNum = deque.removeFirst();
        StdOut.printf("%s%.2f\n", "Expected: ", firstNum);
        StdOut.printf("%s%.2f\n", "Actual: ", popNum);
        StdOut.printf("%s\n\n", firstNum.compareTo(popNum) == 0 ? "PASSED" : "FAILED");

        StdOut.printf("%s\n", "Checking size of the deque: ");
        StdOut.printf("%s\n", "Expected: 1");
        StdOut.printf("%s%d\n", "Actual: ", deque.size());
        StdOut.printf("%s\n\n", deque.size() == 1 ? "PASSED" : "FAILED");

        StdOut.printf("%s%.2f%s\n", "Removing ", lastNum, " from the deque: ");
        popNum = deque.removeLast();
        StdOut.printf("%s%.2f\n", "Expected: ", lastNum);
        StdOut.printf("%s%.2f\n", "Actual: ", popNum);
        StdOut.printf("%s\n\n", lastNum.compareTo(popNum) == 0 ? "PASSED" : "FAILED");

        StdOut.printf("%s\n", "Checking if deque is empty: ");
        StdOut.printf("%s\n", "Expected: true");
        StdOut.printf("%s%s\n", "Actual: ", deque.isEmpty() ? "true" : "false");
        StdOut.printf("%s\n\n", deque.isEmpty() ? "PASSED" : "FAILED");
    }
}