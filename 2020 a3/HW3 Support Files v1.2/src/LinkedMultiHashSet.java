import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * LinkedMultiHashSet is an implementation of a (@see MultiSet), using a hashtable as the internal
 * data structure, and with predictable iteration order based on the insertion order
 * of elements.
 * 
 * Its iterator orders elements according to when the first occurrence of the element 
 * was added. When the multiset contains multiple instances of an element, those instances 
 * are consecutive in the iteration order. If all occurrences of an element are removed, 
 * after which that element is added to the multiset, the element will appear at the end of the 
 * iteration.
 * 
 * The internal hashtable array should be doubled in size after an add that would cause it to be
 * at full capacity. The internal capacity should never decrease.
 * 
 * Collision handling for elements with the same hashcode (i.e. with hashCode()) should be done
 * using linear probing, as described in lectures.
 * 
 * @param <T> type of elements in the set
 */
public class LinkedMultiHashSet<T> implements MultiSet<T>, Iterable<T> {
    // TODO: implement question 4 in this file
    /**
     * A linked list to store element and the element's number
     * ordered by element insertion order
     */
    private class ListNode<T> {
        private T element; // element in set.
        private ListNode next;
        private ListNode prev;
        private int elementNum; // the number of occurrences of the element in set.

        // initialization
        ListNode(T element){
            this.element = element;
            this.elementNum = 0;
            this.next = null;
            this.prev = null;
        }
    }

    //private int[] occurrenceSet; // a set to store the number of occurrences of the element in set.
    private int capacity; // the capacity of set.
    private ListNode[] valueSet; // a linked list to store the element in set.
    private int valueNum; // the number of occurrences of the element.
    private int distinctValueNum; // the number of the distinct element.
    private ListNode head; // linked list head node.
    private ListNode tail; // linked list tail node.

    /**
     * LinkedMultiHashSet constructor
     * @param initialCapacity the initial capacity of the set
     */
    public LinkedMultiHashSet(int initialCapacity) {
        valueSet =  new ListNode[initialCapacity];
        //occurrenceSet =  new int[initialCapacity];
        capacity = initialCapacity;
        valueNum = 0;
        head = null;
        tail = null;
    }


    /**
     * expected time complexity: O(1)
     * time complexity: O(n)
     * space complexity: O(1)
     *
     * Adds the element to the set. If an equal element is already in the set,
     * increases its occurrence count by 1.
     *
     * @param element to add
     * @require element != null
     */
    @Override
    public void add(T element) {
        add(element, 1);
    }

    /**
     * expected time complexity: O(1)
     * time complexity: O(n)
     * expected space complexity: O(1)
     * space complexity: O(n)
     *
     * Adds count to the number of occurrences of the element in set.
     *
     * @param element to add
     * @require element != null && count >= 0
     */
    @Override
    public void add(T element, int count) {
        // resize when total number after added bigger than the capacity.
        if (valueNum + count >= capacity) {
            resize();
        }

        // compression the element including linear probing
        int key = indexOf(element);

        if (valueSet[key] == null || key == -1) {
            ListNode node = new ListNode(element);
            valueSet[key] = node;
            valueSet[key].elementNum += count;

            // add element into value linked list
            if (head == null) {
                head = valueSet[key];
                tail = valueSet[key];
            } else {
                tail.next = valueSet[key];
                valueSet[key].prev = tail;
                tail = valueSet[key];
            }
            distinctValueNum++;
        } else {
            valueSet[key].elementNum += count;
        }
        // add occurrence
        //occurrenceSet[key] += count;
        // add the number of occurrences of the element.
        valueNum += count;
    }

    /**
     * time complexity: O(n)
     * space complexity: O(n)
     *
     * resize the set by doubling it's capacity
     */
    private void resize () {
        // create a copy linked list to copy the original value linked list.
        ListNode[] copySet = new ListNode[capacity];

        int j = 0; // a cursor to count the total element in the linked list.
        Iterator it = new ItrElement();

        // copy linked list
        while (it.hasNext()) {
            copySet[j] = ((ItrElement) it).next();
            j++;
        }

        capacity *= 2;  // doubling the capacity.
        valueSet = new ListNode[capacity];  // reset the value linked list with new capacity.
        //occurrenceSet = new int[capacity]; // reset the occurrence set with new capacity.
        head = null;
        tail = null;
        distinctValueNum = 0;
        valueNum = 0;

        // copy the elements back from the copy linked list to the original one which has been doubled capacity.
        for ( int i = 0; i < j ; i++) {
            if (copySet[i] != null) {
                add((T)copySet[i].element, copySet[i].elementNum);
            }
        }
    }

