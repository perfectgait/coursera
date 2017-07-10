import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomizedQueueTest {
    private RandomizedQueue<String> queue;

    @BeforeEach
    void setUp() {
        this.queue = new RandomizedQueue<>();
    }

    @Test
    void isEmpty() {
        assertTrue(this.queue.isEmpty());

        this.queue.enqueue("Test");

        assertFalse(this.queue.isEmpty());

        this.queue.dequeue();

        assertTrue(this.queue.isEmpty());
    }

    @Test
    void size() {
        assertEquals(0, this.queue.size());

        this.queue.enqueue("Test");

        assertEquals(1, this.queue.size());

        this.queue.enqueue("Test2");

        assertEquals(2, this.queue.size());

        this.queue.dequeue();

        assertEquals(1, this.queue.size());

        this.queue.dequeue();

        assertEquals(0, this.queue.size());
    }

    @Test
    void enqueueWithNullItem() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.queue.enqueue(null);
        });
    }

    @Test
    void enqueue() {
        this.queue.enqueue("Test");

        assertEquals(1, this.queue.size());

        String item = this.queue.dequeue();

        assertEquals("Test", item);
        assertEquals(0, this.queue.size());
    }

    @Test
    void dequeueWhenEmpty() {
        assertThrows(NoSuchElementException.class, () -> {
            this.queue.dequeue();
        });
    }

    @Test
    void dequeue() {
        this.queue.enqueue("Test");
        this.queue.enqueue("Test2");

        assertEquals(2, this.queue.size());

        String item = this.queue.dequeue();

        assertEquals(1, this.queue.size());

        String item2 = this.queue.dequeue();

        assertEquals(0, this.queue.size());

        if (!item.equals("Test") && !item.equals("Test2")) {
            fail("item not found in the queue");
        }

        if (!item2.equals("Test") && !item2.equals("Test2")) {
            fail("item2 not found in the queue");
        }
    }

    @Test
    void dequeueWithManyItems() {
        this.queue.enqueue("Test");
        this.queue.enqueue("Test2");
        this.queue.enqueue("Test3");
        this.queue.enqueue("Test4");

        assertEquals(4, this.queue.size());

        String item = this.queue.dequeue();

        assertEquals(3, this.queue.size());

        String item2 = this.queue.dequeue();

        assertEquals(2, this.queue.size());


        String item3 = this.queue.dequeue();

        assertEquals(1, this.queue.size());


        String item4 = this.queue.dequeue();

        assertEquals(0, this.queue.size());

        if (!item.equals("Test") && !item.equals("Test2") && !item.equals("Test3") && !item.equals("Test4")) {
            fail("item not found in the queue");
        }

        if (!item2.equals("Test") && !item2.equals("Test2") && !item2.equals("Test3") && !item2.equals("Test4")) {
            fail("item2 not found in the queue");
        }

        if (!item3.equals("Test") && !item3.equals("Test2") && !item3.equals("Test3") && !item3.equals("Test4")) {
            fail("item3 not found in the queue");
        }

        if (!item4.equals("Test") && !item4.equals("Test2") && !item4.equals("Test3") && !item4.equals("Test4")) {
            fail("item4 not found in the queue");
        }
    }

    @Test
    void sampleWhenEmpty() {
        assertThrows(NoSuchElementException.class, () -> {
            this.queue.sample();
        });
    }

    @Test
    void sample() {
        this.queue.enqueue("Test");
        this.queue.enqueue("Test2");

        assertEquals(2, this.queue.size());

        String item = this.queue.sample();

        assertEquals(2, this.queue.size());

        String item2 = this.queue.sample();

        assertEquals(2, this.queue.size());

        if (!item.equals("Test") && !item.equals("Test2")) {
            fail("item not found in the queue");
        }

        if (!item2.equals("Test") && !item2.equals("Test2")) {
            fail("item2 not found in the queue");
        }
    }

    @Test
    void iteratorRemove() {
        assertThrows(UnsupportedOperationException.class, () -> {
            this.queue.iterator().remove();
        });
    }

    @Test
    void iteratorNextWhileEmpty() {
        assertThrows(NoSuchElementException.class, () -> {
            this.queue.iterator().next();
        });
    }

    @Test
    void iterator() {
        int i;

        for (i = 1; i <= 10; i++) {
            this.queue.enqueue("Test" + i);

            assertEquals(i, this.queue.size());
        }

        i = 10;
        Iterator iterator = this.queue.iterator();

        while (iterator.hasNext()) {
            String next = (String) iterator.next();
            boolean validItem = false;

            for (int j = 1; j <= 10; j++) {
                if (next.equals("Test" + j)) {
                    validItem = true;
                    break;
                }
            }

            if (!validItem) {
                fail("The item is not valid");
            }

            i--;
        }

        // Ensure that we have gone through every item added to the deque
        assertEquals(0, i);
    }
}
