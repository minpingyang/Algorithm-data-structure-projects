package code.comp261.ass1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.print.attribute.Size2DSyntax;
import javax.sound.sampled.Line;
import javax.swing.text.Segment;

public class Node {
	//easy to access, but can not change. by using public-final
	public final int nodeID;
	public final Location location;
	public final Set<RoadSegment> intersection;
	public Set<Node> neighbourNodes;
	public final int nodeSize;   // it would be changed with zooming in/our , fix it later
	private Color color; //setter-getter later
	//set unique color to highlight different actions;
	public static final Color GENERAL_COLOUR = new Color(215, 226, 192); //green
	public static final Color SELECTED_COLOUR = new Color(25, 55, 114); //blue
	public static final Color NAVIGATION_COLOUR = new Color(186, 87, 16); //brown
	public static final Color INTERSECTIONS_COLOUR = new Color(179,39, 109); // pink
	
	//
	
	
	
	
	
	
	public Node(String line){
		String[] values = line.split("\t");
		this.nodeID = Integer.parseInt(values[0]);
		double lat = Double.parseDouble(values[1]);
		double lon = Double.parseDouble(values[2]);
		this.location = Location.newFromLatLon(lat, lon);
		intersection = new HashSet<>();
		neighbourNodes = new HashSet<>();	
		nodeSize = 1;
	}
	
	public void setColor (Color color){
		this.color = color;
	}
	public void setNeighbourNodes(){
		for (RoadSegment sg : intersection) {
			Node endNode = sg;
		}
	}
	public void draw(Graphics g, Location origin, double scale, Dimension d) {
		int centX = (int) (d.getWidth() / 2);
		int centY = (int) (d.getHeight() / 2);
		// use a Point class to record position
		Point p1 = this.location.asPoint(origin, scale);
		//after zooming in, the location of P1 will be changed to P2 (right-bottom)
		//limit the range of the location of p2 (screen size is determined by dimension)
		Point p2 = new Point(p1.x, p1.y);
		if (p2.x > d.width || p2.x < 0 || p2.y > d.height || p2.y <0)
			return;
		g.setColor(this.color);
		g.fillOval(p2.x - nodeSize / 2, p2.y - nodeSize / 2,nodeSize, nodeSize);
					
	}
	
	
}
