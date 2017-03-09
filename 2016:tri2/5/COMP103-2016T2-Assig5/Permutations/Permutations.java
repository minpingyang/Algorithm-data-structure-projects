// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103, Assignment 5
 * Name: Minping Yang
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.util.*;

/** 
 *  Prints out all permuations of a string
 *  The static method permute constructs all the permutations.
 *  The main method gets the string, calls recPermute, and prints the result.
 */
public class Permutations {
    
    /** Return a List of all the permutations of a String. */
    public static List <String> recPermute(String string){
        /*# YOUR CODE HERE */
        List<String> per = new ArrayList<String>();
        per.add(string); //add the original string to the list first
        permutation(string, string.length(), per); //start generating permutation
        return per;
    }
     /*# YOUR CODE HERE */
    public static List<String> permutation (String string, int index, List<String> list){
        if (index == 0) return list;  //ground case of the recursion
        else{
            permutation(string, index - 1, list);
            int localFirstIndex = string.length() - index;
            for (int i = localFirstIndex + 1; i < string.length(); i++){
                string = swap(string, localFirstIndex, i); //swap every one of the chars with the local first one
                list.add(string);
                permutation(string, index - 1, list);
                string = swap(string, i, localFirstIndex); //swap back the local original string
            }
        }
        return list;
    }
     /*# YOUR CODE HERE */
    //swap the i-th and j-th positions of a string
    public static String swap(String string, int i, int j){
        char[] c = string.toCharArray();
        char temp = c[i];
        c[i] = c[j];
        c[j] = temp;
        return new String(c);
    }   

    // Main
    public static void main(String[] arguments){
        String string = "";
        while (! string.equals("#")) {
            string = UI.askString("Enter string to permute - # to exit: ");
            List<String> permutations = recPermute(string);
            for (String p : permutations)
                UI.println(p);
            UI.println("---------");
        }
        UI.quit();
    }    
}
