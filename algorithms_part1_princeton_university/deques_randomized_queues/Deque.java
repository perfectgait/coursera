/******************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:    java Deque
 *  Dependencies: None
 *
 *  Implements double ended queue
 ******************************************************************************/

import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    // Sentinel node
    private final Node sentinelFirst;
    // Sentinel node
    private final Node sentinelLast;
    private int size;

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    private class DequeFrontToBackIterator implements Iterator<Item> {
        private Node current = sentinelFirst.next;

        /**
         * @return Whether or not there are more items
         */
        public boolean hasNext() {
            return this.current != sentinelLast;
        }

        public void remove() {
            throw new UnsupportedOperationException("You may not call remove during iteration");
        }

        /**
         * @return The next item
         */
        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("There are no more items");
            }

            Item item = this.current.item;
            this.current = this.current.next;

            return item;
        }
    }

    public Deque() {
        this.sentinelFirst = new Node();
        this.sentinelLast = new Node();
        this.sentinelFirst.next = this.sentinelLast;
        this.sentinelFirst.previous = null;
        this.sentinelLast.previous = sentinelFirst;
        this.sentinelLast.next = null;
        this.size = 0;
    }

    /**
     * @return Whether or not the deque is empty
     */
    public boolean isEmpty() {
        return this.sentinelFirst.next == this.sentinelLast && this.sentinelLast.previous == this.sentinelFirst;
    }

    /**
     * @return The size of the deque
     */
    public int size() {
        return this.size;
    }

    /**
     * Add an item to the front of the deque
     *
     * @param item The item to add to the front of the deque
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("You cannot add a null item");
        }

        // Grab the current first node
        Node currentFirst = this.sentinelFirst.next;
        // Create a new node, pointing it back to the sentinel first node and forward to the current first node
        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.previous = this.sentinelFirst;
        newFirst.next = currentFirst;
        // Point the sentinel first node forward to the new first node
        this.sentinelFirst.next = newFirst;
        // Point the current first node back to the new first node
        currentFirst.previous = newFirst;

        this.size++;
    }

    /**
     * Add an item to the end of the deque
     *
     * @param item The item to add to the end of the deque
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("You cannot add a null item");
        }

        // Grab the current last node
        Node currentLast = this.sentinelLast.previous;
        // Create a new node, pointing it forward to the sentinel last node and back to the current last node
        Node newLast = new Node();
        newLast.item = item;
        newLast.next = this.sentinelLast;
        newLast.previous = currentLast;
        // Point the sentinel last node back to the new last node
        this.sentinelLast.previous = newLast;
        // Point the current last node forward to the new last node
        currentLast.next = newLast;

        this.size++;
    }

    /**
     * Remove the first node from the deque
     *
     * @return The first node
     */
    public Item removeFirst() {
        if (this.sentinelFirst.next == this.sentinelLast) {
            throw new NoSuchElementException("There is no element at the front of the deque");
        }

        Node currentFirst = this.sentinelFirst.next;
        // Point the sentinel first node to the current first node's next node
        this.sentinelFirst.next = currentFirst.next;
        // Point the next first node to the sentinel first node
        this.sentinelFirst.next.previous = this.sentinelFirst;

        this.size--;

        return currentFirst.item;
    }

    /**
     * Remove the last node from the deque
     *
     * @return The last node
     */
    public Item removeLast() {
        if (this.sentinelLast.previous == this.sentinelFirst) {
            throw new NoSuchElementException("There is no element at the end of the deque");
        }

        Node currentLast = this.sentinelLast.previous;
        // Point the sentinel last node to the current last node's previous node
        this.sentinelLast.previous = currentLast.previous;
        // Point the previous last node forward to the sentinel last node
        this.sentinelLast.previous.next = this.sentinelLast;

        this.size--;

        return currentLast.item;
    }

    /**
     * @return A front to back iterator
     */
    public Iterator<Item> iterator() {
        return new DequeFrontToBackIterator();
    }

    public static void main(String[] args) {
        // Implement unit tests if needed
    }
}
