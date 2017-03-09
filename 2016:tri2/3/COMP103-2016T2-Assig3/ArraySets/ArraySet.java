// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 103, Assignment 3
 * Name:
 * Usercode:
 * ID:
 */

import java.util.*;

/**
 *  ArraySet - a Set collection;
 *
 *  The implementation uses an array to store the items
 *  and a count variable to store the number of items in the array.
 * 
 *  The items in the set should be stored in positions
 *    0, 1,... (count-1) of the array.
 * 
 *  The length of the array when the set is first created should be 10. 
 * 
 *  The items need not be in any particular order, and may change the
 *    order when an item is removed. There is no need to shift all the items 
 *    up or down to keep them in a specific order.
 * 
 *  Note that a set does not allow null items or duplicates.
 *  Attempting to add null should throw an IllegalArgumentException exception
 *  Adding an item which is already present should simply return false, without
 *    changing the set.
 *  It should always compare items using equals()  (not using ==)
 *  When the array is full, it will create a new array of double the current
 *    size, and copy all the items over to the new array
 */

public class ArraySet <E> extends AbstractSet <E> {

    // Data fields
    /*# YOUR CODE HERE */
    private E[] data;
    private int count;
    // --- Constructors --------------------------------------

    @SuppressWarnings("unchecked")  // this will stop Java complaining
    public ArraySet() {
        /*# YOUR CODE HERE */
        data=(E[])new Object[10];
        count=0;
    }

    // --- Methods --------------------------------------
    /** 
     * @return the number of items in the set  
     */
    public int size () {
        return count;

    }

    /** 
     *  Adds the specified item to this set 
     *  (if it is not already in the set).
     *  Will not add a null value (throws an IllegalArgumentException in this case).
     *  
     *  @param item the item to be added to the set
     *  @return true if the collection changes, and false if it did not change.
     */
    public boolean add(E item) {
        /*# YOUR CODE HERE */

        boolean duplicate=false;
        if(item!=null){
            for(int i=0;i<count;i++){
                Object tem=data[i];
                if(item.equals(tem)){
                    duplicate=true;
                    break;
                }
            }
            if(!duplicate){
                if(count<data.length){

                    data[count]=item;
                    count++;
                }else{
                    this.ensureCapacity();
                    data[count]=item;
                    count++;
                }

                return true;
            }
        }else{

            throw new IllegalArgumentException();

        }
        return false;

    }

    /** 
     * @return true if this set contains the specified item. 
     * 
     */
    public boolean contains(Object item) {
        /*# YOUR CODE HERE */
        if(item!=null){
            for(int i=0;i<count;i++){
                Object tem=data[i];
                if(item.equals(tem)){
                    return true;

                }
            }
            return false;
        }
        return false;
    }

    /** 
     *  Removes an item matching a given item.
     *  @return true if the item was present and then removed.
     *  Makes no changes to the set and returns false if the item is not present.
     */
    public boolean remove (Object item) {
        /*# YOUR CODE HERE */
        int index=0;
        if(contains(item)){
            for(int i=0;i<count;i++){
                Object tem=data[i];
                if(item.equals(tem)){
                    index=i;
                    data[index]=data[count-1];
                    data[count-1]=null;
                    count--;
                    return true;
                }
            }
        }
        return false;
    }

    /** 
    * 
    * Ensures data array has sufficient capacity (length)
    * to accomodate a new item 
    */
    @SuppressWarnings("unchecked")  // this will stop Java complaining
    private void ensureCapacity () {
        /*# YOUR CODE HERE */
       // boolean ifFull=true;
        for(int i=0;i<count;i++){
            Object tem=data[i];
            
        }
        if(count==data.length){
            E[] copy = Arrays.copyOf(data, 2*data.length);
            data=copy;
        }
    }

    // You may find it convenient to define the following method and use it in
    // the methods above, but you don't need to do it this way.

    /** 
     *  Finds the index of an item in the dataarray.
     *  Assumes that the item is not null.
     *  
     *  @return the index of the item, or -1 if not present
     */
    private int findIndexOf(Object item) {
        /*# YOUR CODE HERE */
        int index=0;
        if(item!=null){
            for(int i=0;i<data.length;i++){
                Object tem=data[i];
                if(item.equals(tem)){
                    index=i;
                    return index;
                }
            }
        }
        return -1;
    }

    /** ---------- The code below is already written for you ---------- **/

    /** 
     * @return an iterator over the items in this set. 
     * 
     */
    public Iterator <E> iterator() {
        return new ArraySetIterator<E>(this);
    }

    private class ArraySetIterator <E> implements Iterator <E> {

        // needs fields, constructor, hasNext(), next(), and remove()

        // fields
        private ArraySet<E> set;
        private int nextIndex = 0;
        private boolean canRemove = false;

        // constructor
        private ArraySetIterator(ArraySet<E> s) {
            set = s;
        }

        /**
         * @return true if iterator has at least one more item
         */
        public boolean hasNext() {
            return (nextIndex < set.count);
        }

        /** 
         * Returns the next element or throws a 
         * NoSuchElementException exception if none exists. 
         * 
         * @return next item in the set
         */
        public E next() {
            if (nextIndex >= set.count)
                throw new NoSuchElementException();

            canRemove = true;

            return set.data[nextIndex++];
        }

        /** 
         *  Removes the last item returned by the iterator from the set.
         *  Can only be called once per call to next.
         */
        public void remove() {
            if (! canRemove)
                throw new IllegalStateException();

            nextIndex--;
            set.count--;
            set.data[nextIndex] = set.data[set.count];
            set.data[set.count] = null;
            canRemove = false;
        }
    }
}

