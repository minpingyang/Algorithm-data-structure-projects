// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 10
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.awt.Color;
import java.io.*;
import java.util.*;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
/** The MiniDraw program allows the user to create, save, and reload files
 *    specifying drawings consisting of a list of simple shapes.
 *    The program allows the user to
 *      - add a new shape to the drawing
 *      - remove a shape from the drawing
 *      - move a shape to a different position
 *      - set the colour for the next shape
 *      - save the current drawing to a file
 *      - load a previous drawing from a file.
 *    The shapes include lines, rectangles, ovals, and dots
 *        
 *    Classes
 *      The MiniDraw class handles all the user interaction:
 *        buttons, mouse actions, file opening and closing.
 *        It stores the current drawing in an ArrayList of Shape .
 *
 *      The Shape interface specifies the Shape type
 *      The shape classes all implement Shape and represent different kinds of shapes.
 *
 *    Files:
 *      A drawing is stored in a file containing one line for each shape,
 *        Each line must start with the name of the type of shape,
 *         followed by a specification of the shape,
 *         First the colour (three integers for red, blue, and green)
 *         Then the position (x and y)
 *         The other values will depend on the shape.
 *        
 *    User Interface:
 *        There are buttons for dealing with the whole drawing (New, Open, Save),
 *         buttons for specifying the next shape to draw, and
 *         button for setting the color,
 *         buttons for moving, removing and resizing shapes.
 */

public class MiniDraw {
    // Fields
    private ArrayList<Shape> shapes = new ArrayList<Shape>();    // the collection of shapes

    // suggested fields to store mouse positions, current action, current shape, current colour, etc

    public static double pressedX;                 //where the mouse was pressed
    public static double pressedY;  
    private String currentAction = "Line";   // current action ("Move", etc) or shape ("Line" etc)
    private Shape currentShape = null;      //  the current shape (or null if no shape)
    private Color currentColor = Color.red;
    private Polygon currentPolygon=null;

    /** Constructor sets up the GUI:
     *  sets the mouse listener and adds all the buttons
     *  For New, Open, Save, and Color, call the appropriate method (see below)
     *   to perform the action immediately.
     *  For other buttons, store the button name in the currentAction field
     */
    public MiniDraw(){
        UI.setMouseMotionListener(this::doMouse);
        UI.addButton("New",this::newDrawing);
        UI.addButton("Open",this::openDrawing);
        UI.addButton("Save",this::saveDrawing);
        UI.addButton("Line",()->{currentAction="Line";});
        UI.addButton("Rect",()->{currentAction="Rect";});
        UI.addButton("Oval",()->{currentAction="Oval";});
        UI.addButton("Dot",()->{currentAction="Dot";});
        UI.addButton("Poly",()->{currentPolygon=null;currentAction="Poly";});
        UI.addButton("Color",this::selectColor);
        UI.addButton("Move",()->{currentAction="Move";});
        UI.addButton("Delete",()->{currentAction="Delete";});
        UI.addButton("Resize",()->{currentAction="Resize";});
        
    }

    // Respond to mouse events 

    /** When mouse is pressed, remember the position in fields
     *  and also find the shape it is on (if any), and store
     *  the shape in a field (use the findShape(..) method)
     *  When the Mouse is released, depending on the currentAction,
     *  - perform the action (move, delete, or resize).
     *    move and resize are done on the shape where the mouse was pressed,
     *    delete is done on the shape where the mouse was released 
     *  - construct the shape and add to the shapes ArrayList,
     *    (though the polygon is more complicated)
     *  - redraw the drawing.
     *  It is easiest to call other methods (see below) to actually do the work,
     *  otherwise this method gets too big!
     */
    public void doMouse(String mouseAction, double x, double y) {
        if (mouseAction.equals("pressed")){
            this.currentShape = this.findShape(x, y);
            this.pressedX=x;
            this.pressedY=y;
        }
        if (mouseAction.equals("released")){
            this.addShape(this.pressedX,this.pressedY,x,y);

            if(currentAction.equals("Move")){
                if(currentShape!=null&&currentShape.on(this.pressedX,this.pressedY)){

                    currentShape.moveBy(x-this.pressedX,y-this.pressedY);
                }

            }
            if(currentShape!=null&&currentAction.equals("Delete")&&currentShape.on(this.pressedX,this.pressedY)){
                this.deleteShape(x,y);
            }
            if(currentShape!=null&&currentAction.equals("Resize")&&currentShape.on(this.pressedX,this.pressedY)){
                this.resizeShape(x-this.pressedX,y-this.pressedY);
            }

            this.drawDrawing();
        }

    }

