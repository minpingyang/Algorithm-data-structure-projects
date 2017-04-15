package code.comp261.ass1;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Graph class do two things: load files and draw stuff.
 *
 * include the heaps of onLoad methods corresponding to different files(roade, node, polygon, etc)
 * do the redraw method as well.
 *
 * @author Frederick
 * **/

public class Graph {
    //use map collection to store nodes and roads by indexing their ID number
    //use Set for segment and polygon, since the order of segments and polygons does not matter. Set can prevent duplicates data
    //higher efficiency compare to List
    Map<Integer, Node> nodeMap;  // used for searching closest node by its node
    Map<Integer, Road> roadMap;
    Set<RoadSegment> roadSegmentSet;
    Set<Polygon> polygonSet;
    RoadTrie roadTries; // Tries structure of adding information of a road
    /**
     * constructor
     **/
    public Graph() {
        //initialise filed
        nodeMap = new HashMap<>();
        roadMap = new HashMap<>();
        polygonSet = new HashSet<>();
        roadSegmentSet = new HashSet<>();
        roadTries = new RoadTrie();
    }

    /**
     * a wrapper method for different small onload methods
     ***/
    public void onload(File nodeFile, File roadFile, File segmentFile, File polygonFile) {
        //initialise all field before we use those filed in load methods
        nodeMap = new HashMap<>();
        roadMap = new HashMap<>();
        polygonSet = new HashSet<>();
        roadSegmentSet = new HashSet<>();

        loadNodes(nodeFile);
        loadRoads(roadFile);
        loadRoadSegments(segmentFile);
        //small data fold probably does not exist polygon file. So should check if it exist first
        if (polygonFile == null) return;
        loadPolygons(polygonFile);
    }

