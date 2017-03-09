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
import java.awt.Color;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/** you should fill the array with values first (for a Line, a Square, or a Spiral),
 * and then draw the appropriate array. We think the demo's functionality is self-evident,
 * but if you have questions please do ask on the forum so that we can share the answers to the class
 *
 */
public class Spiral{

    private int[] lineArray=new int[10];
    private int[] squareArray=new int[100];
    private int[] spiralArray=new int[100];
    public static final int LENGTH=50,LEFT=20,TOP=20;

    public void line(){
        //fill value to array
        for(int i=0;i<10;i++){
            this.lineArray[i]=i+1;
        }
        //draw rectangle with different color in one line
        for(int i=0;i<10;i++){
            UI.setColor(new Color(25*this.lineArray[i]));
            UI.fillRect(LEFT+i*LENGTH,TOP,LENGTH,LENGTH);
            String s=Integer.toString(i+1);
            UI.setColor(Color.white);
            UI.drawString(s,LEFT+(LENGTH/2.6)+i*LENGTH,TOP+(LENGTH/1.7));
        }

    }

    public void square(){
        for(int i=0;i<100;i++){
            this.squareArray[i]=i+1;
        }
        //draw rectangle with different color in one line
        int col=0,count=0;
        for(int i=0;i<100;col++){

            while(count<10){
                UI.setColor(new Color(25*col));
                UI.fillRect(LEFT+count*LENGTH,TOP+(col)*LENGTH,LENGTH,LENGTH);
                String s=Integer.toString(i+1);
                UI.setColor(Color.white);
                UI.drawString(s,LEFT+(LENGTH/2.6)+count*LENGTH,TOP+(LENGTH/1.7)+(col)*LENGTH);
                count++;
                i++;
            }
            count=0;
        }

    }

    public void spiral(){

        for(int i=0;i<100;i++){
            this.spiralArray[i]=i+1;    
        }
        //draw first line (1-)
        int count=0;
        int number=1;
        //int amount=9;
        for( int amount=9;amount>0;amount-=2){
            //1-9
            for(int i=0;i<amount;i++){
                UI.setColor(new Color(2*this.spiralArray[number-1]));
                UI.fillRect(LEFT+i*LENGTH+count*LENGTH,TOP+count*LENGTH,LENGTH,LENGTH);
                String s=Integer.toString(number);
                UI.setColor(Color.white);
                UI.drawString(s,LEFT+(LENGTH/2.6)+i*LENGTH+count*LENGTH,TOP+(LENGTH/1.7)+count*LENGTH);
                number++;
            }
            //10-18
            for(int i=0;i<amount;i++){
                UI.setColor(new Color(2*this.spiralArray[number-1]));
                UI.fillRect(LEFT+amount*LENGTH+count*LENGTH,TOP+i*LENGTH+count*LENGTH,LENGTH,LENGTH);
                String s=Integer.toString(number);
                UI.setColor(Color.white);
                UI.drawString(s,LEFT+(LENGTH/2.6)+amount*LENGTH+count*LENGTH,TOP+(LENGTH/1.7)+i*LENGTH+count*LENGTH);
                number++;
            }
            //19-27
            for(int i=amount;i>0;i--){
                UI.setColor(new Color(2*this.spiralArray[number-1]));
                UI.fillRect(LEFT+i*LENGTH+count*LENGTH,TOP+amount*LENGTH+count*LENGTH,LENGTH,LENGTH);
                String s=Integer.toString(number);
                UI.setColor(Color.white);
                UI.drawString(s,LEFT+(LENGTH/2.6)+i*LENGTH+count*LENGTH,TOP+(LENGTH/1.7)+amount*LENGTH+count*LENGTH);
                number++;
            }
            //29-36
            for(int i=amount;i>0;i--){
                UI.setColor(new Color(2*this.spiralArray[number-1]));
                UI.fillRect(LEFT+0*LENGTH+count*LENGTH,TOP+i*LENGTH+count*LENGTH,LENGTH,LENGTH);
                String s=Integer.toString(number);
                UI.setColor(Color.white);
                UI.drawString(s,LEFT+(LENGTH/2.6)+0*LENGTH+count*LENGTH,TOP+(LENGTH/1.7)+i*LENGTH+count*LENGTH);
                number++;
            }
            count++;
        }

        /**99 88 77 ---11
         * count==2 --> change direction (verticle --->horizental)
         *          -->number--; until number==1;
         * First step: 
         *  draw a verticle 
         */ 
    }
    // Constructors
    /**
     * Construct a new ImageProcessor object
     * and set up the GUI
     */
    public Spiral(){
        UI.initialise();

        UI.addButton("Line",       this::line ); 
        UI.addButton("Square",      this::square );   
        UI.addButton("Spiral",     this::spiral);
        UI.addButton("Clear",       UI::clearGraphics);        
        UI.addButton("Quit", UI::quit );              
        UI.setDivider(0);
        UI.repaintGraphics(); 
    }

    // Main
    public static void main(String[] arguments){
        Spiral ob = new Spiral();
    }   

}
