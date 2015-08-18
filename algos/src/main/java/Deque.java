import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author aks
 * @since 16/08/15
 */
public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first = null;
    private Node<Item> last = null;
    private int size = 0;

    public Deque() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        checkNotNull(item);

        Node<Item> newNode = new Node<Item>(item, first, null);
        if (isEmpty()) {
            first = newNode;
            last = first;
        } else {
            Node oldFirst = first;
            oldFirst.prev = newNode;
            first = newNode;
        }

        size++;
    }

    public void addLast(Item item) {
        checkNotNull(item);

        if (isEmpty()) {
            addFirst(item);
        } else {
            Node<Item> newNode = new Node<Item>(item, null, last);
            last.next = newNode;
            last = newNode;
            size++;
        }
    }

    public Item removeFirst() {
        checkNotEmpty();
        Item item = first.item;
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.prev = null;
        }

        size--;
        return item;
    }

    public Item removeLast() {
        checkNotEmpty();

        Item item = last.item;
        last = last.prev;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
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

    private class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;

        private Node(Item item) {
            this.item = item;
        }

        private Node(Item item, Node<Item> next, Node<Item> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private class DequeIterator implements Iterator {
        private Node<Item> current = Deque.this.first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("Kumar");
        deque.addLast("Sharma");
        deque.addFirst("Anil");
        print(deque.iterator());
        System.out.println(deque.size());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        deque.addFirst("Anil");
        System.out.println(deque.removeLast());
        deque.addFirst("Anil");
        System.out.println(deque.removeFirst());
        deque.addLast("Anil");
        System.out.println(deque.removeFirst());
        deque.addLast("Anil");
        System.out.println(deque.removeLast());
    }

    private static <Item> void print(Iterator<Item> itr) {
        while (itr.hasNext()) {
            System.out.print(itr.next() + " ");
        }
    }

}
