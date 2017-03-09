// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 10
 * Name:
 * Usercode:
 * ID:
 */

import java.util.*;
import ecs100.*;
import java.awt.Color;
import java.io.*;

/** Rectangle represents a solid rectangle shape
 *   Implements the Shape interface.
 *   Needs fields to record the position, size, and colour
 */

public class Rectangle implements Shape {
    //fields
    private double x;  // one end
    private double y;
    private Color col;  // the colour of the Rect
    private double wd;
    private double ht;

    /** Constructor with explicit values
     *  Arguments are the x and y of the top left corner,
     *  the width and height, and the color. 
     */
    public Rectangle(double x, double y, double wd, double ht, Color col) {
        this.x=x;
        this.y=y;
        this.wd=wd;
        this.ht=ht;
        this.col=col;

    }

    /** [Completion] Constructor which reads values from a String
     *  that contains the specification of the Rectangle. 
     *  The format of the String is determined by the toString method.
     *     The first 3 integers specify the color;
     *     the following four numbers specify the position and the size.
     */
    public Rectangle(String description) {
        Scanner data = new Scanner(description);
        int red = data.nextInt();
        int green = data.nextInt();
        int blue = data.nextInt();
        this.col = new Color(red, green, blue);
        this.x = data.nextDouble();
        this.y = data.nextDouble();
        this.wd= data.nextDouble();
        this.ht = data.nextDouble();

    }

    /** Returns true if the point (u, v) is on top of the shape */
    public boolean on(double u, double v) {
        if(u>=this.x&&u<=this.x+this.wd&&v>=this.y&&v<=this.y+this.ht){
            return true;
        }else{
            return false;
        }

        //return false; // to make this class compile. PLEASE FIX THIS LINE
    }

    /** Changes the position of the shape by dx and dy.
     *  If it was positioned at (x, y), it will now be at (x+dx, y+dy)
     */
    public void moveBy(double dx, double dy) {
        this.x+=dx;
        this.y+=dy;
        //this.wd+=dx;
        //this.ht+=dy;

    }

    /** Draws the rectangle on the graphics pane. 
     *  It draws a black border and fills it with the color of the rectangle.
     */
    public void redraw() {
        UI.setColor(this.col);
        UI.fillRect(this.x,this.y,this.wd,this.ht);

    }

    /** [Completion] Changes the width and height of the shape by the
     *  specified amounts.
     *  The amounts may be negative, which means that the shape
     *  should get smaller, at least in that direction.
     *  The shape should never become smaller than 1 pixel in width or height
     *  The center of the shape should remain the same.
     */
    public void resize (double changeWd, double changeHt) {
        
            if(changeWd>=0&&changeHt>=0&&MiniDraw.pressedX>=(this.x+this.wd/2)&&MiniDraw.pressedY>=(this.y+this.ht/2)){// dir bot right && cheak potsiton
                this.wd += changeWd;
                this.ht += changeHt;
            }
            else if(changeWd>=0&&changeHt<=0&&MiniDraw.pressedX>=(this.x+this.wd/2)&&MiniDraw.pressedY<=(this.y+this.ht/2)){// drag from top right to expand
                this.y += changeHt;
               
                this.wd += changeWd;
                this.ht -= changeHt;
            }
            else if(changeWd<=0&&changeHt>=0&&MiniDraw.pressedX<=(this.x+this.wd/2)&&MiniDraw.pressedY>=(this.y+this.ht/2)){// drag from bot left
                this.x += changeWd;
                this.wd -= changeWd;
                this.ht += changeHt;
            }
            else if(changeWd<=0&&changeHt<=0&&MiniDraw.pressedX<=(this.x+this.wd/2)&&MiniDraw.pressedY<=(this.y+this.ht/2)){// drag from top right
                this.x += changeWd;
                this.y += changeHt;
                this.wd -= changeWd;
                this.ht -= changeHt;
            }
            else if(changeWd>=0&&changeHt>=0&&MiniDraw.pressedX<=(this.x+this.wd/2)&&MiniDraw.pressedY<=(this.y+this.ht/2)){
                this.x += changeWd;
                this.y += changeHt;
                this.wd -= changeWd;
                this.ht -= changeHt;

            }
            else if(changeWd>=0&&changeHt<=0&&MiniDraw.pressedX<=(this.x+this.wd/2)&&MiniDraw.pressedY>=(this.y+this.ht/2)){
                this.x += changeWd;
                this.wd -= changeWd;
                this.ht += changeHt;
            }
            else if(changeWd<=0&&changeHt>=0&&MiniDraw.pressedX>=(this.x+wd/2)&&MiniDraw.pressedY<=(this.y+this.ht/2)){
                this.y += changeHt;
                this.wd += changeWd;
                this.ht -= changeHt;
            }
            else if(changeWd<=0&&changeHt<=0&&MiniDraw.pressedX>=(this.x+wd/2)&&MiniDraw.pressedY>=(this.y+this.ht/2)){
                this.wd += changeWd;
                this.ht += changeHt;
            }
            
            if(this.wd<10){
                this.wd=10;
            }
            if(this.ht<10){
                this.ht=10;
            }
        

        
    }

    /** Returns a string description of the rectangle in a form suitable for
     *  writing to a file in order to reconstruct the rectangle later
     *  The first word of the string must be Rectangle 
     */
    public String toString() {
        return ("Rectangle "+col.getRed()+" "+col.getGreen()+" "+col.getBlue()+" "+this.x+" "+this.y+" "+this.wd+" "+this.ht);
    }

}
