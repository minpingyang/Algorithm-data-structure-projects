// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103, Assignment 9
 * Name: Minping Yang
 * Usercode: yangminp
 * ID: 300364234
 */

import java.util.Iterator;
import java.util.Queue;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/** ArrayQueueTest
 *  A JUnit class for testing an ArrayQueue
 */

public class ArrayQueueTest {

    private Queue<String> queue;

    /** initialise set to be an empty set before each test is run */
    @Before
    public void initialiseEmptyQueue() {
        queue = new ArrayQueue<String>();
    }

    /** method to initialise the set with the n values "v1", "v2", ... */
    public void fillQueue(int n){
        for (int i = 1; i <= n; i++) {
            queue.offer("v" + i);
        }
    }
    //-------------------------------------------------------------------

    @Test
    public void testIsEmptyOnEmptyQueue() {
        assertTrue("A new ArrayQueue should be empty", queue.isEmpty());
    }

    @Test
    public void testEmptyQueueHasSizeZero() {
        assertEquals("An empty queue should have size zero", 0, queue.size());
    }

    
    
     @Test
    public void testPeekEmptyIsNull() {
        assertNull("Peek an empty arrayQueue should be null", queue.peek());
    }

    @Test
    public void testPeek(){
        fillQueue(20);
        assertEquals("Size should be 20 after peeking", 20, queue.size());
        assertEquals("Peek should retrun the first item", "v" + 1, queue.peek());      
    }

    @Test
    public void testPollEmptyIsNull() {
        assertNull("Poll an empty arrayQueue should be null", queue.poll());
    }

    @Test
    public void testPoll(){
        fillQueue(20);
        queue.poll();
        assertEquals("Size should be 19 after polling", 19, queue.size());
        assertEquals("Poll should retrun the first item", "v" + 2, queue.poll());       
    }

    @Test
    public void testOffer() {
        assertFalse("Should return null when offering a null.", queue.offer(null));
        for (int i = 1; i <= 20; i++) {
            assertTrue("Queue should successfully offer item " + i, queue.offer("v" + i));
            assertFalse("Queue should not be empty after offer", queue.isEmpty());
            assertEquals("Size should be " + i + " after " + i + " offers", i, queue.size());
        }
    }

    @Test
    public void testHasNextOnEmptyIterator() {
        Iterator<String> iterator = queue.iterator();

        assertFalse("An empty queue does not have next item.", iterator.hasNext());
    }

    @Test
    public void testIterator() {
        fillQueue(22);
        Iterator <String> iterator = queue.iterator();

        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }

        assertEquals("Iterator should have returned 22 items", 22, count);
    }

    public static void main(String args[]) {
        org.junit.runner.JUnitCore.main("ArrayQueueTest");
    }

}
