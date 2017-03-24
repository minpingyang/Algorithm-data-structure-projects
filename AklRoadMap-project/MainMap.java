package code.comp261.ass1;


import java.awt.*;
import java.awt.event.MouseEvent;

import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * MainMap is the main class of the Java app
 *
 * This class will extend GUI class, and implement the GUI class methods.
 * In addition, use all of the object I created to achieve those implementing methods.
 *
 *
 * @author Minping
 * */

public class MainMap extends GUI{
    //declare all filed and instance of the Mainmap class

    //the lat-lon constants for the centre of Auckland, which references from "Location" class
    private static final double CENTRE_LAT = -36.847622;
    private static final double CENTRE_LON = 174.763444;

    //transfer lat-lon of AKL centre into "location" object
    private static  final Location CENTREAKL= Location.newFromLatLon(CENTRE_LAT,CENTRE_LON);

    //constants for zooming function. those constants could be changed into a better one tho.
    private  static final double MIN_ZOOMING = 6, MAX_ZOOMING = 780;
    private  static final double ZOOM_COEFFICENT = 1.5;
    // the constant factor of moving
    private  static final double FACTOR_MOVE = 12.3;

    //the node object will be clicked by mouse
    private  Node clickedNode;
    private List<Road> searchedRoads; // the roads which the clickNode belong to, probably more than one.
    //declare currentOrigin and scale, they would be changed after movement(left right zooming)
    private  Location currentOrigin;
    private  double currentScale;
    //Graph object is used to implement "onload" method and "drawing" method.
    Graph graph;
    private Location currentDragPoint;
    private Point betterDragEndPoint;

    /**main method**/
    public static void main(String[] args) {
        new MainMap();
    }
    /**constructor**/
    public MainMap(){
        //initialise filed
        graph = new Graph();
        searchedRoads = new ArrayList<>();
        clickedNode = null;
        //set initial origin to Auckland centre which is a location object;
        currentOrigin = CENTREAKL;
        //the initial scale is related on current display panel. 55 is a good constant factor by testing.
        currentScale = Math.max(getDrawingAreaDimension().getHeight(),getDrawingAreaDimension().getWidth())/55;
    }

    /**
     * core part; which is read the files of the chosen folder
     * Meantime, intialise all the filed of the class.(which is pretty much similar to constructor)
     * In particularly, call the onload method of graph passing all the files
     * **/
    @Override
    protected void onLoad(File nodes, File roads, File segments, File polygons) {
        currentOrigin = CENTREAKL;  //coordinator lat-lon data based on AucklandCentre of origin.
        currentScale = Math.max(getDrawingAreaDimension().getHeight(),getDrawingAreaDimension().getWidth())/55;
        //reset fields
        searchedRoads = new ArrayList<>();
        clickedNode = null;
        graph.onload(nodes,roads,segments,polygons);

    }
    /**
     * the redraw method will be called after everytimme movement or clicking, ect. which is used in GUI class .
     * **/
    @Override
    protected void redraw(Graphics g) {
        graph.redraw(g,currentOrigin,currentScale,getDrawingAreaDimension());

    }
    /***
     * This method does 2 things: moving and zooming by changing currentOrigin and currentScale respectively
     * when the origin moved, the value dived by currentScale, which is used to cancel the effect of different scale
     * ****/

