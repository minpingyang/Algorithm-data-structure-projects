// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 102 Assignment 2 
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;

/** Program for calculating various things
Some methods convert between two units
Other methods perform other simple calculations */
public class CalculatorExercise{

    /** Convert from miles to kilometers */
    public void milesToKilometers(){
        /*# YOUR CODE HERE */

    }        

    /** Calculate area of triangle
     */
    public void triangleArea(){
        /*# YOUR CODE HERE */

    }

    /** Convert from grams to ounces
     */
    public void gramsToOunces(){
        /*# YOUR CODE HERE */

    }

    /** Compute surface of a sphere */
    public void surfaceAreaOfSphere(){
        /*# YOUR CODE HERE */

    }

    /** Convert from kelvin to fahrenheit t */
    public void kelvinToFahrenheit(){
        /*# YOUR CODE HERE */

    }

    /** Compute cost of order of peppers */
    public void costOfPeppers(){
        /*# YOUR CODE HERE */

    }

    /** ---------- The code below is already written for you ---------- **/
    /** Constructor: set up the user interface and buttons */ 
    public CalculatorExercise(){
        UI.clearPanes();
        UI.addButton("milesToKilometers", this::milesToKilometers );
        UI.addButton("triangleArea", this::triangleArea );
        UI.addButton("gramsToOunces", this::gramsToOunces );
        UI.addButton("surfaceAreaOfSphere", this::surfaceAreaOfSphere );
        UI.addButton("kelvinToFahrenheit", this::kelvinToFahrenheit );
        UI.addButton("costOfPeppers", this::costOfPeppers );
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1);   // expands the text area
    }


}
