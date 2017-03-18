package code.comp261.ass1;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainMap extends GUI {

	Graph graph;

	// the center of Auckland City according to Location class
	private static final double CENTRE_LAT = -36.847622;
	private static final double CENTRE_LON = 174.763444;
	//declare the constant factor and limitation of zooming
	private  static final double MIN_ZOOMING = 6, MAX_ZOOMING = 780;
	private  static final double ZOOM_COEFFICENT = 1.5;
	//the location object of Auckland city
	private static  final Location CENTREAKL= Location.newFromLatLon(CENTRE_LAT,CENTRE_LON);

	private  Node clickedNode, startNod,endNode;
	private  List<RoadSegment> pathsBetweenStartEnd;
	private List<Road> clickedRoad;
	//not sure if need
	private Location locationOnDraged;
	private  Location currentOrigin;
	private  double currentScale;


	/**
	 * constructor, initalise fields.
	 * */
	public MainMap(){
		clickedNode = null;
		startNod = null;
		endNode = null;
		pathsBetweenStartEnd = new ArrayList<>();
		clickedRoad = new ArrayList<>();
		graph = new Graph();
		currentOrigin = CENTREAKL;
		//getDramingAreaDimension from GUI super class ,return the dimensions of the drawing area.
		currentScale = Math.max(getDrawingAreaDimension().getHeight(),getDrawingAreaDimension().getWidth())/55;
		locationOnDraged = null;
	}
	/**
	 * display the information of nodes
	 * */
	private void displayNodeInfo(Node nodeClicking){
		int count = 1; //count
		// line Separator is =======> "\n" new line in C
		String lineSeparator = System.lineSeparator();
		StringBuilder stringBuilder = new StringBuilder("Click on Node which ID is: "+nodeClicking.nodeId
		+ lineSeparator + "Roads the intersection belong to: " + lineSeparator);
		Set<String> infoSet =new HashSet<>();
		for(RoadSegment roadSegment: nodeClicking.linkedSegments){
			String string = roadSegment.road.label +","+ roadSegment.road.city + ", road ID: "+roadSegment.roadId;
			if(!infoSet.contains(string))
				infoSet.add(string);
		}
		for (String string:infoSet){
			stringBuilder.append("(").append(count++).append(")").append(string).append(lineSeparator);
		}
		getTextOutputArea().setText(stringBuilder.toString());

	}

	/** highlight and display information of clicked node
	 *
	 * */

	public  void clickdeNode(Node nodeClicking){
		//set last clicked node as default color
		if(clickedNode!=null)
			clickedNode.setColor(Node.DEFAULT_COLOR);
		if (startNod!= null) {
			startNod.setColor(Node.DEFAULT_COLOR);
			startNod = null;  //prevent repeated operations
		}
		if(endNode!=null){
			endNode.setColor(Node.DEFAULT_COLOR);
			endNode = null; //prevent repeated operations
		}
		if(!pathsBetweenStartEnd.isEmpty()){
			for (RoadSegment roadSegment: pathsBetweenStartEnd){
				roadSegment.setColor(roadSegment.DEFAULT_COLOUR);
			}
		}
		clickedNode = nodeClicking;
		clickedNode.setColor(Node.CLICKED_COLOR);
		displayNodeInfo(clickedNode);
	}
	

	public static void main(String[] args) {
		new MainMap();

	}

	@Override
	protected void redraw(Graphics g) {
		graph.redraw(g,currentOrigin,currentScale,getDrawingAreaDimension());

	}

	@Override
	protected void onClick(MouseEvent e) {

	}

	@Override
	protected void onSearch() {

	}

	@Override
	protected void onMove(Move m) {

	}

	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons) {

	}
}