    @Override
    protected void onMove(Move m) {
        Dimension dimension = getDrawingAreaDimension();
        switch (m){
            case NORTH:{
                currentOrigin = currentOrigin.moveBy(0, dimension.getHeight() / currentScale / FACTOR_MOVE);
                break;
            } case SOUTH:{
                currentOrigin = currentOrigin.moveBy(0, -(dimension.getHeight() / currentScale) / FACTOR_MOVE);
                break;
            } case WEST:{
                currentOrigin = currentOrigin.moveBy(-(dimension.getWidth() / currentScale)/FACTOR_MOVE,0);
                break;
            } case EAST:{
                currentOrigin = currentOrigin.moveBy(dimension.getWidth() / currentScale / FACTOR_MOVE, 0);
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



    /**
     * allow user clicking the node
     * find the point of clicking based on center of display panel as origin
     * find the location of the point
     * use findClosestNode method, find the nearest node by pass location of clicking point
     * highlight the node and show its information
     *
     * */
    @Override
    protected void onClick(MouseEvent e) {
        currentDragPoint = null;
        betterDragEndPoint = null;
        Point pointClicking = e.getPoint();

        // create the changed points of polygon base on centre of display panel as origin, not center of AKL

        //changing the coordinator of the clicking point into a better point by decreasing half length
        //of the display panel size.
        //The reason is the points of nodes is based on center of display panel. In order to make clicking point
        //corresponding to the coordinator of drawing node, use clickpoint.x - half horizontal size of panel
        //clickpoint.y as well.
       Point betterPoint = new Point (pointClicking.x - (int) (getDrawingAreaDimension().getWidth()/2), pointClicking.y -  (int) (getDrawingAreaDimension().getHeight()/2));
       Location locationClicking =Location.newFromPoint(betterPoint,currentOrigin,currentScale);
        Node nodeClicking = graph.findClosestNode(locationClicking);
        if(nodeClicking != null){
            //reset the previous node color to default color before user clicked a new node every time
            if(clickedNode!=null)
                clickedNode.setColor(Node.DEFAULT_COLOR);
            clickedNode = nodeClicking;
            clickedNode.setColor(Node.CLICKED_COLOR);

            /**
             * using stringBuilder to create a string object to edit the information of node, finally, which is used to pass to SetText() method
             * lineSeparator is used to format the information
             * linkedSegment field is used to find all segments of the roads the node belong to
             * **/

            int count = 1; //count how many roads the node belong to, and list them out in the TextOutArea
            // line Separator is =======> "\n" new line in C
            String lineSeparator = System.lineSeparator();
            //basic information of the clicked node
            StringBuilder stringBuilder = new StringBuilder("Click on Node which ID is: "+clickedNode.nodeId
                    + lineSeparator + "Roads the intersection belong to: " + lineSeparator);
            //information of information of the road which the node belong to
            //different roadSegment probably belong to same road, due to this case,
            // if statement is used to avoid duplication road information

            Set<String> infoSet =new HashSet<>();
            for(RoadSegment roadSegment: clickedNode.linkedSegments){
                String string = roadSegment.road.label +","+ roadSegment.road.city + ", road ID: "+roadSegment.roadId;
                if(!infoSet.contains(string))    //avoid duplicate information
                    infoSet.add(string);
            }
            //format information
            for (String string:infoSet){
                stringBuilder.append("( ").append(count++).append(" )").append(string).append(lineSeparator);
            }
            getTextOutputArea().setText(stringBuilder.toString());
        }else{
            getTextOutputArea().setText(null);
        }
    }

    @Override
    protected void onDrag(MouseEvent e) {


        if (currentDragPoint == null) {
            currentDragPoint = new Location(e.getX(), e.getY());
            return;
        }

        double xOff = currentDragPoint.x - e.getX();
        double yOff = e.getY() - currentDragPoint.y;

        currentOrigin = currentOrigin.moveBy(xOff / currentScale, yOff / currentScale);
        System.out.println(currentOrigin.toString());
        System.out.printf("%f, %f\n", xOff, yOff);

        currentDragPoint = new Location(e.getX(), e.getY());


//        if(currentDragPoint == null){
//            Point betterDrageStartPoint = new Point (e.getPoint().x - (int) (getDrawingAreaDimension().getWidth()/2), e.getPoint().y -  (int) (getDrawingAreaDimension().getHeight()/2));
//            currentDragPoint = betterDrageStartPoint;
//            return;
//        }
//        betterDragEndPoint = new Point (e.getPoint().x - (int) (getDrawingAreaDimension().getWidth()/2), e.getPoint().y -  (int) (getDrawingAreaDimension().getHeight()/2));
//
//        double releasedPointX = betterDragEndPoint.getX();
//        double releasedPointY = betterDragEndPoint.getY();
//        double movingDistanceX = -releasedPointX + currentDragPoint.getX();
//        double movingDistanceY = releasedPointY - currentDragPoint.getY();
//        currentDragPoint = null;
//        betterDragEndPoint = null;
//        currentOrigin = currentOrigin.moveBy(movingDistanceX/currentScale/2,movingDistanceY/currentScale/2);

    }


    /**
     * get the String contained in the search box which is what the user type in
     * use
     * Highlight matching roads into searchedColor
     * recover previous matching roads into default color
     *
     * ***/

    @Override
    protected void onSearch() {
       //get the String contained in the search box which is what the user type in
        String string = getSearchBox().getText();
        //System.out.println("typed in: "+string);
        List<Road> foundRoads = graph.search(string);
        if(foundRoads == null || foundRoads.isEmpty()){
            System.out.println("Roads were not found");
            return;
        }
        //if roads are already search before, then recover selectedRoad back to default color
        //literate roads in clickRoads and road segments of each road
        if(!searchedRoads.isEmpty()){
            for (Road road : searchedRoads){
                for(RoadSegment roadSegment:road.roadSegments){
                    roadSegment.setColor(RoadSegment.DEFAULT_COLOUR);
                }
            }
        }
        //Otherwise, highlight clickedRoad
        searchedRoads = foundRoads;
        for (Road r: searchedRoads){
            System.out.println("Clicked Road: "+r.label);
        }

        for (Road road: searchedRoads){
            for (RoadSegment roadSegment: road.roadSegments){
                roadSegment.setColor(RoadSegment.SEARCHED_COLOUR);
            }
        }
        /**
         * using string builder to store the information of matching roads
         * Output the content of string builder into TextOutPut area
         * **/
        String lineSeparator = System.lineSeparator();
        StringBuilder stringBuilder = new StringBuilder("Roads are found:"+ searchedRoads.size() + lineSeparator);
        for (Road road : searchedRoads){
            stringBuilder.append("RoadID: ")
                    .append(road.roadId).append(", Road Name: ")
                    .append(road.label).append(", City: ")
                    .append(road.city).append(lineSeparator);
        }

        getTextOutputArea().setText(stringBuilder.toString());
    }

/**
 * allow user zooming in and zooming our by using scroll of mouse
 * run scroll forward ---> zooming in, backward--->zooming our
 *
 * */
    @Override
    protected void onScroll(MouseWheelEvent event) {
        if(event.getWheelRotation()>0)
            onMove(Move.ZOOM_OUT);
        else
            onMove(Move.ZOOM_IN);
    }

}