package code.comp261.ass1;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainMap extends GUI {

	Graph graph;

	// the center of Auckland City according to Location class
	private  static final double FACTOR_MOVE = 12.3;
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


	private  Location currentOrigin;
	private  double currentScale;

	public static void main(String[] args) {
		new MainMap();

	}
	/**
	 * constructor, initalise fields.
	 * */
	public MainMap(){
		graph = new Graph();
		clickedNode = null;
		startNod = null;
		endNode = null;
		pathsBetweenStartEnd = new ArrayList<>();
		clickedRoad = new ArrayList<>();
		currentOrigin = CENTREAKL;
		//getDrawingAreaDimension from GUI super class ,return the dimensions of the drawing area.
		currentScale = Math.max(getDrawingAreaDimension().getHeight(),getDrawingAreaDimension().getWidth())/55;

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
		//initial the color of special Node
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
	private  void selectStartNode(Node nodeClicking){
		//set last clicked node defalut color;
		if(startNod != null){
			startNod.setColor(Node.DEFAULT_COLOR);
		}
		if(clickedNode != null){
			clickedNode.setColor(Node.DEFAULT_COLOR);
			clickedNode = null;
		}
		// now, the current clicked node becomes the end node, highlight the end node to navigation color
		endNode = nodeClicking;
		endNode.setColor(Node.NAVIGATION_COLOR);

	}
	private  void selectStarEndNode(Node nodeClicking){
		//set last clicked node defalut color;
		if(endNode != null){
			endNode.setColor(Node.DEFAULT_COLOR);
		}
		if(clickedNode != null){
			clickedNode.setColor(Node.DEFAULT_COLOR);
			clickedNode = null;
		}
		// now, the current clicked node becomes the end node, highlight the end node to navigation color
		endNode = nodeClicking;
		endNode.setColor(Node.NAVIGATION_COLOR);

	}
//add a abstract "onScoll" method in GUI class which is similar to "onlick" method
	// NOTICE: MOSEHWELLEVENT  NOT MOSEEVENT
	@Override
	protected void onScroll(MouseWheelEvent event){
		//negative values if the mouse wheel was rotated up/away from the user,
		// and positive values if the mouse wheel was rotated down/ towards the user
			if(event.getWheelRotation()>0)
				onMove(Move.ZOOM_OUT);
			else
				onMove(Move.ZOOM_IN);
	}




	@Override
	protected void redraw(Graphics g) {
		graph.redraw(g,currentOrigin,currentScale,getDrawingAreaDimension());

	}

	@Override
	protected void onClick(MouseEvent e) {
	    currentOrigin = CENTREAKL;

        Point pointClicking = e.getPoint();   // changed Current Point location into a betterPoint base on  central of panel as origin
        Point betterPoint = new Point (pointClicking.x - (int) (getDrawingAreaDimension().getWidth()/2),
                pointClicking.y - (int) (getDrawingAreaDimension().getHeight()/2));

        Location locationClicking =Location.newFromPoint(betterPoint,currentOrigin,currentScale);
        Node nodeClicking = graph.findClosest(locationClicking);
        if(nodeClicking != null){
            this.clickdeNode(nodeClicking);
        }else{
            getTextOutputArea().setText(null);
        }
	}

	@Override
	protected void onSearch() {
		//getSearchBox() given by GUI class, which return a JTextFiled, then use JTextFiled call its method "getText()"
	 	String string = getSearchBox().getText();
	 	List<Road> roadsFound = graph.search(string);
	 	if(roadsFound == null || roadsFound.isEmpty()){
			return;
		}
		//if roads are already search before, then recovery selectedRoad back to initial color
		//literate roads in clickRoads and road segments of each road
		if(!clickedRoad.isEmpty()){
	 		for (Road road : clickedRoad){
	 			for(RoadSegment roadSegment:road.roadSegments){
	 				roadSegment.setColor(RoadSegment.DEFAULT_COLOUR);
				}
			}
		}
		//Otherwise, highlight clickedRoad
		clickedRoad = roadsFound;
		for (Road road: clickedRoad){
			for (RoadSegment roadSegment: road.roadSegments){
				roadSegment.setColor(RoadSegment.CLICKED_COLOUR);
			}
		}
		displayRoadInfo(clickedRoad);
	}
//similar to "displayNodeInfo" method
	private void displayRoadInfo(List<Road> clickedRoad) {
		String lineSeparator = System.lineSeparator();
		StringBuilder stringBuilder = new StringBuilder("Roads are found:"+ clickedRoad.size() + lineSeparator);
		for (Road road : clickedRoad){
			stringBuilder.append("RoadID: ")
					.append(road.roadId).append(", Road Name: ")
					.append(road.label).append(", City: ")
					.append(road.city).append(lineSeparator);
		}
		// getTextOutputArea() return--->JTextArea.setText
		getTextOutputArea().setText(stringBuilder.toString());
	}
//change currentOrigin and currentScale
	@Override
	protected void onMove(Move m) {
		Dimension dimension = getDrawingAreaDimension();
		switch (m){
			case NORTH:{
				currentOrigin = currentOrigin.moveBy(0, dimension.getHeight() / currentScale / 10);
				break;
			} case SOUTH:{
				currentOrigin = currentOrigin.moveBy(0, -(dimension.getHeight() / currentScale) / 10);
				break;
			} case WEST:{
				currentOrigin = currentOrigin.moveBy(-(dimension.getWidth() / currentScale)/FACTOR_MOVE,0);
				break;
			} case EAST:{
				currentOrigin = currentOrigin.moveBy(dimension.getWidth() / currentScale / 10, 0);
				break;
			} case ZOOM_IN:{
				if(currentScale<MAX_ZOOMING){
					currentScale *= ZOOM_COEFFICENT;
				}
				break;
			} case ZOOM_OUT:{
				if(currentScale>MIN_ZOOMING){
					currentScale /= ZOOM_COEFFICENT;
				}
				break;
			}  default: {
				System.out.println("Should input right enum value");

			}

		}

	}

	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons) {
		currentOrigin = CENTREAKL;
		currentScale = Math.max(getDrawingAreaDimension().getHeight(),getDrawingAreaDimension().getWidth())/55;
		//reset fields
		clickedRoad = new ArrayList<>();
		clickedNode = null;
		startNod = null;
		endNode = null;
		pathsBetweenStartEnd =new ArrayList<>();
		graph.onload(nodes,roads,segments,polygons);

	}

	/*
	* try to do the challenge.
	* */
	private void findPath(){
		//reset the previous navigate path to default color
		if(!pathsBetweenStartEnd.isEmpty()){
			for (RoadSegment roadSegment:pathsBetweenStartEnd){
				roadSegment.setColor(RoadSegment.DEFAULT_COLOUR);
			}
			pathsBetweenStartEnd.clear(); //clear history
		}
		//pathsBetweenStartEnd=something ..yes it should equal something, but I have not figured out
	}

}
