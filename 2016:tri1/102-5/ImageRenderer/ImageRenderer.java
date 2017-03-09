// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 5
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.util.*;
import java.io.*;
import java.awt.Color;

/** Renders plain ppm images onto the graphics panel
 *  ppm images are the simplest possible colour image format.
 */

public class ImageRenderer{
    public static final int top = 20;   // top edge of the image
    public static final int left = 20;  // left edge of the image
    public static final int pixelSize = 2;  

    /** Core:
     * Renders a ppm image file.
     * Asks for the name of the file, then calls renderImageHelper.
     */
    public void renderImageCore(){

        try{
            String fname=UIFileChooser.open("Choose a ppm image file"); 
            File myfile=new File(fname);
            Scanner sc=new Scanner(myfile);
            String magicNumber=sc.next();

            if(magicNumber.equals("P3")){
                this.renderImageHelper(sc);
            }
            else{
                UI.println("please choose the ppm format");
            }

            sc.close();
        }

        catch(IOException e){UI.println("File reading failed");}

    }

    /** Core:
     * Renders a ppm image file.
     * Renders the image at position (left, top).
     * Each pixel of the image  is rendered by a square of size pixelSize
     * Assumes that
     * - the colour depth is 255,
     * - there is just one image in the file (not "animated"), and
     * - there are no comments in the file.
     * The first four tokens are "P3", number of columns, number of rows, 255
     * The remaining tokens are the pixel values (red, green, blue for each pixel)
     */
    public void renderImageHelper(Scanner sc){

        int col=sc.nextInt();
        int row=sc.nextInt();
        sc.next();

        for(int i=0;sc.hasNextInt()&&i<row;i++){
            for(int j=0;j<col;j++){
                int r=sc.nextInt();
                int g=sc.nextInt();
                int b=sc.nextInt();
                Color c1=new Color(r,g,b);
                UI.setColor(c1);

                UI.drawRect(left+2*j,top+2*i,pixelSize,pixelSize);
            }
        }

    }

    /** Completion
     * Renders a ppm image file which may be animated (multiple images in the file)
     * Asks for the name of the file, then renders the image at position (left, top).
     * Each pixel of the image  is rendered by a square of size pixelSize
     * Renders each image in the file in turn with 200 mSec delay.
     * Repeats the sequence 3 times.
     */
    public void renderAnimatedImage(){
        try{
            String fname=UIFileChooser.open("Choose a multi-image file"); 
            File myfile=new File(fname);
            Scanner sc=new Scanner(myfile);
            String magicNumber=sc.next();
            if(magicNumber.equals("P3")){

                for(int x=0;x<3;x++){
                    while(magicNumber.equals("P3")){
                        this.renderImageHelper(sc);
                        UI.sleep(200);
                        if(sc.hasNext()){
                            magicNumber=sc.next();
                        }
                        else{
                            break;
                        }
                    }
                    sc=new Scanner(myfile);
                    magicNumber=sc.next();
                }

            }
            else{
                UI.println("please choose the ppm format");
            }

            sc.close();
        }

        catch(IOException e){UI.println("File reading failed");}

    }

    public void renderImageChallenge(){
        try{
            Scanner sc=new Scanner(new File(UIFileChooser.open("choose a image file")));

            while(sc.hasNext()){

                String magicNumber=sc.next();
                if(magicNumber.equals("P3")){
                    while(!sc.hasNextInt()){
                        sc.nextLine(); // remove comments
                    }

                    int col=sc.nextInt();
                    while(!sc.hasNextInt()){
                        sc.nextLine(); // remove comments
                    }

                    int row=sc.nextInt();
                    while(!sc.hasNextInt()){
                        sc.nextLine(); // remove comments
                    }

                    int depth=sc.nextInt();

                    //read the color value
                    for(int i=0;sc.hasNextInt()&&i<row;i++){
                        for(int j=0;j<col;j++){
                            int r=sc.nextInt();
                            int g=sc.nextInt();
                            int b=sc.nextInt();
                            Color c1=new Color(r*(255/depth),g*(255/depth),b*(255/depth));
                            UI.setColor(c1);

                            UI.drawRect(left+2*j,top+2*i,pixelSize,pixelSize);
                        }
                    }

                }
                else if(magicNumber.equals("P2")){

                    while(!sc.hasNextInt()){
                        sc.nextLine(); // remove comments
                    }

                    int col=sc.nextInt();
                    while(!sc.hasNextInt()){
                        sc.nextLine(); // remove comments
                    }

                    int row=sc.nextInt();
                    while(!sc.hasNextInt()){
                        sc.nextLine(); // remove comments
                    }

                    int depth=sc.nextInt();

                    //read the color value
                    for(int i=0;sc.hasNextInt()&&i<row;i++){
                        for(int j=0;j<col;j++){
                            int grey=sc.nextInt();
                           
                            Color c1=new Color(grey*(255/depth),grey*(255/depth),grey*(255/depth));
                            UI.setColor(c1);

                            UI.drawRect(left+2*j,top+2*i,pixelSize,pixelSize);
                        }
                    }

                }
            }

            sc.close();
        }
        catch(IOException e){UI.println("File reading failed");}

    }

    /** ---------- The code below is already written for you ---------- **/
    // Constructor
    public ImageRenderer() {

        UI.initialise();
        UI.addButton("Clear", UI::clearGraphics );
        UI.addButton("Render (core)", this::renderImageCore );
        UI.addButton("Render (compl)", this::renderAnimatedImage );
        UI.addButton("Render (challenge)", this::renderImageChallenge);
        UI.addButton("Quit", UI::quit );
        UI.setWindowSize(850, 700);
        UI.setDivider(0.0);

    }

}
