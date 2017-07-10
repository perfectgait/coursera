/******************************************************************************
 *  Compilation:  javac Permutation.java
 *  Execution:    java Permutation
 *  Dependencies: StdOut.java, StdIn.java
 *
 *  Implements percolation grid
 ******************************************************************************/

import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int numberOfItems = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String inputString = StdIn.readString();

            queue.enqueue(inputString);
        }

        Iterator<String> iterator = queue.iterator();

        for (int i = 0; i < numberOfItems; i++) {
            StdOut.println(iterator.next());
        }
    }
}
