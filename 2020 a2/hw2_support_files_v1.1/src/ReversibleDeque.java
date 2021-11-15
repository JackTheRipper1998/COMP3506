import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReversibleDeque<T> implements SimpleDeque<T> {

    private SimpleDeque<T> deque;

    /**
     * Constructs a new reversible deque, using the given data deque to store
     * elements.
     * The data deque must not be used externally once this ReversibleDeque
     * is created.
     *
     * runtime: O(n) where n is the capacity of deque(data)
     * memory complexity: O(n) where n is the capacity of deque(data)
     *
     * @param data a deque to store elements in.
     */
    public ReversibleDeque(SimpleDeque<T> data) {
        deque = data;
    }

    /**
     * runtime: O(n) where n is the capacity of deque(data)
     * memory complexity: O(n) where n is the capacity of deque(data)
     */
    public void reverse() {
        Iterator<? extends T> iter = deque.reverseIterator();
        int counter = 0;
        int capacity = 0; //record capacity

        while (iter.hasNext()) {
            capacity ++;
            iter.next();
        }

        while (counter <= capacity) {
            deque.popRight();
            deque.pushLeft(iter.next());
            counter++;
        }
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public int size() {
        return deque.size();
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public boolean isFull() {
        return deque.isFull();
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public void pushLeft(T e) throws RuntimeException {
        deque.pushLeft(e);
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public void pushRight(T e) throws RuntimeException {
        deque.pushRight(e);
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public T peekLeft() throws NoSuchElementException {
        return deque.peekLeft();
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public T peekRight() throws NoSuchElementException {
        return deque.peekRight();
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public T popLeft() throws NoSuchElementException {
        return deque.popLeft();
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public T popRight() throws NoSuchElementException {
        return deque.popRight();
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public Iterator<T> iterator() {
        return deque.iterator();
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public Iterator<T> reverseIterator() {
        return deque.reverseIterator();
    }
}
