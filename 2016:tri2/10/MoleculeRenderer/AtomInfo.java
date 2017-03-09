// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103, Assignment 9
 * Name: 
 * Usercode: 
 * ID: 
 */

import java.awt.Color;
import java.util.*;

/** 
 *  Represents information about an atomic element,
 *  including the color and size to render it on the graphics pane.
 *  
 *  Because an element doesn't change its name, size or colour,
 *  it is represented by an "immutable" object - an object with fields
 *  that cannot be modified.
 *  
 *  This is what the "final" keyword does - once a value is assigned to
 *  the field, it cannot be changed.  Therefore, it is safe to make these fields
 *  public.  
 */

public class AtomInfo {
    public final String name;
    public final double radius;   // the size of the atom
    public final Color color;

    /** 
     * Constructor 1 requires the name of the element, the radius, and a color.
     */
    public AtomInfo (String name, int radius, Color color) {
        this.name = name;
        this.color = color;
        this.radius = radius;
    }

    /**
     *  Constructor 2 requires a Scanner from which the name of the element,
     *  the radius, and the three components of a color can be read
     */
    public AtomInfo (Scanner scan) {
        this.name = scan.next();
        this.radius = scan.nextDouble();
        this.color = new Color(scan.nextInt(), scan.nextInt(), scan.nextInt());
    }
}