    // -----------------------------------------------------
    // Methods that actually do the work  
    // -----------------------------------------------------

    /** Draws all the shapes in the list on the graphics pane
     *  First clears the graphics pane, then draws each shape,
     *  Finally repaints the graphics pane
     */
    public void drawDrawing(){
        UI.clearGraphics();
        for(Shape s:shapes){
            s.redraw();
        }
        UI.repaintGraphics();
    }   

    /** Checks each shape in the list to see if the point (x,y) is on the shape.
     *  It returns the topmost shape for which this is true.
     *     Returns null if there is no such shape.
     */
    public Shape findShape(double x, double y){
        for(int i=shapes.size()-1;i>=0;i--){
            if(shapes.get(i).on(x,y)){
                return shapes.get(i);
            }
        }

        // failed to find any shape that the point was over 
        return null;  
    }

    /** Sets the current color.
     * Asks user for a new color using a JColorChooser (see MiniPaint, Assig 6)
     * As long as the color is not null, it remembers the color 
     */
    private void selectColor(){
        if(this.currentColor!=null){
            this.currentColor = JColorChooser.showDialog(null, "Choose Color", this.currentColor);
        }

    }

    /** Start a new drawing -
     *  initialise the shapes ArrayList and clear the graphics pane. 
     */
    public void newDrawing(){

        int option = JOptionPane.showConfirmDialog(null,"Do you want to save the drawing first?");
        if (option==0){
            this.saveDrawing();
        }
        else if(option==1){
            UI.clearGraphics();
            shapes.clear();
            UI.repaintGraphics();
        }

    }

    /** Construct a new Shape object of the appropriate kind
     *    (depending on currentAction) using the appropriate constructor
     *    of the Line, Rectangle, Oval, or Dot classes.
     *    Adds the shape to the end of the collection.
     */
    public void addShape(double x1, double y1, double x2, double y2){
        Trace.printf("Drawing shape %s, at (%.2f, %.2f)-(%.2f, %.2f)\n",
            this.currentAction, x1, y1, x2, y2);  //for debugging
        if(currentAction.equals("Line")){
            shapes.add(new Line(x1,y1,x2,y2,this.currentColor));
        }
        if(currentAction.equals("Rect")){
            double width=Math.abs(x1-x2);
            double height=Math.abs(y1-y2);
            double x=Math.min(x1,x2);
            double y=Math.min(y1,y2);

            shapes.add(new Rectangle(x,y,width,height,this.currentColor));
        }
        if(currentAction.equals("Oval")){
            double width=Math.abs(x1-x2);
            double height=Math.abs(y1-y2);
            double x=Math.min(x1,x2);
            double y=Math.min(y1,y2);
            shapes.add(new Oval(x,y,width,height,this.currentColor));

        }
        if(currentAction.equals("Dot")){
            shapes.add(new Dot(x2,y2,this.currentColor));
        }
        if(currentAction.equals("Poly")){
            this.addPolygon(x2,y2);
        }

    }

    /** Moves the current shape (if there is one)
     *    to where the mouse was released.
     *    Ie, change its position by (toX-fromX) and (toY-fromY)
     */
    public void moveShape(double changeX, double changeY){
        Trace.printf("Moving shape by (%.2f, %.2f)\n", changeX, changeY);  //for debugging
        this.currentAction = "Move";

    }

    /** Finds the shape that was under the mouseReleased position (x, y)
     *    and then removes it from the ArrayList of shapes. 
     *  If not pressed on any shape, then do nothing.
     */
    public void deleteShape(double x, double y){
        Trace.printf("Deleting shape under (%.2f, %.2f)\n", x, y);  //for debugging
        this.currentAction = "Delete";
        for(int i=shapes.size()-1;i>=0;i--){
            if(shapes.get(i).on(x,y)){
                shapes.remove(i);
                UI.println(shapes.get(i));
            }
        }
    }

