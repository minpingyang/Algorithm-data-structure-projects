// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 Assignment 3
 * Name:Minping Yang
 * Usercode:
 * ID:300364234
 */


import ecs100.*;
import java.awt.Color;
import javax.swing.JColorChooser;

/** TricolourFlagDrawer: draws a series of tricolour flags */
public class TricolourFlagDrawer{

    public static final double width = 200;
    public static final double height = 133;
    boolean horizontal=true;
    /**
     * asks user for a position and three colours, then calls the 
     * drawThreeColourFlagCore method, passing the appropriate arguments
     *
     * CORE
     */
    public void testCore(){
        double left = UI.askDouble("left of flag");
        double top = UI.askDouble("top of flag");
        UI.println("Now choose the colours");
        UI.clearGraphics();
        Color stripe1 = JColorChooser.showDialog(null, "First Stripe", Color.white);
        Color stripe2 = JColorChooser.showDialog(null, "Second Stripe", Color.white);
        Color stripe3 = JColorChooser.showDialog(null, "Third Stripe", Color.white);
        this.drawThreeColourFlagCore(left, top, stripe1, stripe2, stripe3 );
    }

    /**
     * draws a three colour flag consisting of three vertical equal-width stripes
     * at the given position
     *
     * CORE
     */
    public void drawThreeColourFlagCore(double left,double top,Color stripe1,Color stripe2,Color stripe3 ){
        UI.clearGraphics();
       
        UI.setColor(stripe1);
        UI.fillRect(left,top,width*1/3.0,height);
        UI.setColor(stripe2);
        UI.fillRect(left+(width*1/3.0),top,width*1/3.0,height);
        UI.setColor(stripe3);
        UI.fillRect(left+(width*2/3.0),top,width*1/3.0,height);
        UI.setColor(Color.black);
        UI.drawRect(left,top,width,height);
    }

    /**
     * draws multiple flag made up of three equal size stripes by calling the
     * drawThreeColourFlagCompletion method, passing the appropriate arguments
     *
     * COMPLETION
     */
 public void testCompletion(){
        double left=100;
        double top=20;
        UI.clearGraphics();
        this.drawThreeColourFlagCompletion(true, 20, 50, Color.black, Color.yellow, Color.red);               // Belgium
        this.drawThreeColourFlagCompletion(false, 250, 100, Color.black, Color.red, Color.yellow);            // Germany
        this.drawThreeColourFlagCompletion(true, 140, 430, Color.blue, Color.white, Color.red);               // France
        this.drawThreeColourFlagCompletion(false, 470, 30, Color.red, Color.white, Color.blue);               // The Netherlands
        this.drawThreeColourFlagCompletion(false, 50, 250, Color.white, Color.blue, Color.red);               // Russia
        this.drawThreeColourFlagCompletion(true, 290, 270, Color.red, Color.yellow, Color.green.darker());    // Guinea
    }

   /**
     * draws a three colour flag consisting of three equal-size stripes
     * at the given position
     * The stripes can be either vertical or horizontal
     *
     * COMPLETION
     */
    public void drawThreeColourFlagCompletion(boolean horizontal,double left,double top,Color stripe1,Color stripe2,Color stripe3 ){
       if(horizontal){
        UI.setColor(stripe1);
        UI.fillRect(left,top,width*1/3.0,height);
        UI.setColor(stripe2);
        UI.fillRect(left+(width*1/3.0),top,width*1/3.0,height);
        UI.setColor(stripe3);
        UI.fillRect(left+(width*2/3.0),top,width*1/3.0,height);
        UI.setColor(Color.black);
        UI.drawRect(left,top,width,height);
      }else{
        UI.setColor(stripe1);
        UI.fillRect(left,top,width,height*1/3.0);
        UI.setColor(stripe2);
        UI.fillRect(left,top+(height*1/3.0),width,height*1/3.0);
        UI.setColor(stripe3);
        UI.fillRect(left,top+(height*2/3.0),width,height*1/3.0);
        UI.setColor(Color.black);
        UI.drawRect(left,top,width,height);
      }
    }
    public void testChallenge(){
        double left=100;
        double top=20;
        UI.clearGraphics();
        this.drawThreeColourFlagChallenge(true,125,20+6, Color.green.darker(), Color.red.darker(), Color.green.darker(),"Colombia");
        this.drawThreeColourFlagChallenge(true,100+250+25,26, Color.blue, Color.white, Color.blue,"Guatemala");
        this.drawThreeColourFlagChallenge(true,100+500+25,26, Color.red.darker(), Color.white, Color.red.darker(),"Mons, Belgium");
        
        this.drawThreeColourFlagChallenge(false,125,200+20+6, Color.black, Color.green.darker(), Color.orange,"African");
        this.drawThreeColourFlagChallenge(false,100+250+25,200+26, Color.blue.darker(), Color.white, Color.green.darker(),"Patagonia");
        this.drawThreeColourFlagChallenge(false,100+500+25,200+26, Color.red.darker(), Color.blue.darker(), Color.orange," Armenia");
        
        this.drawThreeColourFlagChallenge(false,125,200+200+20+6, Color.blue, Color.yellow, Color.orange,"Atlantium");
        this.drawThreeColourFlagChallenge(false,100+250+25,200+200+26, Color.green.darker(), Color.yellow, Color.blue,"Indonesia");
        this.drawThreeColourFlagChallenge(false,100+500+25,200+200+26, Color.orange, Color.green.darker(), Color.red.darker()," Bolivar, Colombia");
    }
    public void drawThreeColourFlagChallenge(boolean horizontal,double left,double top,Color stripe1,Color stripe2,Color stripe3,String country){
      double left1=100;
      double top1=20;
      UI.setColor(Color.black);
      UI.drawRect(left1,top1,750,600);
      UI.setColor(Color.black);
      UI.drawLine(left1,top1+200,left1+750,top1+200);
      UI.setColor(Color.black);
      UI.drawLine(left1,top1+400,left1+750,top1+400);
      UI.setColor(Color.black);
      UI.drawLine(left1+250,top1,left1+250,top1+600);
      UI.setColor(Color.black);
      UI.drawLine(left1+500,top1,left1+500,top1+600);
      if(horizontal){
        UI.setColor(stripe1);
        UI.fillRect(left,top,width*1/3.0,height);
        UI.setColor(stripe2);
        UI.fillRect(left+(width*1/3.0),top,width*1/3.0,height);
        UI.setColor(stripe3);
        UI.fillRect(left+(width*2/3.0),top,width*1/3.0,height);
        UI.setColor(Color.black);
        UI.drawRect(left,top,width,height);
        UI.drawString(country, left+width/3, top+height+30);
    }else{
        UI.setColor(stripe1);
        UI.fillRect(left,top,width,height*1/3.0);
        UI.setColor(stripe2);
        UI.fillRect(left,top+(height*1/3.0),width,height*1/3.0);
        UI.setColor(stripe3);
        UI.fillRect(left,top+(height*2/3.0),width,height*1/3.0);
        UI.setColor(Color.black);
        UI.drawRect(left,top,width,height);
         UI.drawString(country, left+width/3, top+height+30);
      }
}

  
    /** ---------- The code below is already written for you ---------- **/

    /** Constructor: set up user interface */
    public TricolourFlagDrawer(){
        UI.initialise();
        UI.addButton("Clear", UI::clearPanes );
        UI.addButton("Core", this::testCore );
       UI.addButton("Completion", this::testCompletion );
        UI.addButton("Challenge", this::testChallenge );
        UI.addButton("Quit", UI::quit );
    }


}
