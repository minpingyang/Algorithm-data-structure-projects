package SymbolDrawer;

 


import ecs100.*;
import java.awt.Color;

/**
 * Draws various symbols: flags, signs, and car logos
 *
 * The correct dimensions of the official flags varies from country to country,
 * The exact colours of the flags will also be difficult to match;
 * It is fine to use the standard colours: red, green, blue, orange, etc
 * You can find lots of flag details (including the correct dimensions and colours)
 * from   http://www.crwflags.com/fotw/flags/    
 */
public class SymbolDrawer{

    public static final double top = 100;
    public static final double left = 50;

    /** The flag of France has three vertical stripes;
     *  The left is blue, the right is red and the middle is white.
     *  The flag is 2/3 as high as it is wide (ratio 2:3).
     *  CORE
     */
    public void franceFlag(){
        double width=UI.askDouble("enters width");
        double height=width*2/3.0;
        UI.clearGraphics();
        UI.setColor(Color.black);
        UI.drawRect(left,top,width,height);
        UI.setColor(Color.blue.darker());
        UI.fillRect(left,top,width*1/3.0,height);
        UI.setColor(Color.red.darker());
        UI.fillRect(left+(width*2/3.0),top,width*1/3.0,height);
    }

    /** The hospital sign consists of a blue square with a big white centred H
     *  The H consists of 3 rectangles (width is the size divided by 7.5
     *  A square means that the hight and the width are of equal size
     *  CORE
     */
    public void hospitalSign() {
       double width=UI.askDouble("enters width");
       UI.clearGraphics();
       UI.drawRect(left,top,width,width);
       UI.setColor(Color.blue.darker());
       UI.fillRect(left,top,width,width);
       UI.setColor(Color.white);
       UI.fillRect(left+(width/4.0),top+(width/5.0),width/7.5,width*3/5);
       UI.setColor(Color.white);
       UI.fillRect(left+(width/4.0)+(width/7.5),top+(width/2.0)-(width/7.5/2.0),width/2.0-width/7.5,width/7.5);
       UI.setColor(Color.white);
       UI.fillRect(left+(width*3.0/4.0)-(width/7.5),top+(width/5.0),width/7.5,width*3/5);
    } 

    /**
     *  The flag of Laos has three horizontal stripes with a white circle in the centre;
     *  The flag is 2/3 as high as it is wide (ratio 2:3).
     *  The top and bottom stripes are red, and they are each 1/4 of the height of the flag.
     *  The middle stripe is blue and it is 1/2 of the height of the flag.
     *  The diameter of white circle is 4/5 the height of blue strip, 
     *       eg 2/5 of the total height.
     *  CORE
     */
    public void laosFlag() {
        double width=UI.askDouble("enter width");
        double height=width*(2.0/3.0);
        double diam=height*2.0/5.0;
        UI.clearGraphics();
        UI.setColor(Color.black);
        UI.drawRect(left,top,width,height);
        UI.setColor(Color.red.darker());
        UI.fillRect(left,top,width,height/4.0);
        UI.setColor(Color.blue.darker());
        UI.fillRect(left,top+height/4.0,width,height/2.0);
        UI.setColor(Color.red.darker());
        UI.fillRect(left,top+height*3.0/4.0,width,height/4.0);
        UI.setColor(Color.white);
        UI.fillOval(left+width/2.0-diam/2.0,top+height/2.0-diam/2.0,diam,diam);
    }

    /**
     *  The flag of the United Arab Emirates is divided into four rectangular parts.
     *  The first red rectangle is the vertical band nearest to the mast, its length being
     *  equivalent to the height of the flag, while its width is one quarter of the length of
     *  the entire flag.
     *  The other three parts are three horizontal stripes of equal size.
     *  The top most is green, the middle is white and the lower is black.
     *  The flag is 1/2 as high as it is wide (ratio 1:2).
     *  COMPLETION
     */
    public void uaeFlag() {
       double width=UI.askDouble("enter width");
       double height=width/2.0;
       UI.clearGraphics();
       UI.setColor(Color.black);
       UI.drawRect(left,top,width,height);
       UI.setColor(Color.red);
       UI.fillRect(left,top,width/4.0,height);
       UI.setColor(Color.green.darker());
       UI.fillRect(left+width/4.0,top,width*3.0/4.0,height/3.0);
       UI.setColor(Color.white);
       UI.fillRect(left+width/4.0,top+height/3.0,width*3.0/4.0,height/3.0);
       UI.setColor(Color.black);
       UI.fillRect(left+width/4.0,top+height*2.0/3.0,width*3.0/4.0,height/3.0);
    }

