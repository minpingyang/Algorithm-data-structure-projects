// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 10
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.util.*;
import java.awt.Color;
import java.io.*;

/** Dot represents a small circle shape of a fixed size (5 pixels)
 *   Implements the Shape interface.
 *   Needs fields to record the position, and colour
 */

public class Dot implements Shape{
    private double x;               // position of the center of the circle.
    private double y;
    private Color col;
    private static double radius = 5.0;  

    /** Construct a new Circle object */
    public Dot(double x, double y,Color col){
        this.x = x;
        this.y = y;
        this.col=col;
    }
    
     public Dot(String description) {
        Scanner data = new Scanner(description);
        int red = data.nextInt();
        int green = data.nextInt();
        int blue = data.nextInt();
        this.col = new Color(red, green, blue);
        this.x = data.nextDouble();
        this.y = data.nextDouble();
        
    }
    public void moveBy(double dx, double dy){
        this.x += dx;
        this.y += dy;
        
    }

   
    /* returns true if the point x,y is on the circle */
    public boolean on(double v, double u){
        return (Math.hypot(v-this.x, u-this.y) < this.radius);
    }

    /* Draw the circle at its current position */
    public void redraw(){
        UI.setColor(this.col);
        UI.fillOval(this.x-this.radius, this.y-this.radius, 2*this.radius, 2*this.radius);
    }
    public void resize(double changeWd, double changeHt){
        //
    }
    public String toString(){
    return ("Dot "+col.getRed()+" "+col.getGreen()+" "+col.getBlue()+" "+this.x+" "+this.y);
    }
}

