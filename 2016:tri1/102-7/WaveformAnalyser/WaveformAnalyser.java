// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 7 
 * Name: 
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.io.*;
import java.util.*;
import java.awt.Color;
import java.lang.*;

/**
 * This is related to your program from assignment 4 which analysed polution levels
 * However, instead of reading data from the user, it reads the data from
 * a file into an ArrayList, which means that it can analyse the numbers
 * more easily and in more different ways.
 * It can also cope with much larger sets of numbers.
 * The numbers are guaranteed to be integers but the values can be
 *   negative and the signal swings above and below zero.
 *
 * There are 11 methods you are to complete, all focusing on the ArrayList of data.
 * CORE
 *  doRead:              reads numbers into an ArrayList.
 *  doDisplay:           displays the waveform.
 *  doReportDistortion:  prints out the fraction of time the signal is distorted.
 * COMPLETION
 *  doSpread:            displays the spread with two horizontal lines and returns its value.
 *  doDisplayDistortion: shows in red the distorted part of the signal.
 *  doHighlightPeaks:    plots the peaks with small green circles.
 * CHALLENGE
 *  doNormalise:         normalises all the values down so there is no distortion.
 *  upperEnvelope:       displays the upper envelope.
 *  lowerEnvelope:       displays the lower envelope.
 *  doSave:              saves the current waveform values into a file.
 *  select and edit:     let the user select a regions of the waveform with the mouse
 *                       to remove them.  Add a save button to save the edited values.
 */

public class WaveformAnalyser{
    // Fields 
    private ArrayList<Double> waveform;   // the field to hold the ArrayList of values

    // Constant: the threshold for the distortionLevel and showDistortion methods
    public static final double THRESHOLD = 200;

    // Constants: the dimensions of the graph for the displayWaveform method
    public static final int GRAPH_LEFT = 10;
    public static final int ZERO_LINE = 260;

    // Constant fields holding the size of the circles for the highlightPeaks method
    public static final int SIZE_CIRCLE = 10;

    private double startX,startY;

    /**
     * [CORE]
     * Clears the panes, 
     * Creates an ArrayList stored in a field, then
     * Asks user for a waveform file (eg waveform1.txt) 
     * Reads data from the file into the ArrayList
     */
    public void doRead(){
        UI.clearPanes();

        try{
            this.waveform=new ArrayList<Double>();
            String fname = UIFileChooser.open("Choose a waveform file");
            Scanner sc=new Scanner(new File(fname));
            while(sc.hasNext()){
                double number=sc.nextDouble();
                this.waveform.add(number);
            }
            UI.println("Read "+ this.waveform.size()+" data points from " + fname);
            sc.close();
        }

        catch(IOException e) {UI.printf("File Failure %s \n", e);}

    }

    /**
     * [CORE]
     * Displays the waveform as a line graph,
     * The n'th value in waveform is displayed at
     *    x-position is GRAPH_LEFT + n
     *    y-position is ZERO_LINE - the value
     * Plots a line graph of all the points with a blue line between
     *  each pair of adjacent points
     * Draw the horizontal line representing the value zero.
     * Uses GRAPH_LEFT and ZERO_LINE for the dimensions and positions of the graph.
     * Don't worry if the lines go off the window
     */
    public void doDisplay(){
        if (this.waveform == null){ //there is no data to display
            UI.println("No waveform to display");
            return;
        }
        UI.clearGraphics();

        // draw x axis (showing where the value 0 will be)
        UI.setColor(Color.black);
        UI.drawLine(GRAPH_LEFT, ZERO_LINE, GRAPH_LEFT + this.waveform.size() , ZERO_LINE); 

        // plot points: blue line between each pair of values
        UI.setColor(Color.blue);
        //UI.setLineWidth(1);     //WARNNING  :SEE THE DEMO
        for(int n=1;n<waveform.size();n++){
            UI.drawLine(GRAPH_LEFT+n,ZERO_LINE-waveform.get(n-1),GRAPH_LEFT+n,ZERO_LINE-waveform.get(n));
        }

    }

