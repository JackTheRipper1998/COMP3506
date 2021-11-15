import org.w3c.dom.NodeList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleLinkedDeque<T> implements SimpleDeque<T> {

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    class ListNode {
        T element;
        ListNode next;
        ListNode prev;
    }

    private ListNode head; // head node
    private ListNode tail; // tail node
    private int capacity; // linkedDeque length
    private int size; // number of none null elements

    /**
     * Constructs a new linked list based deque with unlimited capacity.
     * runtime: O(1)
     * memory complexity: O(1)
     */
    public SimpleLinkedDeque() {
        this.head = null;
        this.tail = null;
    }

    /**
     * Constructs a new linked list based deque with limited capacity.
     *
     * runtime: O(1)
     * memory complexity: O(1)
     *
     * @param capacity the capacity
     * @throws IllegalArgumentException if capacity <= 0
     */
    public SimpleLinkedDeque(int capacity) throws IllegalArgumentException {

        if (capacity > 0) {
            this.head = null;
            this.tail = null;
            this.capacity = capacity;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Constructs a new linked list based deque with unlimited capacity, and initially 
     * populates the deque with the elements of another SimpleDeque.
     *
     * runtime: O(n) where n is the capacity of otherDeque
     * memory complexity: O(n) where n is the capacity of otherDeque
     *
     * @param otherDeque the other deque to copy elements from. otherDeque should be left intact.
     * @requires otherDeque != null
     */
    public SimpleLinkedDeque(SimpleDeque<? extends T> otherDeque) {
        this.head = null;
        this.tail = null;
        Iterator<? extends T> iter = otherDeque.iterator();

        //copy otherDeque elements to deque
        while (iter.hasNext()) {
            pushRight(iter.next());
        }
    }
    
    /**
     * Constructs a new linked list based deque with limited capacity, and initially 
     * populates the deque with the elements of another SimpleDeque.
     *
     * runtime: O(n) where n is the capacity of otherDeque
     * memory complexity: O(n) where n is the capacity of otherDeque
     *
     * @param otherDeque the other deque to copy elements from. otherDeque should be left intact.
     * @param capacity the capacity
     * @throws IllegalArgumentException if capacity <= 0 or size of otherDeque is > capacity
     */
    public SimpleLinkedDeque(int capacity, SimpleDeque<? extends T> otherDeque) 
            throws IllegalArgumentException {
        // create a deque with capacity length
        if (capacity > 0) {
            this.head = null;
            this.tail = null;
            this.capacity = capacity;
        } else {
            throw new IllegalArgumentException();
        }

        // copy otherDeque elements to deque
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
        return head == null;
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public boolean isFull() {
        if (capacity == 0) {
            return false;
        } else if (capacity == size()) {
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

        ListNode newNode = new ListNode();
        newNode.element = e;

        if (head != null) {
            newNode.next = head;
            head.prev = newNode;
        }

        head = newNode;

        if (tail == null) {
            tail = head;
        }

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

        ListNode newNode = new ListNode();
        newNode.element = e;

        if (tail != null) {
            newNode.prev = tail;
            tail.next = newNode;
        }

        tail = newNode;

        if (head == null) {
            head = tail;
        }

        size++;
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public T peekLeft() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return head.element;
    }

    /**
     * runtime: O(1)
     * memory complexity: O(1)
     */
    @Override
    public T peekRight() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return head.element;
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

        ListNode tempHead = head; // store leftMost element which will be popped
        head = head.next;

        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }

        size++;

        return tempHead.element;
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

        ListNode tempTail = tail; // store rightMost element which will be popped
        tail = tail.prev;

        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }

        size--;

        return tempTail.element;
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
    private class Itr implements Iterator<T> {
        ListNode current = head;

        /**
         * runtime: O(1)
         * memory complexity: O(1)
         */
        public boolean hasNext() {
            return current != null;
        }

        /**
         * runtime: O(1)
         * memory complexity: O(1)
         */
        public T next() {
           T element = current.element;

           current = current.next;

           return element;

        }
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
    private class revItr implements Iterator<T> {
        ListNode current = tail;

        /**
         * runtime: O(1)
         * memory complexity: O(1)
         */
        public boolean hasNext() {
            return current != null;
        }

        /**
         * runtime: O(1)
         * memory complexity: O(1)
         */
        public T next() {
            T element = current.element;

            current = current.prev;

            return element;
        }
    }
}
