/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INITIAL_CAPACITY = 16;
    private Item[] container;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        container = (Item[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resizeContainer(int cap) {
        Item[] newSizeContainer = (Item[]) new Object[cap];
        for (int i = 0; i < size; i++) {
            newSizeContainer[i] = container[i];
        }
        container = newSizeContainer;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size > 0 && size == container.length) {
            resizeContainer(2 * container.length);
        }
        container[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (size == 1) {
            Item item = container[0];
            container[0] = null;
            size--;
            return item;
        }
        int index = StdRandom.uniformInt(size);
        Item item = container[index];
        container[index] = null;
        container[index] = container[size - 1];
        size--;
        if (size > 0 && size == container.length / 4) {
            resizeContainer(container.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (size == 1) {
            Item item = container[0];
            return item;
        }
        int index = StdRandom.uniformInt(size);
        Item item = container[index];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] cpContainer;
        private int cpSize;

        public RandomizedQueueIterator() {
            cpSize = size;
            cpContainer = (Item[]) new Object[cpSize];
            System.arraycopy(container, 0, cpContainer, 0, cpSize);
        }

        public boolean hasNext() {
            return cpSize > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int index = StdRandom.uniformInt(cpSize);
            Item item = cpContainer[index];
            if (index == cpSize - 1) {
                cpContainer[index] = null;
            }
            else {
                cpContainer[index] = null;
                cpContainer[index] = cpContainer[cpSize - 1];
            }
            if (cpSize > 0 && cpSize == cpContainer.length / 4) {
                resizeCpContainer(cpContainer.length / 2);
            }
            cpSize--;
            return item;
        }

        private int resizeCpContainer(int cap) {
            Item[] newCpContainer = (Item[]) new Object[cap];
            for (int i = 0; i < cpSize; i++) {
                newCpContainer[i] = cpContainer[i];
            }
            cpContainer = newCpContainer;
            return cap;
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 5;

        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();

        for (int i = 0; i < n; i++) {
            randomizedQueue.enqueue(i);
        }

        for (int a : randomizedQueue) {
            for (int b : randomizedQueue) {
                System.out.print(a + "-" + b + " ");
            }
            System.out.println();

        }
    }
}
