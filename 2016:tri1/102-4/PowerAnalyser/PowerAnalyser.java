// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 102 Assignment 4 
 * Name: Minping Yang
 * Usercode:yangminp
 * ID:300364234
 */

import ecs100.*;
import java.awt.Color;

/** The program contains two methods for analysing the readings of the level of power usage in a house over the course of a day.
 *  There are several things about the power usage level that a user may be interested in: 
 *    The maximum and the minimum power usage level at any point during the day.
 *    The average power usage level during the day.
 *
 *  The program has two methods.  They both read a sequence of levels from
 *  the user (through the terminal window).
 *  One prints a report about the levels; the other plots a graph.
 */
public class PowerAnalyser{

    /**
     * analyseLevels reads a sequence of levels from the user  and prints out
     *    maximum, minimum, and average level.
     *    
     * The sequence is terminated by any word (non-number) such as "done" or "end".
     * All the levels are integers between 0 and 8000. 
     * The method will need variables to keep track of various quantities, all of which 
     * need to be initialised to an appropriate value.
     * It will need a loop to keep reading the levels until there isn't an integer next.
     * [Core]
     *   - There is guaranteed to be at least one level,
     *   - Print the maximum, minimum, and average level
     *   - Hint, keep track of the sum of the levels using a variable of type double
     */
    public void analyseLevels() {
        UI.clearText();

        // Initialise variables
        double sum=0;
        int max=Integer.MIN_VALUE,count=0,min=Integer.MAX_VALUE;
        // Prompt for input
        UI.println("enter a sequence of numbers, ending with the word 'done'");
        UI.print(" all the levels are integers between 0 and 8000");
        // Loop, reading numbers and updating variables
        while(UI.hasNextInt()){
            int number=UI.nextInt();
            if(max<number){
                max=number;
            } 
            if(min>number){
                min=number;
            }
            sum+=number;
            count++;
        }

        // Compute and print out the analysis
        double average=(sum/count);
        UI.println("----------Analysis-------");
        UI.printf("Average level: %.2f Kwh\n",average);
        UI.printf("Max level:%d Kwh\n",max);
        UI.printf("Min level :%d Kwh\n",min);
        /*# YOUR CODE HERE */

        UI.nextLine(); // to clear out the input
    }

    /**
     * Reads a sequence of levels (integers) from the user (using Scanner
     * and the terminal window) and plots a bar graph of them, using narrow 
     * rectangles whose heights are equal to the level.
     * The sequence is terminated by any word (non-number) such as "done" or "end".
     * The method may assume that there are at most 24 numbers.
     * The method will need a loop to keep reading the levels until there isn't a number next.
     *  Each time round the loop, it needs to read the next level and work out where
     *  to draw the rectangle for the bar. 
     * [Completion]
     *   - The method should work even if there were no readings for the day.
     *   - Any level greater than 8000 should be plotted as if it were just 8000, putting an
     *         asterisk ("*") above it to show that it has been cut off.
     *   - Draws a horizontal line for the x-axis (or baseline) without any labels.
     * [Challenge:] 
     *   - The graph should also have labels on the axes, roughly every 50 pixels.
     *   - Make the method ask for a maximum level first, then scale the y-axis and values 
     *     so that the largest numbers just fit on the graph.
     *   - The numbers on the y axis should reflect the scaling.
     */
    public void plotLevels() {
        UI.clearText();
        UI.clearGraphics();
        //  Initialise variables and prompt for input
        double top=50,left=50;

        int count=1;

        UI.println("enter a sequence of numbers, ending with the word 'done'");
        UI.print("and there are at most 24 numbers.");

        // Loop, reading numbers and drawing bars

        UI.drawLine(left+10,top+450,left+2400,top+450);  //x axis

        while(UI.hasNextInt()&&count<=24){
            double number=UI.nextInt();

            if(number<=8000){
                UI.setColor(Color.black);
                UI.fillRect(left+10+(count-1)*20,top+450-(number/20),10, number/20);
            }
            else {
                UI.setColor(Color.black);
                UI.fillRect(left+10+(count-1)*20,top+450-(8000/20),10, 8000/20);
                UI.drawString("*",left+12+(count-1)*20,top+47);
            }

            count++; 

        }

        UI.nextLine(); // to clear out the input
        UI.println("Done");
    }

    public void plotLevelsChallenge(){
        UI.clearText();
        UI.clearGraphics();
        //  Initialise variables and prompt for input
        double top=50,left=50;
        
       
        int count1=1;
        
       
        

        // Loop, reading numbers and drawing bars

        UI.drawLine(left,top,left,top+500); //y
        UI.drawLine(0,top+450,left+2400,top+450);  //x axis

        for(int i=1;i<25;i++){
            UI.drawLine(left+50+(i-1)*50,top+447,left+50+(i-1)*50,top+450);

        }

        for(int i=1;i<25;i++){
            UI.drawString(String.valueOf(i),left+48+(i-1)*50,top+460);
        }

        for(int j=1;j<10;j++){
            UI.drawLine(left,top+(450-j*50),left+3,top+(450-j*50));

        }

       
        
        int max= UI.askInt("Enter max level:");
        
        for(int j=1;j<10;j++){

            UI.drawString(String.valueOf((max*j)/9),left-30,top+(450-j*50));

        } 
        UI.println("enter a sequence of numbers, ending with the word 'done'");   
        UI.print("and there are at most 24 numbers.");
        while(UI.hasNextInt()&&count1<25){
            int number1=UI.nextInt();
            UI.println(number1/(max/400));

            UI.setColor(Color.black);
            UI.fillRect(left+50+(count1-1)*50,top+450-number1/(max/450.0),20,number1/(max/450.0) );

            count1++;
        }

        UI.nextLine(); // to clear out the input

        UI.println("Done");
    }

    
    /** ---------- The code below is already written for you ---------- **/
    /** Constructor: set up user interface */
    public PowerAnalyser(){
        UI.initialise();
        UI.addButton("Clear", UI::clearPanes );
        UI.addButton("Analyse Levels", this::analyseLevels );
        UI.addButton("Plot Levels Completion", this::plotLevels );
        UI.addButton("Plot Levels Challenge", this::plotLevelsChallenge );
        UI.addButton("Quit", UI::quit );
    }

       
       
     
          
}




