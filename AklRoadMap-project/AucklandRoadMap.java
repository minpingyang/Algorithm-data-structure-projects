package code.comp261.ass1;

import java.awt.Graphics;
import java.awt.List;
import java.awt.event.MouseEvent;
import java.io.File;
/**
 * 
 * 
 * 
 * @author minping_yang
 * 
 * */
import java.util.ArrayList;
import java.util.Set;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;
public class AucklandRoadMap extends GUI {
	
	// the center of Auckland City according to Location class
	private static final double CENTRE_LAT = -36.847622;
	private static final double CENTRE_LON = 174.763444;
	private double scale;
	private static final double zoom_factor = 1.4;
	private static final double minimun = 7, maximum = 1000;
	private static final Location centre =Location .newFromLatLon(CENTRE_LAT, CENTRE_LON );
	private Location origin;
	private ArrayList<Roads> matchingRoads; // these roads are matched what the user typed in.
	// private Location locationRoadStartDrag;
	private Node startNode;
	private Node endNode;
	private ArrayList<RoadSegment> path;
	
	//declaration of articulation nodes
	private Set<Node> articulationNodes;
	private boolean doesShowArticulationPoints;
	
		
		
public static void main(String[] args) {
		new AucklandRoadMap();		
	}



@Override
protected void redraw(Graphics g) {
	// TODO Auto-generated method stub
	
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
