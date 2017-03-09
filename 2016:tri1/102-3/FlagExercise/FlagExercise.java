// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for Assignment 2, COMP102
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.awt.Color;

/** Draws flags of various countries.
The correct dimensions of the official flags varies from country to country.
Many flags are 2/3 as high as they are wide.
It is fine to make it 1/2 as high as they are wide.
It is also fine on the flags of Bangladesh, Japan, etc to make the circles
exactly half the height of the flags.
The exact colours of the flags will also be difficult to match;
It is fine to use the standard colours: red, green, blue, orange.
You can find lots of details, including the correct dimensions and colours, of flags from
http://www.crwflags.com/fotw/flags/    
 */
public class FlagExercise{

    public static final double top = 100;
    public static final double left = 100;

    /** The flag of Japan has a white background and
     *  a red circle in the middle.
     *  The flag is 2/3 as high as it is wide (ratio 2:3).
     */
    public void japanFlag(){
        /*# YOUR CODE HERE */

    }

    /** The flag of Indonesia has two horizontal stripes;
     *  the top is red and the bottom is white.
     *  The flag is 2/3 as high as it is wide (ratio 2:3).
     */
    public void indonesiaFlag(){
        /*# YOUR CODE HERE */

    }

    /** The flag of Austria has three horizontal stripes;
     *  the top and bottom are red and the middle is white.
     *  The flag is 2/3 as high as it is wide (ratio 2:3).
     */
    public void austriaFlag(){
        /*# YOUR CODE HERE */

    }

    /** The flag of Bangladesh has a green background and
     *   a red circle in the middle (off-set to left).
     *  The flag is 3/5 as high as it is wide (ratio 3:5).
     */
    public void bangladeshFlag(){
        /*# YOUR CODE HERE */

    }

    /** The flag of Niger has three horizontal stripes,
     *  orange at the top, white in the middle, green at the bottom,
     *  and an orange circle in the middle of the middle stripe.
     *  The flag is 6/7 as high as it is wide (ratio 6:7).
     */
    public void nigerFlag(){
        /*# YOUR CODE HERE */

    }

    /** ---------- The code below is already written for you ---------- **/
    /** Constructor: set up the user interface and buttons */ 
    public FlagExercise(){
        UI.clearPanes();
        UI.addButton("Clear", UI::clearGraphics );
        UI.addButton("japanFlag", this::japanFlag );
        UI.addButton("indonesiaFlag", this::indonesiaFlag );
        UI.addButton("austriaFlag", this::austriaFlag );
        UI.addButton("bangladeshFlag", this::bangladeshFlag );
        UI.addButton("nigerFlag", this::nigerFlag );
        UI.addButton("Quit", UI::quit);
    }


}
