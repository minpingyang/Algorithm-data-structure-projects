package code.comp261.ass1;

import java.awt.*;
import java.util.List;
import java.util.Set;

/*This class represent a colored polygon which is used to drawing a nicer map - parks, coastline,
 *airport, rivers, etc
 *
 * @author Minping
 * */

public class Polygon {
	public final String label;
	public final Integer type, endLevel, cityIdx;
	//every polygon drawing different places, so they should be unique. so I used set of polygon,
	//the coordinates of each polygon is used a list to collect each point(location) of polygon
	public final Set<List<Location>> positions;
	public final Color color;
	/*constructor
	 * */
	public Polygon(Integer type, String label, Integer endLevel, Integer cityIdx, Set<List<Location>> positions){
		this.type = type;
		this.label = label;
		this.endLevel = endLevel;
		this.cityIdx = cityIdx;
		this.positions = positions;
		switch (this.type) {
        case 0x07:
            this.color = new Color(211, 202, 189); // airport
            break;
        case 0x0a:
            this.color = new Color(232, 221, 189); // college
            break;
        case 0x0b:
            this.color = new Color(235, 210, 207); // hospital
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
            this.color = new Color(202, 223, 170); // green field
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
            this.color = new Color(179, 209, 255); // sea & ocean & lake,water places, etc
            break;
        case 0x53:
            this.color = new Color(255, 225, 104); // sand
            break;
        default:
            this.color = new Color(233, 229, 220);
        }
				
	}
	public void draw(Graphics g, Location currentOrigin, double currentScale, Dimension dimension){
	    //coordinate of centre of panel
        int centrXofPanel = (int) (dimension.getWidth()/2);
        int centrYofPanel = (int)(dimension.getHeight()/2);
        for(List<Location> polygon: positions){
            int  numberOfpointsOfPolygon= polygon.size();
            int[] xPoints = new int[numberOfpointsOfPolygon];
            int[] yPoints = new int[numberOfpointsOfPolygon];
            for(int i=0; i<numberOfpointsOfPolygon;i++){
                Point point = polygon.get(i).asPoint(currentOrigin,currentScale);
                // create the changed points of polygon after zooming
                Point changedPoint = new Point(point.x + centrXofPanel,point.y + centrYofPanel);
                xPoints[i] = (int) changedPoint.getX();
                yPoints[i] = (int) changedPoint.getY();
            }
            g.setColor(color);
            g.fillPolygon(xPoints,yPoints,numberOfpointsOfPolygon);
        }
    }
	
}