    /**
     * [CORE]
     * Computes and prints out the fraction of time the signal is distorted. 
     * This fraction of time is defined as the number of distorted values, divided by the number of values. 
     * A distorted value is defined as one whose absolute
     * value is greater than the value of THRESHOLD 
     * [Hint] You may find Math.abs() useful for this method - it computes the absolute value
     */
    public void doReportDistortion() {
        if (this.waveform == null){ //there is no data to analyse
            UI.println("No signal to analyse and report on");
            return;
        }
        double fraction = 0;
        double numberDistor=0; //the number of distorted values
        for(int i=0;i<this.waveform.size();i++){

            if(this.THRESHOLD<Math.abs(waveform.get(i))){

                numberDistor++;
            }
        }
        //the number of distorted values/the total number
        fraction=numberDistor/this.waveform.size();

        UI.printf("Fraction of time the signal is distorted %4.3f\n", fraction);
    }

    /**
     * [COMPLETION]
     * The spread is the difference between the maximum and minimum values of the waveform.
     * Finds the maximum and minimum values, then
     * Displays the spread by drawing two horizontal lines on top of the waveform: 
     *   one green line for the maximum value, and
     *   one red line for the minimum value.
     */
    public void doSpread() {
        if (this.waveform == null){ //there is no data to display
            UI.println("No waveform to display");
            return;
        }
        this.doDisplay();
        double max=Integer.MIN_VALUE;
        double min=Integer.MAX_VALUE;
        for(int i=0;i<this.waveform.size();i++){

            if(waveform.get(i)>max){
                max=waveform.get(i);
            }
        }
        UI.setColor(Color.green);
        UI.drawLine(GRAPH_LEFT,ZERO_LINE-max,GRAPH_LEFT+this.waveform.size(),ZERO_LINE-max);

        for(int i=0;i<this.waveform.size();i++){

            if(waveform.get(i)<min){
                min=waveform.get(i);
            }
        }
        UI.setColor(Color.red);
        UI.drawLine(GRAPH_LEFT,ZERO_LINE-min,GRAPH_LEFT+this.waveform.size(),ZERO_LINE-min);
    }

    /**
     * [COMPLETION]  [Fancy version of doDisplay]
     * Display the waveform as a line graph. 
     * Draw a line between each pair of adjacent points
     *   * If neither of the points is distorted, the line is BLUE
     *   * If either of the two end points is distorted, the line is RED
     * Draw the horizontal lines representing the value zero and thresholds values.
     * Uses THRESHOLD to determine distorted values.
     * Uses GRAPH_LEFT and ZERO_LINE for the dimensions and positions of the graph.
     * [Hint] You may find Math.abs(int a) useful for this method.
     * You may assume that all the values are between -250 and +250.
     */
    public void doDisplayDistortion() {
        if (this.waveform == null){ //there is no data to display
            UI.println("No waveform to display");
            return;
        }
        UI.clearGraphics();

        // draw zero axis
        UI.setColor(Color.black);
        UI.drawLine(GRAPH_LEFT, ZERO_LINE, GRAPH_LEFT + this.waveform.size() , ZERO_LINE); 

        // draw thresholds

        // UI.setColor(Color.red);
        for(int n=1;n<this.waveform.size();n++){

            if(this.THRESHOLD>=Math.abs(waveform.get(n-1))&&this.THRESHOLD>=Math.abs(waveform.get(n))){
                UI.setColor(Color.blue);
                UI.drawLine(GRAPH_LEFT+n,ZERO_LINE-waveform.get(n-1),GRAPH_LEFT+n,ZERO_LINE-waveform.get(n));               
            }else{
                UI.setColor(Color.red);
                UI.drawLine(GRAPH_LEFT+n,ZERO_LINE-waveform.get(n-1),GRAPH_LEFT+n,ZERO_LINE-waveform.get(n));
            }
        }

    }

