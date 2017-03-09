// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.awt.Color;

/** Frog
 *  A new frog starts at the given position, with the given direction, and 
 *     has either a "light" or "dark" shade.
 *  Frogs can turn in 4 directions: left, right, up, and down. 
 *  Frogs move around at a constant speed in an arena with an enclosing wall,
 *     following its direction, until it hits a wall. In which case it stops moving.
 *  Frog can grow in size, and (for the completion) can also shrink by resetting their size
 *      to the orginal value.
 *
 *  The walls of the arena are determined by the constants:
 *    FrogGame.TopWall, FrogGame.BotWall, FrogGame.LeftWall and FrogGame.RightWall
 */

public class Frog {
    // Constants
    public static final int INITIAL_SIZE = 30;
    public static final int GROWTH_RATE = 1;
    public static final int SPEED = 2;

    // Fields
    private double frogSize=INITIAL_SIZE;
    private double left,top;
    private String dir;
    private String shade;
    public  int score=1;

    //Constructor 
    /** 
     * Make a new frog
     * The parameters specify the initial position of the top left corner,
     *   the direction, and the shade of the Frog ("light" or "dark")
     * We assume that the position is within the boundaries of the arena
     */
    public Frog(double left, double top, String dir, String shade)  {
        this.left=left;
        this.top=top;
        this.dir=dir;
        this.shade=shade;
        this.draw();
    }

    public double getTop(){
       return this.top;
    }
    public double getLeft(){
     return this.left;
    }
   

    public void setScore(int score){
        this.score=score;
    }

    public  int getScore(){
        return  this.score;
    }

    /**
     * Turn right
     */
    public void turnRight(){
        this.dir="Right";

    }

    /**
     * Turn left
     */
    public void turnLeft(){
        this.dir="Left";

    }

    /**
     * Turn up
     */
    public void turnUp(){
        this.dir="Up";

    }

    /**
     * Turn down
     */
    public void turnDown(){
        this.dir="Down";

    }

    /**
     * Moves the frog: 
     *   use SPEED unit forward in the correct direction
     *   by changing the position of the frog.
     * Make sure that the frog does not go outside the arena, by making sure 
     *  - the top of the frog is never smaller than FrogGame.TopWall
     *  - the bottom of the frog is never greater than FrogGame.BotWall
     *  - the left of the frog is never smaller than FrogGame.LeftWall
     *  - the right of the frog is never smaller than FrogGame.RightWall
     */
    public void move() {

        if(this.dir.equals("Left")&&this.left>FrogGame.LeftWall){
            this.left-=SPEED*4;
        }
        if(this.dir.equals("Right")&&this.left<FrogGame.RightWall-this.frogSize){
            this.left+=SPEED*4;
        }
        if(this.dir.equals("Up")&&this.top>FrogGame.TopWall){
            this.top-=SPEED*4;
        }
        if(this.dir.equals("Down")&&this.top<FrogGame.BotWall-this.frogSize){
            this.top+=SPEED*4;
        }

    }

    public void autoMove(double xBug,double yBug ){
        
        if(this.top<yBug&&this.top<FrogGame.BotWall-this.frogSize){
            this.top+=SPEED*4;
        }
        if(this.top>yBug&&this.top>FrogGame.TopWall){
            this.top-=SPEED*4;
        }
        if(this.left<xBug&&this.left<FrogGame.RightWall-this.frogSize){
            this.left+=SPEED*4;
        }
        if(this.left>xBug&&this.left>FrogGame.LeftWall){
            this.left-=SPEED*4;
        }
        
        
        
        
    }

    /**
     * Check whether the frog is touching the given point, eg, whether the
     *   given point is included in the area covered by the frog.
     * Return true if the frog is on the top of the position (x, y)
     * Return false otherwise
     */
    public boolean touching(double x, double y) {
        if(this.left>=x-this.frogSize&&this.left<=x+FrogGame.BugSize&&this.top>=y-this.frogSize&&this.top<=y+FrogGame.BugSize){    

            return true;
        }else{
            return false;
        }

    }

    /**
     * The Frog has just eaten a bug
     * Makes the frog grow larger by GROWTH_RATE.
     */
    public void grow(){        
        this.frogSize+=GROWTH_RATE;

        this.score++;
    }

    /**
     * The Frog has just bumped into a snake
     * Makes the frog size reset to its original size
     * ONLY EEDED FOR COMPLETION
     */
    public void shrink(){
        this.frogSize=INITIAL_SIZE;

    }

    /**
     * Draws the frog at the current position.
     */
    public void draw(){
        if(this.shade.equals("light")){
            UI.drawImage("lightfrog.jpg",this.left,this.top,this.frogSize,this.frogSize);
        }else{
            UI.drawImage("darkfrog.jpg",this.left,this.top,this.frogSize,this.frogSize);
        }

    }
}

