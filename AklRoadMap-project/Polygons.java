package code.comp261.ass1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Set;
/*
 * colorful polygon on map 
 * @ author Minping 
 * */
// Polygon class is used to make the road map beatiful


public class Polygons {
	public final String label;
	public final Integer type;
	public final Integer endLevel;
	public final Integer cityId;
	
	public final Set<ArrayList<Location>> location;
	private final Color color;
	
	public Polygons (Integer type, String label, Integer endLevel,Integer cityId, Set<ArrayList<Location>> location ){
		
		this.type = type;
		this.label = label;
		this.endLevel = endLevel ;
		this.cityId = cityId ;
		this.location = location;
		switch (type){
		case 0x07:
			color = new Color(r, g, b); //airport
			break;
		case 0x0a:
			color = new Color(r, g, b); // college;
			break;
		case 0x0b:
			color = new Color(r, g, b); // hospital
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
            this.color = new Color(r, g, b); // green field 
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
            this.color = new Color(r, g, b); // sea  ocean  river lake
            break;
        case 0x53:
            this.color = new Color(r, g, b); // sand
            break;
        default:
            this.color = new Color(r, g, b);
		}
	}
	public void draw(Graphics graphics,Location origin, double scale, Dimension dimension){
		int XCentrCoord =(int) (dimension.getWidth() / 2);
		int YCentrCoord =(int) (dimension.getHeight() / 2);
		for (ArrayList<Location> arrayList : location) {
			int numberOfPolygons= arrayList.size();
			int[] xPoints = new int [numberOfPolygons];
			int[] yPoints = new int [numberOfPolygons];
			for (int i = 0; i < yPoints.length; i++) {
				Point point = arrayList.get(i).asPoint(origin, scale);
				//point2 is the new point after zoom in  
				Point point2 = new Point(point.x + XCentrCoord, point.y+ YCentrCoord);
				xPoints[i] =(int) point2.getX();
				yPoints[i] = (int) point2.getY();					
			}
			graphics.setColor(color);
			graphics.fillPolygon(xPoints, yPoints, numberOfPolygons);
		}
		
	}
	
}
