package code.comp261.ass1;
/*
 * Node class represents node of the graph
 * @author MinPing
 * */

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import javax.sound.sampled.Line;

import org.omg.CORBA.portable.ValueBase;

import code.comp261.example.Location;

public class Node {
	public final int nodeId;
	public final Location location;
	public final Set<RoadSegment> jointedSegments;
	private Color color;
	/*default color: blue;
	 * selected corlor: orange;
	 * navigation: red
	 * */
	public static final Color DEFAULT_COLOR = new Color(83, 141, 213);
	public static final Color SELECTED_COLOR = new Color(255, 192, 0);
	public static final Color NAVIGATION_COLOR = new Color(255, 0, 0);
	public Set<Node> neighboursNode;
	/*two constant for adjusting shape of node during the process of zooming
	 * */
	public static final int NODE_WIDTH = 1;
	public static final double NODE_LEAN = 0.7;
	/*constructor
	 * @parma string:  the data which is loaded from files
	 * */
	public Node(String string) {
		String[] data = string.split("\t");
		nodeId = Integer.parseInt(data[0]);
		double lat = Double.parseDouble(data[1]);
		double lon = Double.parseDouble(data[2]);
		// use the newFromLation function defined in location class to transfer lat&lone --> Location Object
		location = Location.newFromLatLon(lat, lon);
		color = DEFAULT_COLOR;  //Initialise color
		jointedSegments  = new HashSet<>();  //use set collections to collect the unique road segments in the collection
		neighboursNode = new HashSet<>(); // same as the collection of segements. Neighbours of node are unique in the collection
			
	}
	/**
	 * fill neighbours of this node into the collection of neighbours node
	 * 
	 * */
	public void setNeighbours() {
		for (RoadSegment roadSegment : jointedSegments) {
			Node neighbour =roadSegment.N
		}
	}
	
	
	/*color setter
	 * */
	public void setColor(Color color) {
		this.color = color;
	}
		
		
}
