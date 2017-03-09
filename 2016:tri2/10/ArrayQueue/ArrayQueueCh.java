// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 103, Assignment 9
 * Name:Minping Yang
 * Usercode:
 * ID:300364234
 */

import java.util.*;
import java.util.concurrent.*;

/*# YOUR CODE HERE */

/**
 * 
 */

public class ArrayQueueCh <E> extends AbstractQueue <E> implements BlockingQueue<E> {

    private static int INITIALCAPACITY = 16;

    private E[] data;
    private int front = 0;  //the index of the first item in the queue
    private int back = 0;   //the index where the next new item will go
    // items are stored from front..back-1
    // if front == back, then the queue is empty.

    @SuppressWarnings("unchecked")  // this will stop Java complaining
    public ArrayQueueCh() {
        data = (E[]) new Object[INITIALCAPACITY];
    }

    /** Return the number of items in collection */
    public synchronized int size () {
        return back - front;     
    }

    /** Return the item at the front of the queue,
     *  or null if the queue is empty, but does not change the queue 
     */
    public synchronized E peek() {
        /*#my code here*/
        if (isEmpty())
            return null;
        else
            return data[front];
    }

    /** Remove and return the item at the front of the queue,
     *  or null if the queue is empty 
     */
    public synchronized E poll() {
        /*#my code here*/
        if (isEmpty())
            return null;
        else {       
            E ans = data[front];
            front = (front+1);
            return ans;
        }
    }

    /** Add an item onto the back for the queue, unless the item is null
     *  If the item was added, it returns true,
     *  if the item was null, it returns false 
     */
    public synchronized boolean offer(E item) {
        if (item == null)
            return false;
        else {
            ensureCapacity();
            data[back] = item;
            back = (back+1);
            return true;
        }
    }

    /** Ensure data array has sufficient room to add a new item */
    @SuppressWarnings("unchecked") // this will stop Java complaining
    private void ensureCapacity () {
        if (back == data.length){  // there is no room to add the next item
            // if we are at least 1/2 empty just shift items down 
            if (front > data.length/2) {
                int j = 0;           
                for (int i = front; i < back; i++){ 
                    /*#my code here*/
                    data[j] = data[i];
                    j++;
                }
                back = back-front;     // reset back and front.
                front = 0;
            }
            else { // need to get a bigger array
                E[] newArray = (E[])(new Object[data.length*2]);
                int j=0;
                for (int i = front; i < back; i++){
                    newArray[j++] = data[i];
                }
                back = back-front;     // reset back and front.
                front = 0;
                data = newArray;
            }
        }
    }

    public synchronized int drainTo(Collection <? super E>c){
        return 0;
    }

    public synchronized int drainTo(Collection <? super E>c, int maximum){
        return 0;
    }

    public synchronized boolean offer(E e, long time, TimeUnit unit){
        return offer(e);
    }

    public synchronized E poll(long time, TimeUnit unit){
        return poll();
    }

    public synchronized void put(E e){
        offer(e);
        notify();
    }

    public synchronized E take() throws InterruptedException{
        while(isEmpty()){
            wait();
        }
        return poll();
    }

    public int remainingCapacity(){
        return Integer.MAX_VALUE;
    }


    /** Return an iterator over the items in the list */
    public Iterator <E> iterator() {
        return new ArrayQueueChIterator<E>(this);
    }

    /** Iterator for ArrayQueue.
     *  This implementation is not smart, and may be corrupted if
     *  any changes are made to the ArrayQueue that it is iterating down.
     *  Note that because it is an inner class, it has access to the 
     *  ArrayQueue's private fields.
     */
    private class ArrayQueueChIterator <E> implements Iterator <E> {
        // needs fields, constructor, hasNext(), next(), and remove()

        private ArrayQueueCh<E> queue; // reference to the list it is iterating down
        private int nextIndex;       // the index of the next item the iterator will return

        private ArrayQueueChIterator (ArrayQueueCh <E> q) {
            queue = q;
            nextIndex= q.front;
        }

        /** Return true if iterator has at least one more item */
        public boolean hasNext () {
            return (nextIndex < back);
        }

        /** Return next item in the set */
        public E next () {
            if (nextIndex == back) 
                throw new NoSuchElementException(); 
            E item = queue.data[nextIndex];
            nextIndex = (nextIndex+1);
            return item;
        }

        /** Remove from the queue the last item returned by the iterator.
         *  The queue does not permit this operation
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}