    /** The flag of Greenland is a rectangle whose top half is white
     *  and bottom half is red. There is a circle in the middle (off-set to left) 
     *  which is also half white/red but on the opposite sides.
     *  The flag is 2/3 as high as it is wide (ratio 2:3).
     *  COMPLETION
     */
    public void greenlandFlag() {
       double width =UI.askDouble("enter width");
       double height=width*2/3;
       double diam=width*17.0/37.0;
       UI.clearGraphics();
       UI.setColor(Color.black);
       UI.drawRect(left,top,width,height);
       UI.setColor(Color.red);
       UI.fillRect(left,top+height/2.0,width,height/2.0);
       UI.setColor(Color.white);
       UI.fillArc(left+width*6.0/37.0,top+height/2.0-diam/2.0,diam,diam,180,360);
       UI.setColor(Color.red);
       UI.fillArc(left+width*6.0/37.0,top+height/2.0-diam/2.0,diam,diam,0,180);
    }

    /** Mitsubishi
     * 
     *  CHALLENGE
     */
    public void mitsubishiLogo() {
        double left=300;
        double size=UI.askDouble("enter size");
        double[] x1={left,left-size/2,left+size/2};
        double[] y1={top,top+size*1.2,top+size*1.2};
        double[] x2={left-size/6.0,left-size/3.0,left};
        double[] y2={top+size*1.2/3.0,top+size*2.4/3,top+size*2.4/3};
        double[] x3={left+size/6.0,left,left+size/2.0};
        double[] y3={top+size*1.2/3.0,top+size*2.4/3,top+size*2.4/3};
        double[] x4={left,left-size/6.0,left+size/6.0};
        double[] y4={top+size*2.4/3,top+size*1.2,top+size*1.2};
        UI.clearGraphics();
        UI.setColor(Color.red);
        UI.fillPolygon(x1,y1, 3);
        UI.setColor(Color.white);
        UI.fillPolygon(x2,y2, 3);
        UI.setColor(Color.white);
        UI.fillPolygon(x3,y3, 3);
        UI.setColor(Color.white);
        UI.fillPolygon(x4,y4, 3);
        //UI.fillPolygon(_xPoints_, _yPoints_, _nPoints_)

    }

    /** The Koru flag belongs to the long list of new flags designs
     *  It has been designed by Sven Baker from Wellington
     *  The flag is 1/2 as high as it is wide (ratio 1:2).
     *  CHALLENGE
     */
    public void koruFlag() {
        double width=UI.askDouble("enter width");
        double height =width/2.0;
        double diam1=width*47.0/84.0;
        double diam2=width*17.0/42.0;
        double diam3=width*5.0/21.0;
        double diam4=width/6.0;
        UI.clearGraphics();
        UI.setColor(Color.red.darker());
        UI.fillRect(left,top,width/2.0,height);
        UI.setColor(Color.blue.darker());
        UI.fillRect(left+width/2.0,top,width/2.0,height);
        UI.setColor(Color.white);
        UI.fillOval(left+width/2.0-diam1/2.0,top+height/2.0-diam1/2.0,diam1, diam1);
        UI.setColor(Color.blue.darker());
        UI.fillOval(left+width/2.0-diam2/2.0,top+height/2.0-diam2/2.0,diam2,diam2);
        UI.setColor(Color.blue.darker());
        UI.fillArc(left+width/2.0+diam2/2.0-diam3/2.0,top+height/2.0-diam3/2.0, diam3, diam3,0,-180);
        UI.setColor(Color.white);
        UI.fillOval(left+width/2.0+diam2/2.0-diam4/2.0,top+height/2.0-diam4/2.0,diam4,diam4);
    }

    /** ---------- The code below is already written for you ---------- **/
    /** Constructor, sets up the user interface */
    public SymbolDrawer(){
        UI.initialise();

        // CORE
        UI.addButton("Core: Flag of France", this::franceFlag);
        UI.addButton("Core: Hospital Sign", this::hospitalSign);
        UI.addButton("Core: Flag of Laos", this::laosFlag);

        // COMPLETION
        UI.addButton("Completion: Flag of UAE", this::uaeFlag);
        UI.addButton("Completion: Flag of Greenland", this::greenlandFlag);

        // CHALLENGE
        UI.addButton("Challenge: Mitsubishi", this::mitsubishiLogo);
        UI.addButton("Challenge: Koru Flag", this::koruFlag);
        UI.addButton("Quit", UI::quit);
    }


}
