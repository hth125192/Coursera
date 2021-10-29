import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int numItems;

    public RandomizedQueue() {
        this.items = (Item[]) new Object[1];
        this.numItems = 0;
    }

    public boolean isEmpty() {
        return numItems == 0;
    }

    public int size() {
        return numItems;
    }

    public void enqueue(Item item) {
        checkItems(item);
        if (numItems == items.length) resize(2 * items.length);
        items[numItems++] = item;
    }

    public Item dequeue() {
        checkEmpty();
        int randIndex = StdRandom.uniform(numItems);
        Item item = items[randIndex];
        items[randIndex] = items[--numItems];
        items[numItems] = null;
        if (numItems > 0 && numItems == items.length / 4) resize(items.length / 2);
        return item;
    }

    public Item sample() {
        checkEmpty();
        return items[StdRandom.uniform(numItems)];
    }

    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<Item> {
        private int i;
        private final int[] indices;

        private ReverseArrayIterator() {
            i = numItems;
            indices = new int[numItems];
            for (int j = 0; j < numItems; j++) indices[j] = j;
            StdRandom.shuffle(indices);
        }

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return items[indices[--i]];
        }
    }

    private void checkItems(Item item) {
        if (item == null) throw new IllegalArgumentException();
    }

    private void checkEmpty() {
        if (isEmpty()) throw new NoSuchElementException();
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        if (numItems >= 0) System.arraycopy(items, 0, copy, 0, numItems);
        items = copy;
    }

    public static void main(String[] args) {
        StringBuilder expected, actual;

        RandomizedQueue<Double> randomizedQueue = new RandomizedQueue<>();
        Double num1 = StdRandom.uniform() * 100;
        StdOut.printf("%s%.2f%s\n\n", "Adding ", num1, " to the queue...");
        randomizedQueue.enqueue(num1);

        StdOut.printf("%s\n", "Getting a random number from the queue: ");
        Double sampleNum = randomizedQueue.sample();
        StdOut.printf("%s%.2f\n", "Expected: ", num1);
        StdOut.printf("%s%.2f\n", "Actual: ", sampleNum);
        StdOut.printf("%s\n\n", num1.compareTo(sampleNum) == 0 ? "PASSED" : "FAILED");

        Double num2 = StdRandom.uniform() * 100;
        StdOut.printf("%s%.2f%s\n\n", "Adding ", num2, " to the queue...");
        randomizedQueue.enqueue(num2);

        Double num3 = StdRandom.uniform() * 100;
        StdOut.printf("%s%.2f%s\n\n", "Adding ", num3, " to the queue...");
        randomizedQueue.enqueue(num3);

        StdOut.printf("%s\n", "Checking size of the queue: ");
        StdOut.printf("%s\n", "Expected: 3");
        StdOut.printf("%s%d\n", "Actual: ", randomizedQueue.size());
        StdOut.printf("%s\n\n", randomizedQueue.size() == 3 ? "PASSED" : "FAILED");

        expected = new StringBuilder();
        expected.append(String.format("%.2f %.2f %.2f", num1, num2, num3));
        actual = new StringBuilder();
        Iterator<Double> iterator = randomizedQueue.iterator();
        boolean checkIterator = true;
        while (iterator.hasNext()) {
            String popNum = String.format("%.2f", iterator.next());
            checkIterator = checkIterator & (expected.indexOf(popNum) != -1);
            actual.append(popNum).append(" ");
        }
        actual.deleteCharAt(actual.length() - 1);
        StdOut.printf("%s\n", "Listing all elements in the queue using interator: ");
        StdOut.printf("%s%s\n", "Expected: ", expected.toString());
        StdOut.printf("%s%s\n", "Actual: ", actual.toString());
        StdOut.printf("%s\n\n", checkIterator ? "PASSED" : "FAILED");

        StdOut.printf("%s%.2f%s\n\n", "Removing ", randomizedQueue.dequeue(), " from the queue...");

        StdOut.printf("%s%.2f%s\n\n", "Removing ", randomizedQueue.dequeue(), " from the queue...");

        StdOut.printf("%s%.2f%s\n\n", "Removing ", randomizedQueue.dequeue(), " from the queue...");

        StdOut.printf("%s\n", "Checking if queue is empty: ");
        StdOut.printf("%s\n", "Expected: true");
        StdOut.printf("%s%s\n", "Actual: ", randomizedQueue.isEmpty() ? "true" : "false");
        StdOut.printf("%s\n\n", randomizedQueue.isEmpty() ? "PASSED" : "FAILED");
    }
}