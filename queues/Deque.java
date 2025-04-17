/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldNoderst = first;
        first = new Node();
        first.item = item;
        if (isEmpty()) {
            last = first;
        }
        else {
            first.next = oldNoderst;
            oldNoderst.prev = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node();
        node.item = item;
        if (isEmpty()) {
            first = node;
            last = first;
        }
        else {
            node.prev = last;
            last.next = node;
            last = node;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        if (size == 1) {
            last = null;
            first = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        if (size == 1) {
            last = null;
            first = null;
        }
        else {
            last = last.prev;
            last.next = null;

        }
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeItem(first);
    }

    private class DequeItem implements Iterator<Item> {
        private Node currItem;

        public DequeItem(Node first) {
            currItem = first;
        }

        public boolean hasNext() {
            return currItem != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = currItem.item;
            currItem = currItem.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();
        StdOut.println("Mixed pops and pushs");
        // 1
        // 1 2
        // 1 2 3
        // 4 1 2 3
        // 5 4 1 2 3
        // 2 3
        // 2 3 6
        // 3
        System.out.println(d.isEmpty());
        d.addFirst(1);
        d.addFirst(2);
        d.addFirst(3);
        d.addFirst(4);
        System.out.println(d.iterator());
        System.out.println(d.iterator());
        System.out.println(d.iterator());
        
    }
}
