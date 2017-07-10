/******************************************************************************
 *  Compilation:  javac RandomizedQueue.java
 *  Execution:    java RandomizedQueue
 *  Dependencies: StdRandom.java
 *
 *  Implements randomized queue
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;

import java.util.NoSuchElementException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int[] order;
        private int current;

        public RandomizedQueueIterator() {
            this.current = 0;
            this.order = new int[size];


            for (int i = 0; i < size; i++) {
                this.order[i] = i;
            }

            StdRandom.shuffle(this.order);
        }

        /**
         * @return Whether or not there are more items
         */
        public boolean hasNext() {
            return this.current != size;
        }

        public void remove() {
            throw new UnsupportedOperationException("You may not call remove during iteration");
        }

        /**
         * @return A random item
         */
        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("There are no more items");
            }

            return items[this.order[current++]];
        }
    }

    public RandomizedQueue() {
        // Create an empty queue with size 2
        this.items = (Item[]) new Object[2];
        this.size = 0;
    }

    /**
     * @return Whether or not the queue is empty
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * @return
     */
    public int size() {
        return this.size;
    }

    /**
     * Enqueue an item to the end of the queue
     *
     * @param item The item to enqueue
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("You cannot add a null item");
        }

        if (this.size == this.items.length) {
            this.resize(this.size * 2);
        }

        this.items[size++] = item;
    }

    /**
     * @return And remove a random item from the queue
     */
    public Item dequeue() {
        if (this.size() == 0) {
            throw new NoSuchElementException("There is no element to deque");
        }

        int current = this.size - 1;
        int randomKey = StdRandom.uniform(0, this.size);
        Item item = this.items[randomKey];
        // Swap the item at key and size so that we always remove from the end
        this.items[randomKey] = this.items[current];
        this.items[current] = null;
        this.size--;

        // Resize the array to half once it is 1/4 full, this will avoid thrashing the array
        if (this.size > 0 && this.size == this.items.length / 4) {
            this.resize(this.items.length / 2);
        }

        return item;
    }

    /**
     * @return A random item from the queue
     */
    public Item sample() {
        if (this.size() == 0) {
            throw new NoSuchElementException("There is no element to deque");
        }

        return this.items[StdRandom.uniform(0, this.size)];
    }

    /**
     *
     * @return A random iterator
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    /**
     * Resize the underlying array that controls the queue
     *
     * @param capacity The new size of the array
     */
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < this.size; i++) {
            copy[i] = this.items[i];
        }

        this.items = copy;
    }

    public static void main(String[] args) {
        // Implement unit tests if needed
    }
}
