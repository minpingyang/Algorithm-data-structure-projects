package code.comp261.ass1;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.List;
import java.awt.event.MouseEvent;
import java.io.File;
/**
 * 
 *Main() method is created in this class to run the java app
 * 
 * @author minping_yang
 * 
 * */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.jws.soap.SOAPBinding.Use;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;
public class AucklandRoadMap extends GUI {
	
	// the center of Auckland City according to Location class
	private static final double CENTRE_LAT = -36.847622;
	private static final double CENTRE_LON = 174.763444;
	private static final Location CENTRE =Location .newFromLatLon(CENTRE_LAT, CENTRE_LON ); //a constant of current centre location
	private double scale;
	private static final double zoom_factor = 1.4;
	private static final double minimun = 7, maximum = 1000;

	private Location origin;
	private Node chosenNode;       // the node is clicked on
	private ArrayList<Roads> matchingRoads; // these roads are matched what the user typed in.
	// private Location locationRoadStartDrag;
	private Node startNode;
	private Node endNode;
	private ArrayList<RoadSegment> path;
	
	//declaration of articulation nodes
	private Set<Node> articulationNodes;
	private boolean doesShowArticulationPoints;
	//a boolean value to decide minimum distance or minimum time which one is user want.
	boolean miniDistance;
	// declare a graph object
	Graph graph ;
	
public AucklandRoadMap (){
	graph = new Graph();
	origin = CENTRE  ;
	Dimension d = getDrawingAreaDimension();
	scale = Math.max(d.getHeight(), d.getWidth()) / 48;       //initialise the value of scale
	chosenNode = null;  //initalise chosen node to null
	endNode = null;
	startNode = null;
	matchingRoads = new ArrayList<>();
	path = new ArrayList<>();
	articulationNodes = new HashSet<>();
	doesShowArticulationPoints= false;
	miniDistance = ture;		
} 

		
		
public static void main(String[] args) {
		new AucklandRoadMap();		
	}

// extend the GUI class, so these abstract classes of GUI should be implemented

@Override
protected void redraw(Graphics g) {
	Dimension d = getDrawingAreaDimension();
	graph.redraw();           //  create a redraw() method of Gaph obejcet
	
}



@Override
protected void onClick(MouseEvent e) {
	// TODO Auto-generated method stub
	
}



@Override
protected void onSearch() {
	// TODO Auto-generated method stub
	
}



@Override
protected void onMove(Move m) {
	// TODO Auto-generated method stub
	
}



@Override
protected void onLoad(File nodes, File roads, File segments, File polygons) {
	// TODO Auto-generated method stub
	
}
	
	
  
}
