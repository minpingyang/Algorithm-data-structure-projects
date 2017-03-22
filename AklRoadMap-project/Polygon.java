package code.comp261.ass1;


import java.awt.*;
import java.util.List;
import java.util.Set;
/**
 * The polygon data is used to draw a nice map with lakes, parks, airports, a coastline, etc.
 * draw method
 * **/
public class Polygon{
    public final String label;
    public final Integer type,endLevel,cityIdx;
    //every location should be ordered,since they are coordinator (x,y), so using List
    // but all locations of each polygon do not have to have the order ,so using Set
    public final Set<List<Location>> positions;
    //Color is used to fill different color for different types of places
    public  final Color color;
    /**constructor
     * Pick few special places and set them to special speprate color
     * **/
    public Polygon(Integer type, String label, Integer endLevel, Integer cityIdx, Set<List<Location>>positions){
        this.type = type;
        this.label = label;
        this.endLevel = endLevel;
        this.cityIdx = cityIdx;
        this.positions = positions;
        //initialise the color for different places
        switch (this.type) {
            case 0x07:
            case 0x0a:
                this.color = new Color(208, 211, 162); // airport & university
                break;
            case 0x0b:
                this.color = new Color(235, 208, 193); // Hospital
                break;
            case 0x28:
            case 0x29:
            case 0x32:
            case 0x3b:
            case 0x3c:
            case 0x3d:
            case 0x3e:
            case 0x3f:
            case 0x40:
            case 0x41:
            case 0x42:
            case 0x43:
            case 0x44:
            case 0x45:
            case 0x46:
            case 0x47:
            case 0x48:
            case 0x49:
            case 0x4c:
            case 0x4d:
                this.color = new Color(159, 217, 255); // Ocean
                break;
            case 0x14:
            case 0x15:
            case 0x16:
            case 0x17:
            case 0x18:
            case 0x19:
            case 0x1a:
            case 0x1e:
            case 0x1f:
            case 0x20:
            case 0x4e:
            case 0x4f:
            case 0x50:
            case 0x51:
                this.color = new Color(199, 223, 172); // Park
                break;
            default:
                this.color = new Color(233, 229, 220);
        }
    }
    /**
     * draw method is called by redraw method of Graph class
     * asPoint method has already concerned about scale changing
     *use graphics class to fillPolygon and set Color
     *
     * ***/

    public void draw(Graphics g, Location currentOrigin, double currentScale, Dimension dimension){
        //coordinate of centre of panel
        int centrXofPanel = (int) (dimension.getWidth()/2);
        int centrYofPanel = (int)(dimension.getHeight()/2);
        //use nested-for loop to read read coordinator of each polygon
        for(List<Location> polygon: positions){
            int  numberOfpointsOfPolygon= polygon.size();
            int[] xPoints = new int[numberOfpointsOfPolygon];
            int[] yPoints = new int[numberOfpointsOfPolygon];
            for(int i=0; i<numberOfpointsOfPolygon;i++){
                Point point = polygon.get(i).asPoint(currentOrigin,currentScale);
                // create the changed points of polygon base on centre of display panel as origin, not left-top original
                Point changedPoint = new Point(point.x + centrXofPanel,point.y + centrYofPanel);
                xPoints[i] = (int) changedPoint.getX();
                yPoints[i] = (int) changedPoint.getY();
            }
            g.setColor(color);
            g.fillPolygon(xPoints,yPoints,numberOfpointsOfPolygon);
        }
    }

}