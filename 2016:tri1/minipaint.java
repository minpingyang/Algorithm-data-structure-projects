

import ecs100.*;
import java.awt.Color;
import javax.swing.JColorChooser;
import javax.swing.*;

public class MiniPaint{

    // fields to remember:
    //  - the shape that will be drawn when the mouse is next released.
    //  - whether the shape should be filled or not
    //  - the position the mouse was pressed, 
    //  - the name of the image file
    private double startX,startY;
    private double dragX,dragY;
    private Color currentColor=Color.black;
    private boolean fill;
    private String currentShape="Line";
    private double currentSize;

    private  JButton colorBt;
    private  JButton fillBt;
    public boolean ifFill(){
        fill=!fill;
        if(fill){
            this.fillBt.setBackground(Color.black);
        }else{
            this.fillBt.setBackground(Color.white);
        }

        return fill;
    }

    //Constructor

    /**
     * Respond to mouse events
     * When pressed, remember the position.
     * When released, draw the current shape using the pressed position
     *  and the released position.
     * Uses the value in the field to determine which kind of shape to draw.
     * Although you could do all the drawing in this method,
     *  it may be better to call some helper methods for the more
     *  complex actions (and then define the helper methods!)
     */
    public void doMouse(String action, double x, double y) {
        if(action.equals("pressed")){
            this.startX=x;
            this.startY=y;
            this.dragX=this.startX;
            this.dragY=this.startY;
        }
        else if(action.equals("released")){
            if(currentShape.equals("Rectangle")){
                this.drawARectangle(this.startX,this.startY,x,y);
            }else if(currentShape.equals("Oval")){
                this.drawAnOval(this.startX,this.startY,x,y);
            }else if(currentShape.equals("Image")){
                this.drawAnImage(x,y);
            }
            else if(currentShape.equals("Ring")){
                this.drawRing(this.startX,this.startY,x,y);
            }

        }else if(action.equals("dragged")){

            if(currentShape.equals("Pen")){
                this.pen(this.dragX,this.dragY,x,y);
            }
            else if(currentShape.equals("Erase")){
                this.erase(this.dragX,this.dragY);
            }else if(currentShape.equals("Line")){
                UI.setColor(this.currentColor);
                UI.invertLine(this.startX,this.startY,this.dragX,this.dragY);
                this.dragX=x;
                this.dragY=y;
                UI.invertLine(this.startX,this.startY,this.dragX,this.dragY);
            }
            this.dragX=x;
            this.dragY=y;

        }

    }

    public void drawRing(double x1,double y1,double x2,double y2){
        UI.setColor(this.currentColor);

        double diameter=Math.abs(x2-x1);
        for(int i=0;i<16;i++){
            UI.drawOval((x1+x2)/2.0+i*(0.5*diameter/15),y1+i*(0.5*diameter/15),diameter-i*(diameter/15),diameter-i*(diameter/15));
        }
    }

    /*
    public void drawLine(double x1,double y1,double x2,double y2){

    UI.setColor(this.currentColor);
    UI.drawLine(x1,y1,x2,y2);
    }
     */
    public void doChooseColor(){
        this.currentColor = JColorChooser.showDialog(null, "Choose Color", this.currentColor);
        this.colorBt.setBackground(this.currentColor);

    }

    public void setSize(double value){
        this.currentSize=value;
    }

    /* Helper methods for drawing the shapes, if you choose to define them 
    I used the following methods:
     */
    // draws a Rectangle between the mouse pressed and mouse released points.
    public void drawARectangle(double x1, double y1,double x2,double y2){

        if(!fill){
            if(x2>x1&&y2>y1){
                UI.setColor(this.currentColor);
                UI.drawRect(x1,y1,Math.abs(x2-x1), Math.abs(y2-y1));
            }else{
                UI.setColor(this.currentColor);
                UI.drawRect(x2,y2,Math.abs(x2-x1), Math.abs(y2-y1));
            }

        }
        else{
            if(x2>x1&&y2>y1){
                UI.setColor(this.currentColor);
                UI.fillRect(x1,y1,Math.abs(x2-x1), Math.abs(y2-y1));
            }else{
                UI.setColor(this.currentColor);
                UI.fillRect(x2,y2,Math.abs(x2-x1), Math.abs(y2-y1));
            }
        }

    }

    // draws a Rectangle between the mouse pressed and mouse released points.
    public void drawAnOval(double x1, double y1,double x2,double y2){

        if(!fill){
            if(x2>x1&&y2>y1){ 
                UI.setColor(this.currentColor);
                UI.drawOval((x1+x2)/2.0,y1,Math.abs(x2-x1), Math.abs(y2-y1));
            }else{
                UI.setColor(this.currentColor);
                UI.drawOval((x1+x2)/2.0,y2,Math.abs(x2-x1), Math.abs(y2-y1));
            }
        }
        else{
            if(x2>x1&&y2>y1){ 
                UI.setColor(this.currentColor);
                UI.fillOval((x1+x2)/2.0,y1,Math.abs(x2-x1), Math.abs(y2-y1));
            }else{
                UI.setColor(this.currentColor);
                UI.fillOval((x1+x2)/2.0,y2,Math.abs(x2-x1), Math.abs(y2-y1));
            }
        }
    }

    public void erase(double x1,double y1){
        UI.eraseRect(x1,y1,currentSize, currentSize);
    }

    public void pen(double x1,double y1,double x2,double y2){
        UI.setColor(this.currentColor);
        UI.drawLine(x1,y1,x2,y2);

    }

    // draws an image at the mouse released point.
    public void drawAnImage(double x, double y){
        String fname=UIFileChooser.open("Choose a ppm image file"); 
        UI.drawImage(fname,x,y);

    }

    /** Sets up the user interface - mouselistener and buttons */
    public MiniPaint(){

        UI.setMouseMotionListener(this::doMouse);
        UI.addButton("Line",()->{currentShape="Line";});
        UI.addButton("Pen",()->{currentShape="Pen";});
        UI.addButton("Rectangle",()->{currentShape="Rectangle";});
        UI.addButton("Oval",()->{currentShape="Oval";});
        UI.addButton("Image",()->{currentShape="Image";});
        UI.addButton("Erase",()->{currentShape="Erase";});

        UI.addButton("Ring",()->{currentShape="Ring";});
        // UI.addSlider( "Brush Width", 1, 30, 5, this::setSize);
        // UI.addButton("Color",this::doChooseColor);
        this.fillBt=UI.addButton("Fill/NoFill",this::ifFill);
        this.colorBt= UI.addButton("Color",this::doChooseColor);

        UI.addButton("Clear",UI::clearGraphics);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(0);
    }

    /*# YOUR CODE HERE */
    // Main:  constructs a new MiniPaint object
    public static void main(String[] arguments){
        new MiniPaint();
    }        

}