    // METHODS FOR THE COMPLETION PART
    /** Resizes the current shape. A simple way of doing it is to
     *    resize the shape by the amount that the mouse was moved
     *    (ie from (fromX, fromY) to (toX, toY)). 
     *    If the mouse is moved to the right, the shape should
     *    be made that much wider on each side; if the mouse is moved to
     *    the left, the shape should be made that much narrower on each side
     *    If the mouse is moved up, the shape should be made
     *    that much higher top and bottom; if the mouse is moved down, the shape 
     *    should be made that much shorter top and bottom.
     *    The effect is that if the user drags from the top right corner of
     *    the shape, the shape should be resized to whereever the dragged to.
     */
    public void resizeShape(double changeX, double changeY){
        Trace.printf("Changing size of shape by (%.2f, %.2f) \n", changeX, changeY);  //for debugging
        currentShape.resize(changeX,changeY);

    }

    /** Adds a polygon [challenge].
     *    If the currentPolygon is null, then create a new polygon with
     *    just the point x,y. Store it in currentPolygon, and add it to shapes.
     *    If the currentPolygon is not null, then add a new point to it.
     *    (Don't add it to shapes, since it is already there).
     *    Note, you need to reset currentPolygon to null every time a button is pressed
     */
    public void addPolygon(double x, double y){

        if(this.currentPolygon==null){
            this.currentPolygon=new Polygon(x,y,this.currentColor);
            shapes.add(currentPolygon);

        }else{
            this.currentPolygon.addXY(x,y);
            //shapes.add(currentPolygon);
        }

    }

    /** Ask the user to select a file and save the current drawing to the file. */
    public void saveDrawing(){
        String filename=UIFileChooser.save();
        if(filename!=null){
            try{
                PrintStream ps=new PrintStream(new File(filename));

                for(int i=0;i<this.shapes.size();i++){
                    Shape s=this.shapes.get(i);
                    String str=s.toString();
                    ps.println(str);
                }
                ps.close();
            }
            catch(IOException e){
                UI.printf("File Failure % \n", e);
            }
        }

    }
    /**
     * Ask the user for a file to open,
     * then read all the shape descriptions into the current drawing.
     * For each line of the file, it will read the first token to find out which
     * kind of shape and read the rest of the line into a string.
     * It will then call the appropriate constructor, passing the string as an argument.
     */
    public void openDrawing(){
        int option = JOptionPane.showConfirmDialog(null,"Do you want to save the drawing before open a file?");
        if (option==0){
            this.saveDrawing();
            String filename=UIFileChooser.open();
            try {
                if(filename!=null){
                    Scanner sc=new Scanner(new File(filename));

                    while(sc.hasNext()){
                        String kindShape=sc.next();

                        String restLine=sc.nextLine();
                        if(kindShape.equals("Line")){
                            shapes.add(new Line(restLine));

                        }
                        if(kindShape.equals("Rectangle")){
                            shapes.add(new Rectangle(restLine));

                        }
                        if(kindShape.equals("Dot")){
                            shapes.add(new Dot(restLine));

                        }
                        if(kindShape.equals("Oval")){
                            shapes.add(new Oval(restLine));;
                        }
                        if(kindShape.equals("Polygon")){
                            shapes.add(new Polygon(restLine));;
                        }
                    }
                    this.drawDrawing();
                    sc.close();
                }

            }

            catch(IOException e) {UI.printf("File Failure %s \n", e);}
        }
        else if(option==1){
            String filename=UIFileChooser.open();
            try {
                if(filename!=null){
                    Scanner sc=new Scanner(new File(filename));

                    while(sc.hasNext()){
                        String kindShape=sc.next();

                        String restLine=sc.nextLine();
                        if(kindShape.equals("Line")){
                            shapes.add(new Line(restLine));

                        }
                        if(kindShape.equals("Rectangle")){
                            shapes.add(new Rectangle(restLine));

                        }
                        if(kindShape.equals("Dot")){
                            shapes.add(new Dot(restLine));

                        }
                        if(kindShape.equals("Oval")){
                            shapes.add(new Oval(restLine));;
                        }
                        if(kindShape.equals("Polygon")){
                            shapes.add(new Polygon(restLine));;
                        }
                    }
                    this.drawDrawing();
                    sc.close();
                }

            }

            catch(IOException e) {UI.printf("File Failure %s \n", e);}
        }

    }

    public static void main(String args[]){
        new MiniDraw();
    }

}

