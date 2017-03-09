// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 8
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.util.*;
import java.io.*;
import java.awt.Color;

/**
 *  Lets a player play a simple Solitaire dominoes game.
 *  Dominoes are rectangular tiles with two numbers from 0 to 6 on
 *  them (shown with dots).
 *  The player has a "hand" which can contain up to six dominoes.
 *  They can reorder the dominoes in their hand, they can place dominoes
 *  from their hand onto the table, and they can pick up more dominoes from a bag
 *  to fill the gaps in their "hand".
 *  The core and completion do not involve any of the matching and scoring
 *  of real dominoes games. 
 *
 *  PROGRAM DESIGN
 *  The dominoes are represented by objects of the Domino class.
 *  The Domino constructor will construct a new, random domino.
 *  Dominos have a draw(double x, double y) method that will draw the
 *  Domino on the graphics pane at the specified position.
 *  
 *  The program has two key fields:
 *    hand:  an array that can hold 6 Dominos. 
 *    table: an arrayList of the Dominos that have been placed on the table.
 *    
 *  The hand should be displayed near the top of the Graphics pane with a
 *   rectangular border and each domino drawn at its place in the hand.
 *  Empty spaces in the hand should be represented by nulls and displayed as empty.
 *
 *  The user can select a position on the hand using the mouse.
 *  The selected domino (or empty space) should be highlighted with
 *  a border around it.
 *  
 *  The user can use the "Left" or "Right" button to move the selected domino
 *  (or the space) to the left or the right, in which case the domino is
 *  swapped with the contents of the adjacent position in the hand.
 *  If the selected position contains a domino, the user
 *  can use the "Place" button to move the selected domino to the table.
 *  
 *  If there are any empty positions on the hand, the user can use the
 *  "Pickup" button to get a new (random) domino which will be added to
 *  the hand at the leftmost empty position.
 *
 *  The table is represented by an ArrayList of dominos.
 *  At the beginning of the game the table should be empty.
 *  Dominos should be added to the end of the table.
 *  The table should be displayed in rows at the top of the graphics pane.
 */

public class DominoGame{
    public static final int NUM_HAND = 6;    // Number of dominos in hand

    // Fields
    private Domino[] hand;            // the hand (fixed size array of Dominos)
    private ArrayList<Domino> table;  // the table (variable sized list of Dominos)

    private int selectedPos = 0;      //  selected position in the hand.
    private int i,tableNumber;
    private int first,second;
    private int pressedPos;
    private int releasedPos; 
    // (You shouldn't add any more fields for core or completion)
    // constants for the layout
    public static final int HAND_LEFT = 60; // x-position of the leftmost Domino in the hand
    public static final int HAND_TOP = 5;   // y-Position of all the Dominos in the hand 
    public static final int DOMINO_SPACING = 54; 
    //spacing is the distance from left side of Domino to left side of next domino
    public static final int DOMINO_HEIGHT = 100; 

    public static final int TABLE_LEFT = 10;                
    public static final int TABLE_TOP = 120;   

    /**  Constructor:
     * Initialise the hand field to have an array that will hold NUM_HAND Dominos
     * Initialise the table field to have an ArrayList of Dominos,
     * set up the GUI (buttons and mouse)
     *  restart the game
     */
    public DominoGame(){
        this.hand=new Domino[this.NUM_HAND];
        this.table=new ArrayList<Domino>();
        UI.addButton("Pickup", this::pickup);
        UI.addButton("Place", this::placeDomino);
        UI.addButton("Flip", this::flipDomino);
        UI.addButton("Left", this::moveLeft);
        UI.addButton("Right", this::moveRight);
        UI.addButton("Suggest", this::suggestDomino);
        UI.addButton("Restart", this::restart);
        UI.addButton("Quit", UI::quit);
        UI.setMouseMotionListener(this::doMouse); 
        this.restart();
        this.redraw();
    }

    /**
     * Restart the game:
     *  set the table to be empty,
     *  set the hand to have no dominos
     */
    public void restart(){
        for(int i=0;i<this.hand.length;i++){
            this.hand[i]=null;
        }

        this.table.clear();

        this.redraw();
    }

