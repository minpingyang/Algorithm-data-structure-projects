package code.comp261.ass1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.text.Segment;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.w3c.dom.NamedNodeMap;

public class RoadSegment {
	public int roadId;
	public Node start, end;
	public Roads road;
	public double segmentLength;
	
	public ArrayList<Location> nodeCoordinations;
	private Color color; //setter-getter later
	//set unique color to highlight different actions;
	public static final Color GENERAL_COLOUR = new Color(215, 226, 192); //green
	public static final Color SELECTED_COLOUR = new Color(25, 55, 114); //blue
	public static final Color NAVIGATION_COLOUR = new Color(186, 87, 16); //brown
	/*
	 *constructor for storing the data from segment.tab file, initialise values of fields 
	 * */
	public RoadSegment (String line, Map<Integer, Node>nodeMap, Map<Integer, Roads>roadMap){
		nodeCoordinations =new ArrayList<>();
		String[] value =line.split("\t");
		roadId = Integer.parseInt(value[0]);
		road = roadMap.get(roadId);
		segmentLength = Double.parseDouble(value[1]);
		start = nodeMap.get(Integer.parseInt(value[2]));
		// add the coordination of first node to ArrayList at the beginning
		nodeCoordinations.add(start.location);
		end = nodeMap.get(Integer.parseInt(value[3]));
		// insert all coordinations to arraylist
		for (int i = 4; i < value.length;) {
			double lat =Double.parseDouble(value[i++]);
			double lon =Double.parseDouble(value[i++]);
			nodeCoordinations.add(Location.newFromLatLon(lat, lon));
		}
		// set the 2nd node as the final element of the ArrayList, (after adding others already)
		nodeCoordinations.add(end.location);
		color =GENERAL_COLOUR;	
	}
	/*
	 * draw method is used to draw the segment
	 * @parameter   Dimension object  ------> the current dimension of the display panel
	 * 
	 * */
	
	public void draw(Graphics graphics, Location origin, double scale, Dimension dimension ) {
		// centre of the display panel
		int XcentrPanel = (int) (dimension.getWidth() /2);
		int YcentrPanel = (int) (dimension.getHeight() / 2);
		graphics.setColor(color);
		for (int i = 0; i < nodeCoordinations.size() -1; i++) {
			// transfer Location into Point
			//then, create a new Point which is on the right-bottom from original point
			Point originalPoint = nodeCoordinations.get(i).asPoint(origin, scale);
			
		}
	}
	
	
}
