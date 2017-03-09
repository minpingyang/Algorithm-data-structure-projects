// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 103, Assignment 4
 * Name:
 * Usercode:
 * ID:
 */

import java.util.*;
import ecs100.*;
/**
 * Class Images implements a list of images.
 * 
 * Each image is represented with an ImageNode object.
 * The ImageNode objects form a linked list. 
 * 
 * An object of this class maintains the reference to the first image node and
 * delegates operations to image nodes as necessary. 
 * 
 * An object of this class furthermore maintains a "cursor", i.e., a reference to a location in the list.
 * 
 * The references to both first node and cursor may be null, representing an empty collection. 
 * 
 * @author Thomas Kuehne
 */

public class Images implements Iterable<String>
{
    private ImageNode head;     // the first image node
    private ImageNode cursor;   // the current point for insertion, removal, etc. 

    /**
     * Creates an empty list of images.
     */
    public Images() {
        cursor = head = null;

    }

    /**
     * @return the fileName of the image at the current cursor position.
     * 
     * This method relieves clients of Images from knowing about image nodes and the 'getFileName()' method.
     */
    public String getImageFileNameAtCursor() {
        // deal with an inappropriate call gracefully
        if (cursor == null)
            return "";  // the correct response would be to throw an exception.

        return cursor.getFileName();
    }

    /**
     * @return the current cursor position.
     * 
     * Used by clients that want to save the current selection in order to restore it after an iteration.
     */
    public ImageNode getCursor() {
        return cursor;
    }

    /**
     * Sets the cursor to a new node.
     * 
     * @param newCursor the new cursor position
     */
    public void setCursor(ImageNode newCursor) {
        cursor = newCursor;
    }

    /**
     * Positions the cursor at the start
     *    
     * For the core part of the assignment.
     */
    public void moveCursorToStart() {
        /*# YOUR CODE HERE */
        if (head == null)
            return;
        cursor=head;

    }

    /**
     * Positions the cursor at the end
     *
     * For the core part of the assignment.
     * 
     * HINT: Consider the list could be empty. 
     */
    public void moveCursorToEnd() {
        /*# YOUR CODE HERE */
        if(cursor==null){
            return;
        }

        while(cursor.getNext() != null){
            cursor = cursor.getNext();
        }
        // cursor = cursor.getNext(); ???
    }

    /**
     * Moves the cursor position to the right. 
     */
    public void moveCursorRight() {
        // is it impossible for the cursor to move right?
        if (cursor == null  ||  cursor.getNext() == null)
            return;

        // advance the cursor
        cursor = cursor.getNext();
    }

    /**
     * Moves the cursor position to the left.
     * 
     * Assumption: 'cursor' points to a node in the list!
     */
    public void moveCursorLeft() {  
        // is it impossible for the cursor to move left?
        if (head == null || cursor == head)
            return;

        // setup an initial attempt to a reference to the node before the current cursor 
        ImageNode previous = head;

        // while the node before the cursor has not been found yet, keep advancing
        while (previous.getNext() != cursor) {
            previous = previous.getNext();
        }

        cursor = previous;
    }

    /**
     * Returns the number of images
     * 
     * @return number of images
     */
    public int count() {
        if (head == null)          // is the list empty?
            return 0;                // yes -> return zero

        return head.count();   // no -> delegate to linked structure
    }

    /**
     * Adds an image after the cursor position
     * 
     * For the core part of the assignment.
     * 
     * @param imageFileName the file name of the image to be added
     * 
     * HINT: Consider that the current collection may be empty.
     * HINT: Create a new image node here and and delegate further work to method 'insertAfter' of class ImageNode.
     * HINT: Pay attention to the cursor position after the image has been added. 
     * 
     */
    public void addImageAfter(String imageFileName) {
        /*# YOUR CODE HERE */
        if(imageFileName == null) return;//checks if the new node is empty
        if(head==null){

            head=new ImageNode(imageFileName,null);          
            cursor=head;
        }else{
            ImageNode newNode=new ImageNode(imageFileName,null);
            cursor.insertAfter(newNode);
            moveCursorRight();
        }

    }