    /**
     * If there is at least one empty position on the hand, then
     * create a new random domino and put it into the first empty position on the hand.
     * (needs to search along the array for an empty position.)
     */
    public void pickup(){

        this.i=0; 
        while(this.hand[this.i]!=null&&this.i<5){
            this.i++;
        }
        if(this.hand[this.i]==null){
            this.hand[this.i]=new Domino();

            this.redraw();
        }

    }

    /**
     * Draws the outline of the hand,
     * draws all the Dominos in the hand,
     * highlights the selected position in some way
     */
    public void drawHand(){
        UI.setColor(Color.black);
        UI.setLineWidth(5);
        UI.drawRect(this.HAND_LEFT,0,this.DOMINO_SPACING*6,this.DOMINO_HEIGHT);

        for(int i=0;i<this.hand.length;i++){
            if(this.hand[i]!=null){
                this.hand[i].draw(this.HAND_LEFT+DOMINO_SPACING*i,0);
            }

        }

        UI.setColor(Color.green.darker());
        UI.setLineWidth(3);
        UI.drawRect(this.HAND_LEFT+(this.DOMINO_SPACING*this.selectedPos),this.HAND_TOP-4, Domino.WIDTH, this.DOMINO_HEIGHT);

    }

    /**
     * Move domino from selected position on hand (if there is domino there) to the table
     * The selectedPos field contains the index of the selected domino.
     */
    public void placeDomino(){
        //this.count++;
        if(this.hand[this.selectedPos]!=null){
            Domino domi=this.hand[this.selectedPos];
            //top or bottom match previous either one of them

            if(this.table.size()>0&&
            (this.hand[this.selectedPos].first==this.table.get(this.table.size()-1).first||
                this.hand[this.selectedPos].secnd==this.table.get(this.table.size()-1).secnd)
            ){

                this.hand[this.selectedPos]=null;
                this.table.add(domi);

            }
            else if(this.table.size()==0){
                this.hand[this.selectedPos]=null;
                this.table.add(domi);

            }

            if(this.selectedPos<5){

                this.selectedPos++;
            }
            this.redraw();
        }

    }

    /**
     * Draws the list of Dominos on the table, 10 to a row
     * Note, has to wrap around to a new row when it gets to the
     * edge of the table
     */
    public void drawTable(){
        this.tableNumber=0;
        int left=this.TABLE_LEFT;
        int top=this.TABLE_TOP;

        for(Domino d : this.table){
            d.draw(left,top);
            left+=this.DOMINO_SPACING;
            this.tableNumber++;
            if(this.tableNumber==10){
                this.tableNumber=0;
                left=this.TABLE_LEFT;
                top=top+this.DOMINO_HEIGHT+2;
            }
        }
    }

    /**
     * If there is a domino at the selected position in the hand, 
     * flip it over.
     */
    public void flipDomino(){
        this.hand[this.selectedPos].flip();

        int first= this.hand[this.selectedPos].getTop();
        int second=this.hand[this.selectedPos].getBottom();
        this.hand[this.selectedPos].first= first;
        this.hand[this.selectedPos].secnd= second;
        this.hand[this.selectedPos].flip();
        this.redraw();

    }

    /**
     * Swap the contents of the selected position on hand with the
     * position on its left (if there is such a position)
     * and also decrement the selected position to follow the domino 
     */
    public void moveLeft(){
        if(this.selectedPos>0){
            Domino temp=this.hand[this.selectedPos];
            this.hand[this.selectedPos]=this.hand[this.selectedPos-1];
            this.hand[this.selectedPos-1]=temp;
            if(this.selectedPos>0){

                this.selectedPos--;
            }
            this.redraw();
        }
    }

    /**
     * Swap the contents of the selected position on hand with the
     *  position on its right (if there is such a position)
     *  and also increment the selected position to follow the domino 
     */
    public void moveRight(){
        if(this.selectedPos<this.hand.length-1){
            Domino temp=this.hand[this.selectedPos];
            this.hand[this.selectedPos]=this.hand[this.selectedPos+1];
            this.hand[this.selectedPos+1]=temp;
            if(this.selectedPos<5){

                this.selectedPos++;
            }
            this.redraw();
        }
    }