    /**
     * expected time complexity: O(1)
     * time complexity: O(n)
     * space complexity: O(1)
     *
     * Checks if the element is in the set (at least once).
     *
     * @param element to check
     * @return true if the element is in the set, else false.
     */
    @Override
    public boolean contains(T element) {
        int key = indexOf(element);

        if (valueSet[key] == null || key == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * expected time complexity: O(1)
     * time complexity: O(n)
     * space complexity: O(1)
     *
     * calculate the index of the input element to be added or removed in the value set
     * considered linear probing.
     *
     * @param element element to be added or removed
     * @return the index of the element to be added or removed in the value set
     */
    private int indexOf(T element) {
        // compression the element get the position of index where the
        // element expected to be added or removed.
        int key = element.hashCode() % capacity;

        // when the index in value set already have an element
        // check if it it equal to the input element and consider linear probing.
        if (valueSet[key] != null) {
            while (key < capacity) {
                if (valueSet[key] == null) {
                    return key;
                }
                if (valueSet[key].element.equals(element)) {
                    return key;
                }
                key++;
            }

            int i = 0;
            key = element.hashCode() % capacity;

            while (i++ < key){
                if (valueSet[key] == null) {
                    return key;
                }
            }
            return -1;
        } else  {
            return key;
        }
    }

    /**
     * expected time complexity: O(1)
     * time complexity: O(n)
     * space complexity: O(1)
     *
     * Returns the count of how many occurrences of the given elements there
     * are currently in the set.
     *
     * @param element to check
     * @return the count of occurrences of element
     */
    @Override
    public int count(T element) {
        // compression the element including linear probing
        int key = indexOf(element);

        if (valueSet[key] == null || key == -1) {
            return 0;
        } else {
            return valueSet[key].elementNum;
        }
    }

    /**
     * expected time complexity: O(1)
     * time complexity: O(n)
     * space complexity: O(1)
     *
     * Removes a single occurrence of element from the set.
     *
     * @param element to remove
     * @throws NoSuchElementException if the set doesn't currently
     *         contain the given element
     * @require element != null
     */
    @Override
    public void remove(T element) throws NoSuchElementException {
        remove(element, 1);
    }

    /**
     * expected time complexity: O(1)
     * time complexity: O(n)
     * space complexity: O(1)
     *
     * Removes several occurrences of the element from the set.
     *
     * @param element to remove
     * @param count the number of occurrences of element to remove
     * @throws NoSuchElementException if the set contains less than
     *         count occurrences of the given element
     * @require element != null && count >= 0
     */
    @Override
    public void remove(T element, int count) throws NoSuchElementException {
        // compression the element including linear probing
        int key = indexOf(element);

        // check if the element exist in value linked list
        if (valueSet[key] == null || valueSet[key].elementNum < count) {
            throw new NoSuchElementException();
        } else {
            //occurrenceSet[key] -= count;
            valueSet[key].elementNum -= count;
            valueNum -= count;

            if (valueSet[key].elementNum == 0 ) {
                distinctValueNum--;
                if (valueSet[key].prev != null) {
                    valueSet[key].prev.next = valueSet[key].next;
                } else {
                    this.head = valueSet[key].next;
                }
                if (valueSet[key].next != null) {
                    valueSet[key].next.prev = valueSet[key].prev;
                } else {
                    this.tail = valueSet[key].prev;
                }
                valueSet[key] = null;
            }
        }
    }

    /**
     * time complexity: O(1)
     * space complexity: O(1)
     *
     * Returns the total count of all elements in the multiset.
     *
     * Note that duplicates of an element all contribute to the count here.
     *
     * @return total count of elements in the collection
     */
    @Override
    public int size() {
        return valueNum;
    }

    /**
     * time complexity: O(1)
     *
     * Returns the maximum number of *distinct* elements the internal data
     * structure can contain before resizing.
     *
     * @return capacity of internal array
     */
    @Override
    public int internalCapacity() {
        return capacity;
    }

    /**
     * time complexity: O(1)
     *
     * Returns the number of distinct elements currently stored in the set.
     *
     * @return count of distinct elements in the set
     */
    @Override
    public int distinctCount() {
        return distinctValueNum;
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    /**
     * Returns an iterator for element in the value linked list node in left to right sequence.
     *
     * @returns an iterator over the elements in in order from leftmost to rightmost.
     */
    private class Itr implements Iterator<T> {
        int counter = 0;
        ListNode pointer = head;
        int occ = (pointer == null) ? -1 : pointer.elementNum;
        /**
         * runtime: O(1)
         * memory complexity: O(1)
         */
        public boolean hasNext() {
            if (counter == valueNum) {
                return false;
            }
            return true;
        }

        /**
         * runtime: O(1)
         * memory complexity: O(1)
         */
        public T next() {
            if (occ == 0) {
                pointer = pointer.next;
                occ = (pointer == null) ? -1 : pointer.elementNum;
            }
            if(hasNext()) {
                occ--;
                counter++;
                return (T)pointer.element;
            }
            return null;
        }
    }

    /**
     * Returns an iterator for the value linked list node in left to right sequence.
     *
     * @returns an iterator over the nodes in in order from leftmost to rightmost.
     */
    private class ItrElement implements Iterator<ListNode> {
        int counter = 0;
        ListNode pointer = head;
        int occ = (pointer == null) ? -1 : pointer.elementNum;
        /**
         * runtime: O(1)
         * memory complexity: O(1)
         */
        public boolean hasNext() {
            if (counter == valueNum) {
                return false;
            }
            return true;
        }

        /**
         * runtime: O(1)
         * memory complexity: O(1)
         */
        public ListNode next() {
            if (occ == 0) {
                pointer = pointer.next;
                occ = (pointer == null) ? -1 : pointer.elementNum;
            }
            if(hasNext()) {
                occ--;
                counter++;
                return pointer;
            }
            return null;
        }
    }

}