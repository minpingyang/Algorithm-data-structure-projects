// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 4, 
 * Name:Minping   Yang
 * Usercode:yangminp
 * ID:300364234
 */

import ecs100.*;
import java.awt.Color;
/** PatternsDrawer
Draw various patterns. */

public class PatternsDrawer{

    public static final double boardLeft = 50;   // Top left corner of the board
    public static final double boardTop = 50;
    public static final double boardSize = 300;  // The size of the board on the window

    /** Draw a square grid board with white squares.
     *  Asks the user for the number of squares on each side
     *
     * CORE
     */
    public void drawGridBoard(){
        UI.clearGraphics();
        int num = UI.askInt("How many rows:");
        int i=0;
        while(i<num){
            int j=0;
            while(j<num){
                UI.setColor(Color.black);
                UI.drawRect(boardLeft+(boardSize/num*i), boardTop+(boardSize/num*j),boardSize/num,boardSize/num);
                j++;
            }
            i++;
        }

    }

    /** Illusion
     * a pattern that makes dark circles appear in the intersections
     * when you look at it. 
     **/
    public void drawIllusion(){
        UI.clearGraphics();
        int num = UI.askInt("How many rows:");
        int i=0;
        while(i<num){
            int j=0;
            while(j<num-i){
                UI.setColor(Color.black);
                UI.drawRect(boardLeft+(boardSize/num*i), boardTop+(boardSize/num*j),boardSize/num,boardSize/num);
                j++;
            }
            i++;
        }


    }

    /** Draw a checkered board with alternating black and white squares
     *    Asks the user for the number of squares on each side
     *
     * COMPLETION
     */
    public void drawCheckersBoard(){
       UI.clearGraphics();
        int num = UI.askInt("How many rows:");
        UI.setColor(Color.black);
        UI.fillRect(boardLeft,boardTop,boardSize,boardSize);
         for(double i=boardLeft;i<=boardLeft+boardSize;i+=2*boardSize/num){
            for(double j=boardTop;j<=boardSize+boardTop;j+=2*boardSize/num){
                UI.setColor(Color.white);
                UI.fillRect(i,j,boardSize/num,boardSize/num);
            }
         }
         for(double i=boardLeft+boardSize/num;i<=boardSize+boardLeft;i+=2*boardSize/num){
             for(double j=boardTop+boardSize/num;j<=boardSize+boardTop;j+=2*boardSize/num){
                UI.setColor(Color.white);
                UI.fillRect(i,j,boardSize/num,boardSize/num);
            }
         }
        UI.setColor(Color.black);
        UI.drawRect(boardLeft,boardTop,boardSize,boardSize);
    }

    /** Draw a board made of concentric circles, 2 pixel apart
     *  Asks the user for the number of squares on each side
     */
    public void drawConcentricBoard(){
        UI.clearGraphics();
        int num = UI.askInt("How many rows:");
        double unit=boardSize/num;
        ///int i=0;
        for(int i=0;i<num;i++){
            for(int j=0;j<num;j++){
                for(double x=unit;x>5;x-=4){
                 UI.drawOval(boardLeft+(i*unit)+(unit-x)/2.0,boardTop+(unit*j)+(unit-x)/2.0,x,x);
                }
            }
        }

    }
    
    
    /** ---------- The code below is already written for you ---------- **/

    public PatternsDrawer(){
        UI.initialise();
        UI.addButton("Clear",UI::clearPanes);
        UI.addButton("Core: Grid", this::drawGridBoard);
        UI.addButton("Core: Illusion", this::drawIllusion);
        UI.addButton("Completion: Checkers", this::drawCheckersBoard);
        UI.addButton("Challenge: Concentric", this::drawConcentricBoard);
    
        UI.addButton("Quit",UI::quit);
    }   


}

