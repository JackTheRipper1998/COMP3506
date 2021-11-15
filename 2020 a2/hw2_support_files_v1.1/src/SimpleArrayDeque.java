import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleArrayDeque<T> implements SimpleDeque<T> {
    // front: the index of the first element
    // rear: the index of the last element
    // size: total none null element deque have
    // capacity: the length of deque
    private int leftMost, rightMost, size, capacity;
    //data
    private T[] deque;
    /**
     * Constructs a new array based deque with limited capacity.
     *
     * runtime: O(1)
     * memory complexity: O(n) where n is the capacity of deque
     *
     * @param capacity the capacity
     * @throws IllegalArgumentException if capacity <= 0
     */
    public SimpleArrayDeque(int capacity) throws IllegalArgumentException {
        if (capacity > 0) {
            deque = (T[]) new Object[capacity];
            this.capacity = capacity;
            leftMost = -1;
            rightMost = 0;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Constructs a new array based deque with limited capacity, and initially populates the deque
     * with the elements of another SimpleDeque.
     *
     * runtime: O(n): n is the capacity of otherDeque
     * memory complexity: O(n) where n is the capacity of deque
     *
     * @param otherDeque the other deque to copy elements from. otherDeque should be left intact.
     * @param capacity the capacity
     * @throws IllegalArgumentException if capacity <= 0 or size of otherDeque is > capacity
     */
    public SimpleArrayDeque(int capacity, SimpleDeque<? extends T> otherDeque)
            throws IllegalArgumentException {
        //create deque with size == capacity
        if (capacity > 0) {
            deque = (T[]) new Object[capacity];
            this.capacity = capacity;
            leftMost = -1;
            rightMost = 0;
        } else {
            throw new IllegalArgumentException();
        }

        //copy elements from otherDeque to deque
        if (capacity >= otherDeque.size()) {
            Iterator<? extends T> iter = otherDeque.iterator();
            while (iter.hasNext()) {
                pushRight(iter.next());
            }
        } else {
            throw new IllegalArgumentException();
        }

    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public boolean isEmpty() {
        if (leftMost == -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public boolean isFull() {
        if (leftMost == 0 && rightMost == capacity - 1) {
            return true;
        } else if (leftMost == rightMost + 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public void pushLeft(T e) throws RuntimeException {
        if (isFull()) {
            throw new RuntimeException();
        }

        if (leftMost == -1) {
            leftMost = 0;
            rightMost = 0;
        } else if (leftMost == 0) {
            leftMost = capacity - 1;
        } else {
            leftMost = leftMost - 1;
        }

        deque[leftMost] = e;

        size++;
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public void pushRight(T e) throws RuntimeException {
        if (isFull()) {
            throw new RuntimeException();
        }

        if (leftMost == -1) {
            leftMost = 0;
            rightMost = 0;
        } else if (rightMost == capacity - 1) {
            rightMost = 0;
        } else {
            rightMost = rightMost + 1;
        }

        deque[rightMost] = e;

        size++;
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public T peekLeft() throws NoSuchElementException {
        if(isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return deque[leftMost];
        }
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public T peekRight() throws NoSuchElementException {
        if(isEmpty() || rightMost < 0) {
            throw new NoSuchElementException();
        } else {
            return deque[rightMost];
        }
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public T popLeft() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int temp = leftMost;

        if (leftMost == rightMost) {
            leftMost = -1;
            rightMost = -1;
        } else if (leftMost == capacity -1){
            leftMost = 0;
        } else {
            leftMost = leftMost + 1;
        }
        size--;
        return deque[temp];
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public T popRight() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int temp = rightMost;

        if (leftMost == rightMost) {
            leftMost = -1;
            rightMost = -1;
        } else if (rightMost == 0) {
            rightMost = capacity - 1;
        } else {
            rightMost = rightMost - 1;
        }
        size--;
        return deque[temp];
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public Iterator<T> reverseIterator() {
        return new revItr();
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    private class Itr implements Iterator<T> {
        int cursor = leftMost;
        int counter = 0; // recording operation time

        /**
         * runtime: O(1)
         * memory complexity: O(1)
         */
        public boolean hasNext() {
            if (isFull() && counter == capacity) {
                return false;
            } else {
                return cursor != rightMost + 1;
            }
        }

        /**
         * runtime: O(1)
         * memory complexity: O(1)
         */
        public T next() {
            if (isEmpty()) {
                throw new NoSuchElementException();
            }
            int i = cursor;

            if (cursor == capacity - 1) {
                cursor = 0;
            } else {
                cursor = i + 1;
            }
            counter++;
            return deque[i];
        }


    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    private class revItr implements Iterator<T> {

        int cursor = rightMost;
        int counter = 0; // recording operation time

        /**
         * runtime: O(1)
         * memory complexity: O(1)
         */
        public boolean hasNext() {
            if (isFull() && counter == capacity) {
                return false;
            } else {
                return cursor != leftMost - 1;
            }
        }

        /**
         * runtime: O(1)
         * memory complexity: O(1)
         */
        public T next() {
            if (isEmpty()) {
                throw new NoSuchElementException();
            }
            int i = cursor;

            if (cursor == 0) {
                cursor = capacity - 1;
            } else {
                cursor = i - 1;
            }
            counter++;
            return deque[i];
        }
    }


}
