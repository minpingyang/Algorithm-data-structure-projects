// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 103 Assignment 1
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.util.*;

public class Game2048 {

    Board2048 board; // board representation
    List <String> keys = new ArrayList <String> ();  // set of allowed keys

    private boolean hasMessageDisplayed = false;

    public Game2048 () {
        UI.initialise();
        UI.addButton("Instructions", this::printInstructions);
        UI.addButton("Restart", this::startGame);
        UI.addButton("Quit", UI::quit);

        keys.add("Left");
        keys.add("Right");
        keys.add("Up");
        keys.add("Down");
        UI.setKeyListener(this::doKey);

        UI.setWindowSize(Board2048.getWidth(), Board2048.getHeight());
        UI.setDivider(0);

        startGame();
    }

    /** Respond to key actions */
    public void doKey(String key) {
        if (keys.contains(key))
            move(key);
         
        if(key.equals("a")){
            this.move("Left");
        }
        if(key.equals("d")){
           this.move("Right");
        }
        if(key.equals("w")){
            this.move("Up");
        }
        if(key.equals("s")){
            this.move("Down");
        }
       
    }

    private void printInstructions() {
        UI.setDivider(1);
        UI.clearText();
        UI.println("Instructions");
        UI.println("============\n");
        
        UI.println("Move the tiles with the arrow keys.\n(Put the mouse over the graphics pane first.)\n");
        
        UI.println("Each time two tiles with the same number touch after a move,\n"+
                   "the two tiles erge into one containing the sum of the numbers.");
        
        UI.println("\nProduce the magic number of 2048.");
        UI.println("Press enter when you are ready to play the game.");
        UI.askString("\nOK\n");
        UI.setDivider(0);
    }

    private void startGame() {
        UI.clearGraphics();

        hasMessageDisplayed = false;

        board = new Board2048();
        board.insertRandomTile();
        board.redraw();
    }

    private void move(String direction) {
        if (board == null) {
            UI.printMessage("Board needs to be restarted.");
            return;
        }

        if (direction.equals("Left"))
            board.left();
        else if (direction.equals("Right"))
            board.right();
        else if (direction.equals("Up"))
            board.up();
        else if (direction.equals("Down"))
            board.down();
            
        board.redraw();

        // Only display the "WON" message once
        if (!hasMessageDisplayed && board.hasReachedTarget()) {
            hasMessageDisplayed = true;
            board.displayMessage("Game won!!!");
            UI.printMessage("You can start a new game or carry on with this game.");

            return;
        }

        // Insert a new random tile
        UI.sleep(20);
        UI.println(board);
        board.insertRandomTile();
        board.redraw();

        // Check if the game is over
        if (board.isGameOver()) {
            board.displayMessage("Game OVER!!!");
            board = null;  // flag that board needs to be restarted
        }
    }

    public static void main(String[] arguments){
        new Game2048();
    }   
}