    /**
     * [COMPLETION]
     * Plots the peaks with small green circles. 
     *    A peak is defined as a value that is greater or equals to both its
     *    neighbouring values.
     * Note the size of the circle is in the constant SIZE_CIRCLE
     * You may assume that peaks values differ from their neighbouring points.
     */
    public void doHighlightPeaks() {
        this.doDisplayDistortion(); //use doDisplay if doDisplayDistortion isn't comple/yangminp/Downloads/Com102/7/WaveformAnalysete
        //value>= neighbouring values both  
        //draw cicle  //SIZE_CIRCLE
        for(int i=0;i<this.waveform.size()-2;i++){
            double first=this.waveform.get(i);
            double second=this.waveform.get(i+1);
            double third=this.waveform.get(i+2);
            if(second>=first&&second>=third){
                UI.setColor(Color.green);
                UI.drawOval(GRAPH_LEFT+i+2-this.SIZE_CIRCLE/2.0,ZERO_LINE-waveform.get(i+1)-this.SIZE_CIRCLE/2.0,this.SIZE_CIRCLE,this.SIZE_CIRCLE);
            }
        }

    }

    /**
     * [CHALLENGE]
     * Finds the largest value (positive or negative) in the waveform, and
     * normalises all the values down so that the largest value is now equal to
     * the distortion threshold.
     * Then redraws the waveform.
     */
    public void doNormalise() {
        double max=Integer.MIN_VALUE;
        double min=Integer.MAX_VALUE;
        ArrayList<Double> newForm=new ArrayList<Double>();
        for(int i=0;i<this.waveform.size();i++){

            if(waveform.get(i)>max){
                max=waveform.get(i);
            }
        }

        for(int i=0;i<this.waveform.size();i++){

            if(waveform.get(i)<min){
                min=waveform.get(i);
            }
        }

        double maxAbs=Math.max(max,Math.abs(min));
        double ratio=this.THRESHOLD/maxAbs;
        for(int i=0;i<this.waveform.size();i++){
            double newNumber=ratio*this.waveform.get(i);   
            newForm.add(newNumber);
        }
        this.waveform.clear();
        for(int i=0;i<newForm.size();i++){
            double newNumber=newForm.get(i);

            this.waveform.add(newNumber);
        }

        UI.clearPanes();
        //this.doDisplayDistortion(); //use doDisplay if doDisplayDistortion isn't complete
        this.doSpread();
    }

    public void doEnvelope(){
        if (this.waveform == null){ //there is no data to display
            UI.println("No waveform to display");
            return;
        }
        this.doDisplay();  // display the waveform
        this.upperEnvelope();
        this.lowerEnvelope();
    }

    /**
     * [CHALLENGE]
     * Displays the upper envelope with GREEN lines connecting all the peaks.
     *    A peak is defined as a point that is greater or equal to *both* neighbouring points.
     * DO NOT clear the graphics as we also want to view the waveform.
     */
    public void upperEnvelope() {
        ArrayList<Double> peakpoint=new ArrayList<Double>();
        for(int i=0;i<this.waveform.size()-2;i++){
            double first=this.waveform.get(i);
            double second=this.waveform.get(i+1);
            double third=this.waveform.get(i+2);
            if(second>=first&&second>=third){

                double x1=GRAPH_LEFT+i+2;
                double y1=ZERO_LINE-waveform.get(i+1);

                peakpoint.add(x1);
                peakpoint.add(y1);

            }
        }
        for(int j=0;j<peakpoint.size()-3;j+=2){

            double x1=peakpoint.get(j);
            double y1=peakpoint.get(j+1);
            double x2=peakpoint.get(j+2);
            double y2=peakpoint.get(j+3);
            UI.setColor(Color.green);
            UI.drawLine(x1,y1,x2,y2);

        }

    }

    /**
     * [CHALLENGE]
     * Displays the lower envelope with RED lines connecting all the "negative" peaks.
     *    A "negative" peak is defined as a point that is smaller or equal to *both* neighbouring points.
     * DO NOT clear the graphics as we also want to view the waveform.
     */
    public void lowerEnvelope() {
        ArrayList<Double> negativePeak=new ArrayList<Double>();
        for(int i=0;i<this.waveform.size()-2;i++){
            double first=this.waveform.get(i);
            double second=this.waveform.get(i+1);
            double third=this.waveform.get(i+2);
            if(second<=first&&second<=third){

                double x1=GRAPH_LEFT+i+2;
                double y1=ZERO_LINE-waveform.get(i+1);

                negativePeak.add(x1);
                negativePeak.add(y1);

            }
        }
        for(int j=0;j<negativePeak.size()-3;j+=2){

            double x1=negativePeak.get(j);
            double y1=negativePeak.get(j+1);
            double x2=negativePeak.get(j+2);
            double y2=negativePeak.get(j+3);

            UI.setColor(Color.red);
            UI.drawLine(x1,y1,x2,y2);

        }
    }

