// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 103, Assignment 8
 * Name: Minping Yang   
 * Usercode:
 * ID:300364234
 */
import java.util.*;

/**
 * Implements a priority queue based on a heap that is
 * represented with an array.
 */
public class HeapArrayQueue <E extends Comparable<? super E> > extends AbstractQueue <E> { 

    @SuppressWarnings("unchecked") 
    private E[] data = (E[])(new Comparable[7]);  // comparable??
    private int count = 0;

    public int size() {
        return count;
    }

    public boolean isEmpty() { 
        return size() == 0; 
    }

    /**
     * Returns the element with the top priority in the queue. 
     * 
     * HINT: This is like 'poll()' without the removal of the element. 
     * 
     * @returns the next element if present, or 'null' if the queue is empty.
     */
    public E peek() {
        /*# YOUR CODE HERE */
        if (isEmpty()) return null;

        return data[0];

    }

    /**
     * Removes the element with the top priority from the queue and returns it.
     * 
     * HINT: The 'data' array should contain a heap so the element with the top priority
     * sits at index '0'. After its removal, you need to restore the heap property again,
     * using 'sinkDownFromIndex(...)'.
     * 
     * @returns the next element in the queue, or 'null' if the queue is empty.
     */
    public E poll() {
        /*# YOUR CODE HERE */
        if (isEmpty()) return null;
        // assign the root to a temporary variable
        E first = data[0];
        count--;

        if (count > 0){
            data[0] = data[count];// swap the last item to the root
            sinkDownFromIndex(0);  // sink down in the partial tree
        }
        return first;
    }

    /**
     * Enqueues an element.
     * '
     * 
     * If the element to be added is 'null', it is not added. 
     * 
     * HINT: Make use of 'ensureCapacity' to make sure that the array can 
     * accommodate one more element. 
     * 
     * @param element - the element to be added to the queue
     * 
     * @returns true, if the element could be added
     */
    public boolean offer(E element) {
        /*# YOUR CODE HERE */
        if (element == null) return false;

        ensureCapacity();
        data[count++] = element;

        bubbleUpFromIndex(count - 1);

        return true;

    }

    private void sinkDownFromIndex(int nodeIndex) {
        /*# YOUR CODE HERE */
        int lChild = (2 * nodeIndex) + 1;  // left child index
        int rChild = lChild + 1;           //right child index

        if (lChild >= count) return;

        if (rChild < count && (data[lChild].compareTo(data[rChild]) > 0))
            lChild = rChild;

        if (data[nodeIndex].compareTo(data[lChild]) > 0){
            swap(nodeIndex, lChild);
            sinkDownFromIndex(lChild);
        }

    }

    private void bubbleUpFromIndex(int nodeIndex) {
        /*# YOUR CODE HERE */
        if (nodeIndex == 0) return;

        int parentIndex = (nodeIndex - 1) / 2;

        if (data[parentIndex].compareTo(data[nodeIndex]) > 0){
            swap(parentIndex, nodeIndex);
            bubbleUpFromIndex(parentIndex);
        }

    }

    /**
     * Swaps two elements in the supporting array.
     */
    private void swap(int from, int to) {
        E temp = data[from];
        data[from] = data[to];
        data[to] = temp;
    }

    /**
     *  Increases the size of the supporting array, if necessary
     */
    private void ensureCapacity() {
        if (count == data.length) {
            @SuppressWarnings("unchecked") 
            E[] newData = (E[])new Comparable[data.length * 2];
            
            // copy data elements
            for (int loop = 0; loop < count; loop++) {
                newData[loop] = data[loop];
            }
            data = newData;
        }
        return;
    }

    // no iterator implementation required for this assignment
    public Iterator<E> iterator() { 
        return null; 
    }
}
