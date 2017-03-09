// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 103, Assignment 3
 * Name:
 * Usercode:
 * ID:
 */
//
import java.util.*;
import ecs100.*;
/**
 * SortedArraySet - a Set collection;
 *
 *  The implementation uses an array to store the items
 *  and a count variable to store the number of items in the array.
 * 
 *  The items in the set should be stored in positions
 *    0, 1,... (count-1) of the array.
 * 
 *  The length of the array when the set is first created should be 10. 
 * 
 *  Items in the array are kept in order, based on the "compareTo()" method.*************
 *  
 *  Binary search is used for searching for items.  *************
 *  
 *  Note that a set does not allow null items or duplicates.
 *  Attempting to add null should throw an IllegalArgumentException exception
 *  Adding an item which is already present should simply return false, without
 *    changing the set.
 *  It should always compare items using equals()  (not using ==)
 *  When the array is full, it will create a new array of double the current
 *    size, and copy all the items over to the new array
 */

// We need "extends Comparable" so that we can use the "compareTo()" method
public class SortedArraySet <E extends Comparable> extends AbstractSet <E> {

    // Data fields
    /*# Copy your declrations from ArraySet here */
    /*# YOUR CODE HERE */
    private E[] data;
    private int count;
    // --- Constructors --------------------------------------

    @SuppressWarnings("unchecked")  // this will stop Java complaining about the cast
    public SortedArraySet() {
        /*# Copy your code from ArraySet (constructor) here AND change "Object" to "String" */
        /*# YOUR CODE HERE */
        data=(E[])new String[10];
        count=0;

    }

    // --- Methods --------------------------------------
    /** 
     * @return the number of items in the set  
     */
    public int size () {
        /*# Copy your code from ArraySet size() method here */
        /*# YOUR CODE HERE */
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
    @SuppressWarnings("unchecked")
    public boolean add(E item) {
        /*# Copy your code from ArraySet add(E item) here 
         *  AND then modify it to insert the item at the right place
         *  so that the array data remains sorted 
         *  make use of a helper method "findIndexOf" 
         */
        /*# YOUR CODE HERE */

        int specialIndex=findIndexOf(item);
        
        if(item!=null){
            if(data[specialIndex]!=null){
                if(data[specialIndex].equals(item)){
                    return false;
                }
            }
            //sorted item with adding by using findIndexof();
            if(specialIndex<count){
                count++;
                ensureCapacity();
                E[] copy=(E[])new String[data.length];
                copy[specialIndex]=item;
                for(int i=0;i<specialIndex;i++){
                    copy[i]=data[i];
                }
                for(int j=specialIndex;j<count-1;j++){
                    copy[j+1]=data[j];
                }
                data=copy;
                return true;
            }else if(specialIndex==count){
                count++;
                ensureCapacity();
                data[specialIndex]=item;
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
    @SuppressWarnings("unchecked")  // stops Java complaining about the call to compare 
    public boolean contains(Object item) {
        E itm = (E) item;

        /*# Copy your code from ArraySet contains(Object item) method here 
         *  then potentially modify it to ensure that it works 
         *  with the new version of findIndexOf 
         */
        /*# YOUR CODE HERE */

        if(itm!=null){
            int indexFound=findIndexOf(itm);
            //System.out.println(indexFound);
            ensureCapacity();
            Object tem=data[indexFound];
            if(itm.equals(tem)){
                return true;

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
    @SuppressWarnings("unchecked")  // stops Java complaining about the call to compare 
    public boolean remove (Object item) {
        E itm = (E) item;

        /*# Copy your code from ArraySet remov(Object item) method here
         *  then modify it to ensure that 
         *  a) the array data remains sorted after the removal 
         *  b) the code works with the new version of findIndexOf
         */
        /*# YOUR CODE HERE */
        int index=0; 
        if(contains(itm)){
            int indexFound=findIndexOf(itm);
            ensureCapacity();
            Object tem=data[indexFound];
            if(item.equals(tem)){
                index=indexFound;
                data[index]=null;
                count--;;
                E[] copy=(E[])new String[data.length];
                int j=0; 
                for(int x=0;x<data.length;x++){
                    E tem2=data[x];
                    if(tem2!=null){
                        copy[j]=tem2;
                        j++;
                    }
                }
                data=copy;
                return true;
            }

        }

        return false;
    }

    /** 
    * 
    * Ensures data array has sufficient capacity (length)
    * to accomodate a new item 
    */
    @SuppressWarnings("unchecked")  // this will stop Java complaining about the cast
    private void ensureCapacity () {
        /*# Copy your code from ArraySet ensureCapacity() method here*/
        /*# you only need to change "Object" to "String" */
        /*# YOUR CODE HERE */

        if(count>=data.length){
            E[] tem=(E[]) new String [data.length*2];
            for(int i=0;i<count;i++){
                tem[i]=data[i];
            }

            data=tem;

        }
    }

    // It is much more convenient to define the following method 
    // and use it in the methods above.

    /** 
    *  Finds the index of where an item is in the dataarray,
    *  (or where it ought to be, if it's not there).
    *  Assumes that the item is not null.
    *  
    *  Uses binary search and requires that the items are kept in order.
    *  Uses the "compareTo()" method to compare two items with each other, e.g., as in
    *  "item1.compareTo(item2)", resulting in 
    *  0, if the items are equal,
    *  a value lower than 0, if item1 is smaller than item2,
    *  a value greater than 0, if item1 is greater than item2.
    *         
    *  @return the index of the item, or 
    *               the index where it should be inserted, if it is not present       
    */
    @SuppressWarnings("unchecked")  // stops Java complaining about the call to compare 
    private int findIndexOf(E item) {
        /*# YOUR CODE HERE */
        // binarySearch

        int low=0;
        int high=count-1;
        //System.out.println("cout: "+count);
        while(low<=high){
            int mid = (low+high)/2;
            int comp=item.compareTo(data[mid]);
            if(comp==0){return mid;}
            if(comp>0){
                low=mid+1;
            }
            else{
                high=mid-1;
            }
        }
        return low;

        /**
        int low=0;
        int high=count-1;

        while(low<=high){

        int valueItemAndLow=Math.abs(item.compareTo(data[low]));
        int valueHighAndLow=Math.abs(data[high].compareTo(data[low]));
        // int valueItemAndLow=item.compareTo(data[low]);
        // int valueHighAndLow=data[high].compareTo(data[low]);
        if(valueHighAndLow==0){
        valueHighAndLow=1;
        }
        int middle=low+(high-low)*(valueItemAndLow)/(valueHighAndLow);//
        //int middle=low+(high-low)*()/ 
        int comp=item.compareTo(data[middle]);
        if(comp==0){
        return middle;

        }
        else if(comp<0){
        high=middle-1;

        }
        else{
        low=middle+1;
        }
        ensureCapacity ();
        }

        return low;
         */
    }

    // --- Iterator and Comparator --------------------------------------
    /** ---------- The code below is already written for you ---------- **/

    /** 
     * @return an iterator over the items in this set. 
     * 
     */
    public Iterator <E> iterator() {
        return new SortedArraySetIterator(this);
    }

    private class SortedArraySetIterator implements Iterator <E> {
        // needs fields, constructor, hasNext(), next(), and remove()

        // fields
        private SortedArraySet<E> set;
        private int nextIndex = 0;
        private boolean canRemove = false;

        // constructor
        private SortedArraySetIterator(SortedArraySet<E> s) {
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

            set.remove(set.data[nextIndex-1]);
            canRemove = false;
        }
    }
}

