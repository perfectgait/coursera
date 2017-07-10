import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DequeTest {
    private Deque<String> deque;

    @BeforeEach
    void setUp() {
        this.deque = new Deque<>();
    }

    @Test
    void isEmpty() {
        assertTrue(this.deque.isEmpty());

        this.deque.addFirst("Test");

        assertFalse(this.deque.isEmpty());

        this.deque.removeFirst();

        assertTrue(this.deque.isEmpty());

        this.deque.addLast("Test2");

        assertFalse(this.deque.isEmpty());

        this.deque.removeLast();

        assertTrue(this.deque.isEmpty());
    }

    @Test
    void size() {
        assertEquals(0, this.deque.size());

        this.deque.addFirst("Test");

        assertEquals(1, this.deque.size());

        this.deque.addLast("Test2");

        assertEquals(2, this.deque.size());

        this.deque.removeFirst();

        assertEquals(1, this.deque.size());

        this.deque.removeLast();

        assertEquals(0, this.deque.size());
    }

    @Test
    void addFirstWithNullItem() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.deque.addFirst(null);
        });
    }

    @Test
    void addFirst() {
        this.deque.addFirst("Test");

        assertEquals("Test", this.deque.removeFirst());

        this.deque.addFirst("Test2");
        this.deque.addFirst("Test3");

        assertEquals("Test3", this.deque.removeFirst());
    }

    @Test
    void addLastWithNullItem() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.deque.addLast(null);
        });
    }

    @Test
    void addLast() {
        this.deque.addLast("Test");

        assertEquals("Test", this.deque.removeLast());

        this.deque.addLast("Test2");
        this.deque.addLast("Test3");

        assertEquals("Test3", this.deque.removeLast());
    }

    @Test
    void removeFirstWhenEmpty() {
        assertThrows(NoSuchElementException.class, () -> {
            this.deque.removeFirst();
        });
    }

    @Test
    void removeFirst() {
        this.deque.addFirst("Test");
        this.deque.addLast("Test2");

        assertEquals("Test", this.deque.removeFirst());
        assertEquals("Test2", this.deque.removeFirst());
    }

    @Test
    void removeLastWhenEmpty() {
        assertThrows(NoSuchElementException.class, () -> {
            this.deque.removeLast();
        });
    }

    @Test
    void removeLast() {
        this.deque.addFirst("Test");
        this.deque.addLast("Test2");

        assertEquals("Test2", this.deque.removeLast());
        assertEquals("Test", this.deque.removeLast());
    }

    @Test
    void addFirstRemoveLast() {
        for (int i = 1; i <= 10; i++) {
            this.deque.addFirst("Test" + i);
        }

        for (int j = 1; j <= 10; j++) {
            assertEquals("Test" + j, this.deque.removeLast());
        }
    }

    @Test
    void addFirstRemoveFirst() {
        for (int i = 1; i <= 10; i++) {
            this.deque.addFirst("Test" + i);
        }

        for (int j = 10; j >= 1; j--) {
            assertEquals("Test" + j, this.deque.removeFirst());
        }
    }

    @Test
    void addLastRemoveFirst() {
        for (int i = 1; i <= 10; i++) {
            this.deque.addLast("Test" + i);
        }

        for (int j = 1; j <= 10; j++) {
            assertEquals("Test" + j, this.deque.removeFirst());
        }
    }

    @Test
    void addLastRemoveLast() {
        for (int i = 1; i <= 10; i++) {
            this.deque.addLast("Test" + i);
        }

        for (int j = 10; j >= 1; j--) {
            assertEquals("Test" + j, this.deque.removeLast());
        }
    }

    @Test
    void alternatingAddAndRemoveFromBothEnds()
    {
        int i;
        int j = 10;

        for (i = 1; i <= 10; i++) {
            this.deque.addFirst("Test" + i);
            this.deque.addLast("Test" + j);

            j--;
        }

        j = 10;

        for (i = 1; i <= 10; i++) {
            assertEquals("Test" + j, this.deque.removeFirst());
            assertEquals("Test" + i, this.deque.removeLast());

            j--;
        }
    }

    @Test
    void iteratorRemove() {
        assertThrows(UnsupportedOperationException.class, () -> {
            this.deque.iterator().remove();
        });
    }

    @Test
    void iteratorNextWhileEmpty() {
        assertThrows(NoSuchElementException.class, () -> {
            this.deque.iterator().next();
        });
    }

    @Test
    void iterator() {
        int i;

        for (i = 1; i <= 10; i++) {
            this.deque.addFirst("Test" + i);
        }

        i = 10;
        Iterator iterator = this.deque.iterator();

        while (iterator.hasNext()) {
            assertEquals("Test" + i, iterator.next());
            i--;
        }

        // Ensure that we have gone through every item added to the deque
        assertEquals(0, i);
    }
}
