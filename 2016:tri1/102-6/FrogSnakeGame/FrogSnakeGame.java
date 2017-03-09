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
import java.lang.String;

/** FrogSnakeGame: Completion
 *  Game with two frogs that race to get to the bug first in order to eat it, and must avoid snakes.
 *  (uses keys: player 1: W/A/S/D for up/left/down/right; controls the "light" frog
 *              player 2: I/J/K/L for up/left/down/right; controls the "dark" frog
 *  Frogs move around at a constant speed in an arena with an enclosing wall.
 *  When one of the frog reaches the bug, the frog eats it (grows in size),
 *       and a new bug appears at a random position within the arena walls.
 *
 *  The game has two snakes that constantly move in a random direction until they hits a wall.
 *    In which case the snake changes direction.
 * When a frog touches a snakes, it shrinks back to its original size.
 *
 *  Controls:
 *  - key to start (space)
 *  - keys to turn the two frogs  (w/a/s/d  and i/j/k/l)
 *
 *  Display:
 *   program constantly shows
 *   - the arena, the bug,  the frogs and the snakes
 *
 *  Constants:
 *    This class should contain constants specifying the various parameters of
 *    the game, including the geometry of the arena and obstacle.
 */

public class FrogSnakeGame{
    // Constants for the Geometry of the game.
    // (You may change or add to these if you wish)

    public static final int ArenaSize = 400;
    public static final int LeftWall = 30;
    public static final int RightWall = LeftWall+ArenaSize;
    public static final int TopWall = 50;
    public static final int BotWall = TopWall+ArenaSize;
    public static final int BugSize = 15;

    public static final int DELAY = 20;  // milliseconds to delay each step.

    // Fields to store the two frogs 
    private Frog frog1;
    private Frog frog2;

    // Fields to store the two snakes
    private Snake snake1;
    private Snake snake2;

    // Fields to position of the centre of the bug
    private double xBug;
    private double yBug;
    
    

    public int frog1Score;
    public int frog2Score;

    /** Constructor
     * Set up the GUI,
     * Draw the arena and the bug
     */
    public FrogSnakeGame(){
        UI.initialise();
        UI.setImmediateRepaint(false);
        UI.setKeyListener(this::doKey);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(0);
        UI.printMessage("Move mouse to arena and press Space to start");

        // this.displayScore(this.frog1Score,this.frog2Score);
        this.drawArenaBug();
        UI.repaintGraphics();
    }

    /**
     * Respond to keys.
     * the space key should reset the game to have two new frogs
     * the w/a/s/d keys should make the light frog turn up, to the left, down or to the right
     * the i/j/k/l keys should make the dark frog turn up, to the left, down or to the right
     */
    public void doKey(String key){
        if (key.equals("Space")) {
            restartGame();
        }
        else if (key.equals("w") ){
            this.frog1.turnUp();
        }
        else if (key.equals("a") ){
            this.frog1.turnLeft();
        }
        if (key.equals("s") ){
            this.frog1.turnDown();
        }
        else if (key.equals("d") ){
            this.frog1.turnRight();
        }
        
        /*
        else if (key.equals("i") ){
            this.frog2.turnUp();
        }
        else if (key.equals("j") ){
            this.frog2.turnLeft();
        }
        if (key.equals("k") ){
            this.frog2.turnDown();
        }
        else if (key.equals("l") ){
            this.frog2.turnRight();
        }
        */
    }