    /**
     * Adds an image before the cursor position
     * 
     * For the completion part of the assignment.
     * 
     * @param imageFileName the file name of the image to be added
     * 
     * HINT: Create a new image node here and then
     *         1. Consider that the current collection may be empty.
     *         2. Consider that the head may need to be adjusted.
     *         3. if necessary, delegate further work to 'insertBefore' of class ImageNode.
     * HINT: Pay attention to the cursor position after the image has been added. 
     * 
     */ 
    public void addImageBefore(String imageFileName) {  
        /*# YOUR CODE HERE */
        ImageNode added = new ImageNode (imageFileName,null);
        if(head==null){
            head = added;         
            cursor=head;
        }else if(cursor == head){ // head will changed
            ImageNode temp=head;
            head=added;
            added.setNext(temp);
            cursor=head;
        }else{
            head.insertBefore(added,cursor);
            moveCursorLeft();
        }
    }

    /**
     * Removes all images.
     *   
     * For the core part of the assignment.
     */
    public void removeAll()
    {
        /*# YOUR CODE HERE */
        cursor=head=null;
        //cursor=null;
    }

    /**
     * Removes an image at the cursor position
     *
     * For the core part of the assignment.
     * 
     * HINT: Consider that the list may be empty.
     * 
     * HINT: Handle removing at the very start of the list in this method and 
     * delegate the removal of other nodes by using method 'removeNodeUsingPrevious' from class ImageNode. 
     * 
     * HINT: Make sure that the cursor position after the removal is correct. 
     */

    public void remove() {
        /*# YOUR CODE HERE */
        /*
        if(cursor!=null&&cursor!=head){
        ImageNode pre=head.nodeBefore(cursor);
        cursor.removeNodeUsingPrevious(pre);
        cursor=pre;
        } 
        else if(cursor==head){
        cursor=head.getNext();
        head=head.getNext();
        }
        else{
        removeAll();
        return;
        }
         */

        if(head==null) return;
        if(head.count()==1){ head=cursor=null;}
        else if(cursor==head){
            cursor=head.getNext();
            head=head.getNext();
        }else {
            ImageNode newNode=cursor.getNext();
            moveCursorLeft();
            cursor.setNext(newNode);

        }

    }
    /**
     * Reverses the order of the image list so that the last node is now the first node, and 
     * and the second-to-last node is the second node, and so on
     * 
     * For the completion part of the assignment.
     * 
     * HINT: Make sure there is something worth reversing first.
     * HINT: You will have to use temporary variables.
     * HINT: Don't forget to update the head of the list.
     */
    public void reverseImages() {
        /*# YOUR CODE HERE */   
        if(head==null) return;
        ImageNode headNext=head.getNext();
        ImageNode last=head;
        while(headNext!=null){
            ImageNode temp=head;
            head=headNext;
            headNext=head.getNext();
            head.setNext(temp);
        }
        setCursor(last);
        cursor.setNext(null);
        cursor=head;
    }

    /** 
     * @return an iterator over the items in this list of images. 
     * 
     * For the completion part of the assignment.
     * 
     */
    public Iterator <String> iterator() {
        /*# YOUR CODE HERE */   

        return new ImagesIterator(this) ; // to make this class compile. PLEASE FIX THIS LINE
    }

    /** 
     * Support for iterating over all images in an "Images" collection. 
     * 
     * For the completion part of the assignment.
     * 
     */
    private class ImagesIterator implements Iterator<String> {

        // needs fields, constructor, hasNext(), next(), and remove()

        // fields
        /*# YOUR CODE HERE */   
        private Images images;
        private ImageNode current;
        private ImagesIterator(Images images) {
            /*# YOUR CODE HERE */   
            this.images=images;
            current=head;
        }

        /**
         * @return true if iterator has at least one more item
         * 
         * For the completion part of the assignment.
         * 
         */
        public boolean hasNext() {
            /*# YOUR CODE HERE */   

            return current!=null; // to make this class compile. PLEASE FIX THIS LINE
        }

        /** 
         * Returns the next element or throws a 
         * NoSuchElementException exception if none exists. 
         * 
         * For the completion part of the assignment.
         *  
         * @return next item in the set
         */
        public String next() {
            /*# YOUR CODE HERE */

            if(current==null){
                throw new NoSuchElementException();
            }
            else{
                ImageNode temp=current;
                current=current.getNext();
                return temp.getFileName();

            }
            /*
            if(head!=null&&head!=cursor){
            cursor=cursor.next;
            return getImageFileNameAtCursor;
            }else if(head!=null&&head==cursor){
            return getImageFileNameAtCursor;
            }else {
            throw new NoSuchElementException();
            }
             */
            //return null; // to make this class compile. PLEASE FIX THIS LINE
        }

        /** 
         *  Removes the last item returned by the iterator from the set.
         *  Not supported by this iterator.
         */
        public void remove() {
            throw new UnsupportedOperationException();        
        }
    }
}