    /**
     * using bufferReader to read the file line by line
     * this loadnode method is just used to get one line of information of the Node,
     * then put the node constructor to deal with
     ***/
    private void loadNodes(File nodeFile) {

        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(nodeFile));
            String line = bufferedReader.readLine();
            while (line != null) {
                //pass the whole line information of the node to the node object, which constructor can sort the information properly
                Node node = new Node(line);
                //node is indexed by its ID
                nodeMap.put(node.nodeId, node);
                line = bufferedReader.readLine(); // next line for next literation
                // System.out.print("load node run: "+(nodeRun++));
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(nodeFile + "File has not been found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    /**
     * using bufferReader to read the file line by line
     * this loadRoad method is just used to get one line of information of Road,
     * then put the Road constructor to deal with
     ***/
    private void loadRoads(File roads) {
        // int roadRun=0;
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(roads));
            bufferedReader.readLine(); //skip first title line
            String line = bufferedReader.readLine();
            while (line != null) {
                Road road = new Road(line);
                //Road is indexed by its ID
                roadMap.put(road.roadId, road);
                roadTries.add(road.label + ", " + road.city, road);   //add the information of the road by tries structure
                //roadTrie.add(road.label + ","+ road.city,road);
                line = bufferedReader.readLine(); // next line
                // System.out.println("loadRoad method run: "+(roadRun++));
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(roads + "File has not been found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }

    }

    /**
     * using bufferReader to read the file line by line
     * Due to the information is chunk by chunk instead of line by line in the RoadSegments file,
     * this load method will sort every information properly first, then put values of temporary variables into
     * RoadSegments constructor
     ***/
    private void loadRoadSegments(File segments) {
        //int segmentRun=0;
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(segments));
            bufferedReader.readLine(); // skip the first line
            String line = bufferedReader.readLine();

            while (line != null) {
                RoadSegment segment = new RoadSegment(line, nodeMap, roadMap);
                roadSegmentSet.add(segment);
                //roadSegments---->node
                segment.startNode.linkedSegments.add(segment);
                segment.endNode.linkedSegments.add(segment);
                //node is indexed by its ID
                //roadsegements --->> road
                roadMap.get(segment.roadId).roadSegments.add(segment);
                line = bufferedReader.readLine(); // next line
                // System.out.println("segmentRun: "+(segmentRun++));
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(segments + "File has not been found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    /**
     * using bufferReader to read the file line by line
     * Due to the information is chunk by chunk instead of line by line in the RoadSegments file,
     * this load method will sort every information properly first, then put values of temporary variables into
     * Polygon constructor
     ***/
    private void loadPolygons(File polygons) {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(polygons));
            String line = bufferedReader.readLine();
            //outer loop is used to read multiple polygons
            while (line != null) {
                if (line.equals("[POLYGON]")) {
                    String label = null;
                    Integer type = null;
                    Integer endLevel = null;
                    Integer cityIdx = null;
                    //store locations of alll points of polygons
                    Set<List<Location>> locations = new HashSet<>();
                    line = bufferedReader.readLine();
                    // inner loop for read data within a polygon
                    while (!line.equals("[END]")) {
                        if (line.startsWith("Type")) {
                            //cover the hexdecimal integer which is after "type=0x" into decimal
                            //NumberFormatException
                            type = Integer.parseInt(line.substring(7), 16);
                        } //use subString() to only get the number of each variable;
                        else if (line.startsWith("Label")) {
                            label = line.substring(6);
                        } else if (line.startsWith("EndLevel")) {
                            endLevel = Integer.parseInt(line.substring(9));
                        } else if (line.startsWith("CityIdx")) {
                            cityIdx = Integer.parseInt(line.substring(8));
                        } else if (line.startsWith("Data")) {
                            // only get coordinators of the file without comma & brackets
                            String[] coordinators = line.substring(6).replace("(", "").replace(")", "").split(",");
                            List<Location> list = new ArrayList<>();
                            for (int i = 0; i < coordinators.length; ) {
                                double lat = Double.parseDouble(coordinators[i++]);
                                double lon = Double.parseDouble(coordinators[i++]);
                                list.add(Location.newFromLatLon(lat, lon));
                            }
                            locations.add(list);
                        }
                        line = bufferedReader.readLine();  // go to the next line within one polygon data
                    }
                    polygonSet.add(new Polygon(type, label, endLevel, cityIdx, locations));
                    bufferedReader.readLine();    // skip the empty line between each polygon;
                    line = bufferedReader.readLine();
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(polygons + "File has not been found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }

    }

    /***
     * The redraw method is used by MainMap class
     * Meantime. it calls draw methods of polygon, RoadSegment, node orderly,
     * Since, draw big stuff first, which prevent covering the drawing of small stuff
     * *******/
    public void redraw(Graphics graphics, Location currentOrigin, double currentScale, Dimension dimension) {

        //redraw  road segments
        //redraw all nodes

        //redraw should orderly, otherwise, polygons will cover roads and nodes
        if (!polygonSet.isEmpty()) {
            for (Polygon polygon : polygonSet) {
                polygon.draw(graphics, currentOrigin, currentScale, dimension);
            }
        }

        for (RoadSegment segment : roadSegmentSet) {
            segment.draw(graphics, currentOrigin, currentScale, dimension);
        }

        for (Node node : nodeMap.values()) {
            node.draw(graphics, currentOrigin, currentScale, dimension);
        }

        graphics.setColor(Color.RED);
        graphics.fillOval((int)currentOrigin.x,(int)currentOrigin.y,20,20);
        System.out.println("origin x:  "+ (int)currentOrigin.x + "origin y: "+ (int)currentOrigin.y);
    }

    /**
     * This method is used to find the closest node of the passing parameter location
     * It is used by onClick method of MainMap class
     **/
    public Node findClosestNode(Location location) {
        //initialisation
        double shortestPath = Double.MAX_VALUE;
        Node closestIntersection = null;

        for (Node node : nodeMap.values()) {
            double distance = location.distance(node.location);
            if (distance < shortestPath) {
                shortestPath = distance;
                closestIntersection = node;
            }
        }
        // if closestIntersection exist, then return the intersection node
        // to be used for highlight, and it can be clicked
        if (closestIntersection != null && location.distance(closestIntersection.location) < 0.2 )
            return closestIntersection;
        else
            return null;
        //*******figure out quard-tree version later
    }

    /***
     * store user input, and search matching roads with same prefix by tries data structure
     * return list of Roads which are matching roads with same prefix
     * ****/

    public List<Road> search(String input) {
        return roadTries.findMatchingRoads(input);
    }

    public Set<Node> findArticulationPoints() {
        return ArticulationNode.findArticulationPoints(this.nodeMap.values());
    }

/*
    public Node findClosetNode(){

    }
    */
}