    /**
     * Reset the game with two new frogs in the starting positions,
     *   and two snakes at a random position.
     * Loop forever
     *  - move each frog one step,
     *  - move each snake one step,
     *  - if any frog has caught the bug, it grows, and a new bug appears somewhere else.
     *  - if any frog was caught by a snake, it shrinks back to its original position.
     *  - redraw the game (frogs, snakes, arena, and bug)
     */
    private void restartGame(){
        // make sure that the current game ends (if it is running)
        this.frog1 = null;
        this.frog2 = null;
        this.snake1 = null;
        this.snake2 = null;
        this.frog1Score=0;
        this.frog2Score=0;
        UI.sleep(3*DELAY);

        // puts a bug at a random position
        this.newBug();

        // make new frogs
        this.frog1 = new Frog(LeftWall+30, (TopWall+BotWall)/2, "Up", "light");
        this.frog2 = new Frog(RightWall-30, (TopWall+BotWall)/2, "Left", "dark");
       

        // add 2 snakes
        this.snake1 = new Snake ();
        this.snake2 = new Snake ();

        // call redraw
        this.redraw();

        // Run the game
        while (this.frog1!=null && this.frog2!=null) {
            this.frog1.move();
            this.frog2.autoMove(xBug,yBug);   //move to catch bug 
            double frog1Top=frog1.getTop();
            double frog2Top=frog2.getTop();
            double frog1Left=frog1.getLeft();
            double frog2Left=frog2.getLeft();
            this.snake1.catchFrog1Move(frog1Top,frog1Left);
            this.snake2.catchFrog2Move(frog2Top,frog2Left);
           
            
            
            
            int totalScore=frog1.getScore()+frog2.getScore();
            snake1.setSpeed(totalScore);
            snake2.setSpeed(totalScore);
            if (this.frog1.touching(xBug, yBug)) {
                this.frog1.grow();
                this.newBug();
            }
            else if (this.frog2.touching(xBug, yBug)) {
                this.frog2.grow();
                this.newBug();
            }
            if (this.frog1.touching(this.snake1.getX(), this.snake1.getY()) ||
            this.frog1.touching(this.snake2.getX(), this.snake2.getY()) ) {
                this.frog1.shrink();
                frog1.setScore(1);  //if frog is touched by snake,the frog's socre =0;
            }
            if (this.frog2.touching(this.snake1.getX(), this.snake1.getY()) ||
            this.frog2.touching(this.snake2.getX(), this.snake2.getY()) ) {
                this.frog2.shrink();
                 frog2.setScore(1); //if frog is touched by snake,the frog's socre =0;
            }
            this.redraw();
            UI.sleep(DELAY);
        }

    }

    /**
     * Redraws
     * - the arena
     * - the bug
     * - the two frogs
     * - the two snakes
     */
    private void redraw(){
        UI.clearGraphics();
        this.frog1Score=frog1.getScore();
        this.frog2Score=frog2.getScore();
        this.displayScore(this.frog1Score,this.frog2Score);

        this.drawArenaBug();
        this.frog1.draw();
        this.frog2.draw();
        this.snake1.draw();
        this.snake2.draw();

        UI.repaintGraphics();

    }

    /**
     * Draw the arena as a rectangle with a bug in it
     */
    private void drawArenaBug(){
        UI.setColor(Color.black);
        UI.setLineWidth(2);
        UI.drawRect(LeftWall, TopWall, ArenaSize, ArenaSize);
        UI.setLineWidth(1);
        UI.drawImage("bug.png", xBug-BugSize/2, yBug-BugSize/2, BugSize, BugSize);
    }

    /**
     * Defines new position for the bug and draws it
     */
    private void newBug(){
        xBug = LeftWall + BugSize/2 + Math.random()*(ArenaSize-BugSize/2);
        yBug = TopWall + BugSize/2 + Math.random()*(ArenaSize-BugSize/2);
    }

    /**aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
     * 
     *display score
     */
    private void displayScore(int frog1Score,int frog2Score){   
        //Integer.toString(_i_)
        String s1= Integer.toString(frog1Score-1);
        String s2= Integer.toString(frog2Score-1);
        UI.drawString("light frog's score: ",LeftWall, 30);
        UI.drawString("dark frog's score: ",LeftWall+180,30);
        UI.drawString(s1, LeftWall+100, 30);
        UI.drawString(s2, LeftWall+280, 30);

    }

    /**
     * Create a new FrogSnakeGame object (which will set up the interface)
     * and then call the run method on it, which will start the game running
     */
    public static void main(String[] arguments){
        new FrogSnakeGame();
    }   

}
