import java.lang.*;import java.lang.Iterable;import java.lang.NullPointerException;import java.lang.Object;import java.lang.Override;import java.lang.String;import java.lang.System;import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author aks
 * @since 16/08/15
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int head = -1;
    private int tail = -1;

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return head == -1;
    }

    public int size() {
        if (isEmpty()) {
            return 0;
        }
        return head - tail + 1;
    }

    public void enqueue(Item item) {
        checkNotNull(item);
        if (head == items.length - 1) {
            resize();
        }
        items[++head] = item;
        if (tail == -1) {
            tail = head;
        }
    }

    public Item dequeue() {
        checkNotEmpty();

        Item item = items[tail];
        items[tail] = null;
        tail++;

        // shrink
        if (size() < items.length / 4) {
            resize();
        }
        return item;
    }

    public Item sample() {
        checkNotEmpty();

        int random = StdRandom.uniform(size());
        return items[tail + random];
    }

    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private void resize() {
        if (isEmpty() || size() == 0) {
            items = (Item[]) new Object[1];
            head = -1;
            tail = -1;
        } else {
            Item[] copy = (Item[]) new Object[2 * size()];
            for (int i = tail, j = 0; i <= head;) {
                copy[j++] = items[i++];
            }
            items = copy;
            head = head - tail;
            tail = 0;
        }
    }

    private void checkNotNull(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }

    private void checkNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }
    private class RandomizedIterator implements Iterator<Item> {
        private final Item[] copy;
        private int current = 0;

        RandomizedIterator() {
            if (isEmpty()) {
                copy = (Item[]) new Object[0];
            } else {
                int from = RandomizedQueue.this.tail;
                int to = RandomizedQueue.this.head;
                copy = (Item[]) new Object[RandomizedQueue.this.size()];
                for (int i = from, j = 0; i <= to;) {
                    copy[j++] = items[i++];
                }
                StdRandom.shuffle(copy);
            }
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return copy[current++];
        }

        @Override
        public boolean hasNext() {
            return copy.length > 0 && current < copy.length;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> randy = new RandomizedQueue<>();
        randy.enqueue("Anil");
        randy.enqueue("Kumar");
        randy.enqueue("Sharma");
        print(randy.iterator());
        System.out.println(randy.sample());
        randy.dequeue();
        randy.dequeue();
        print(randy.iterator());
        randy.dequeue();
        print(randy.iterator());
    }

    private static <Item> void print(Iterator<Item> itr) {
        while (itr.hasNext()) {
            System.out.print(itr.next() + " ");
        }
        System.out.println();
    }
}