    /**
     * If the table is empty, only a double can be suggested.
     * If the table is not empty, see if one domino has a number that matches one of the 
     *    numbers of the last domino.
     */
    public void suggestDomino(){

        int tablePos=this.table.size();//which position
        int suggestHand=0;

        for(int i=0;i<this.hand.length;i++){
            if(this.hand[i]!=null&&this.table.size()>0){
                this.first =this.hand[i].getTop();
                this.second=this.hand[i].getBottom();
                if(this.first==this.table.get(this.table.size()-1).getTop()||this.second==this.table.get(this.table.size()-1).getBottom()){
                    suggestHand=i;
                    break;
                }

            }else if(this.hand[i]!=null&&this.table.size()==0){
                this.first =this.hand[i].getTop();
                this.second=this.hand[i].getBottom();
                if(this.first==this.second){
                    suggestHand=i;
                    break;
                }
            }

        }
        if(this.table.size()>0&&(this.first==this.table.get(this.table.size()-1).first||this.second==this.table.get(this.table.size()-1).secnd)){
            UI.printMessage("Suggesting "+this.first+"-"+this.second+"at index"+suggestHand+" to be placed at in position "+tablePos);
        }else if(this.table.size()==0&&this.first==this.second){
            UI.printMessage("Suggesting "+this.first+"-"+this.second+"at index"+suggestHand+" to be placed at in position "+tablePos);
        }
        else{
            UI.printMessage("No suggestion");
        }
    }
    /** ---------- The code below is already written for you ---------- **/
    /** Allows the user to select a position in the hand using the mouse.
     * If the mouse is released over the hand, then sets  selectedPos
     * to be the index into the hand array.
     * Redraws the hand and table */
    public void doMouse(String action, double x, double y){
        if (action.equals("pressed")){
            if (y >= HAND_TOP && y <= HAND_TOP+DOMINO_HEIGHT && 
            x >= HAND_LEFT && x <= HAND_LEFT + NUM_HAND*DOMINO_SPACING) {
                this.pressedPos=(int)((x-HAND_LEFT)/DOMINO_SPACING);
                this.selectedPos = (int) ((x-HAND_LEFT)/DOMINO_SPACING);
                UI.clearText();UI.println("selected "+this.selectedPos);
                this.redraw();
            }
        }
        else if(action.equals("released")){
            if(y >= HAND_TOP && y <= HAND_TOP+DOMINO_HEIGHT && 
            x >= HAND_LEFT && x <= HAND_LEFT + NUM_HAND*DOMINO_SPACING){
                this.releasedPos=(int)((x-HAND_LEFT)/DOMINO_SPACING);
                //when drag the domino move to left;
                if(this.releasedPos>this.pressedPos){
                    for(int i=0; i<(this.releasedPos-this.pressedPos);i++){
                        Domino temp=hand[this.selectedPos];
                        hand[this.selectedPos]=hand[this.selectedPos+1];
                        hand[this.selectedPos+1]=temp;
                        this.selectedPos++;
                        this.redraw();
                    }
                }
                //when drage the domino move to right;
                else if(this.releasedPos<this.pressedPos){
                    for(int i=0;i<(pressedPos-releasedPos);i++){
                        Domino temp=hand[this.selectedPos];
                        hand[this.selectedPos]=hand[selectedPos-1];
                        hand[this.selectedPos-1]=temp;
                        selectedPos++;
                        this.redraw();
                    }
                }
                this.redraw();
            }         
        }

    }

    /**
     *  Redraw the table and the hand.
     *  To work with the code above, this needs to use the constants:
     *   - DOMINO_SPACING, HAND_HEIGHT, HAND_LEFT, HAND_TOP, TABLE_LEFT, TABLE_TOP
     *   See the descriptions where these fields are defined.
     *  Needs to clear the graphics pane,
     *  then draw the hand with all its dominos, 
     *  then outline the selected position on the hand
     *  then draw the rows of dominos on the table.
     */
    public void redraw(){
        UI.clearGraphics();
        this.drawHand();
        this.drawTable();
    }

    public static void main(String[] args){
        DominoGame obj = new DominoGame();
    }   

}