    /**
     * [CHALLENGE]
     * Saves the current waveform values into a file
     */
    public void doSave(){
        try{
            PrintStream ps=new PrintStream(new File(UIFileChooser.save()));

            for(int i=0;i<this.waveform.size();i++){
                double number=this.waveform.get(i);
                ps.println(number);
            }
            ps.close();
        }
        catch(IOException e){
            UI.printf("File Failure % \n", e);
        }

    }

    private int index1;
    /**
     * [CHALLENGE]
     * Lets user select a region of the waveform with the mouse
     * and deletes that section of the waveform.
     */
    public void doMouse(String action, double x, double y){
        ArrayList<Double> newForm1=new ArrayList<Double>();
        ArrayList<Double> newForm2=new ArrayList<Double>();
        int count1=0,count2=0;
        if(action.equals("pressed")){
            this.startX=x;
            this.startY=y;

        }else if(action.equals("released")){
            if(x<this.startX){
                double position1=x;
                double position2=this.startX;
                int index1=(int)(position1-this.GRAPH_LEFT-1);
                int index2=(int)(position2-this.GRAPH_LEFT-1);
                while(count1<index1){

                    double number=this.waveform.get(count1);
                    newForm1.add(number);
                    count1++;
                }

                for(int i=index2,j=0;i<this.waveform.size();i++,j++){
                    double number=this.waveform.get(i);
                    newForm1.add(count1+j,number);                    
                }
                this.waveform.clear();
                for(int i=0;i<newForm1.size();i++){
                    double newNumber=newForm1.get(i);

                    this.waveform.add(newNumber);
                }

                UI.clearPanes();
                this.doDisplayDistortion();
            }else if (x>this.startX){
                double position1=this.startX;
                double position2=x;
                int index1=(int)(position1-this.GRAPH_LEFT-1);
                int index2=(int)(position2-this.GRAPH_LEFT-1);

                while(count2<index1){

                    double number=this.waveform.get(count2);
                    newForm2.add(number);
                    count2++;
                }
                for(int i=index2,j=0;i<this.waveform.size();i++,j++){
                    double number=this.waveform.get(i);
                    newForm2.add(count2+j,number);                    
                }
                 this.waveform.clear();
                for(int i=0;i<newForm2.size();i++){
                    double newNumber=newForm2.get(i);

                    this.waveform.add(newNumber);
                }

                UI.clearPanes();
                this.doDisplayDistortion();
            }else{
                double position=x;
                int index=(int)(position-this.GRAPH_LEFT-1);
                this.waveform.remove(index);
                this.doDisplayDistortion();
            }
        }
        //this.waveform.remove(i);
    }

    /** ---------- The code below is already written for you ---------- **/
    /** Constructor:
     * Set up the ten buttons and mouselistener
     */
    public WaveformAnalyser(){
        //core
        UI.addButton("Read Data", this::doRead);
        UI.addButton("Display Waveform", this::doDisplay);
        UI.addButton("Report Distortion", this::doReportDistortion);
        //completion
        UI.addButton("Spread", this::doSpread);
        UI.addButton("Display Distortion", this::doDisplayDistortion);
        UI.addButton("Peaks", this::doHighlightPeaks);
        //challenge
        UI.addButton("Normalise", this::doNormalise);
        UI.addButton("Envelope", this::doEnvelope);
        UI.addButton("Save", this::doSave);
        UI.addButton("Quit", UI::quit);
        UI.setMouseListener(this::doMouse);   // only for challenge

    }

    // Main
    public static void main(String[] arguments){
        new WaveformAnalyser();
    }   

}
