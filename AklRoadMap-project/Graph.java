package code.comp261.ass1;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/*
* Graph class will load files, store information into
* graph(data structure needed),Display (part of graph
* in the drawing area), Redraw the graph after movement(left,right, zoom in/out)
* @author Minping
* */
public class Graph {
    Map<Integer,Node> nodeMap;
    Map<Integer,Road> roadMap;
    Set<Polygon> polygonSet;
    Set<RoadSegment> segmentSet;

    RoadTrie roadTrie;
    /// not too sure~
    public static final double CLICKED_RANGE = 0.2;

    public Graph(){
        nodeMap = new HashMap<>();
        roadMap = new HashMap<>();
        polygonSet = new HashSet<>();
        roadTrie = new RoadTrie();
    }
    /**
     * wrapper method for loading different files
     * @param , roads, node,segments, polygons, which refer to their files
     * */
    public void load(File nodes, File roads, File segments, File polygons){
        // initialisation
        nodeMap = new HashMap<>();
        roadMap = new HashMap<>();
        polygonSet = new HashSet<>();
        roadTrie = new RoadTrie();


        loadNodes(nodes);
        loadRoads(roads);
        loadSegments(segments);
        //for security, since the user may click the small data folder
        if(polygons== null) return;
        loadPolygons(polygons);
    }

    private void loadNodes(File nodes) {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(nodes));
            String line = bufferedReader.readLine();
            while (line!=null){
                Node node =new Node(line);
                //node is indexed by its ID
                nodeMap.put(node.nodeId,node);
                line = bufferedReader.readLine(); // next line
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(nodes+ "File has not been found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }
    /*very similar to loadNode method
    * */
    private void loadRoads(File roads) {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(roads));
            bufferedReader.readLine(); //skip first title line
            String line = bufferedReader.readLine();
            while (line!=null){
                Road road =new Road(line);
                //Road is indexed by its ID
                roadMap.put(road.roadId,road);
                line = bufferedReader.readLine(); // next line
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(roads+ "File has not been found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }

    }
    private void loadSegments(File segments) {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(segments));
            bufferedReader.readLine(); // skip the first line
            String line = bufferedReader.readLine();
            while (line!=null){
                RoadSegment segment =new RoadSegment(line, nodeMap, roadMap);
                segmentSet.add(segment);
                //node is indexed by its ID
                roadMap.get(segment.roadId).roadSegments.add(segment);
                line = bufferedReader.readLine(); // next line
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(segments+ "File has not been found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }
    private void loadPolygons(File polygons) {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(polygons));
            String line =bufferedReader.readLine();
            //outer loop is used to read multiple polygons
            while (line!=null){
                if (line.equals("[POLYGON]")){
                    String label = null;
                    Integer type =null;
                    Integer endLevel = null;
                    Integer cityIdx = null;
                    //store locations of alll points of polygons
                    Set<List<Location>> locations = new HashSet<>();
                    line = bufferedReader.readLine();
                    // inner loop for read data within a polygon
                    while (!line.equals("[END]")){
                        if(line.startsWith("Type")){
                            //cover the hexdecimal integer which is after "type=0x" into decimal
                            type = Integer.parseInt(line.substring(7),16);
                        } //use subString() to only get the number of each variable;
                        else if(line.startsWith("Label")){
                            label = line.substring(6);
                        }else if(line.startsWith("EndLevel")){
                            endLevel = Integer.parseInt(line.substring(8));
                        }else if(line.startsWith("Data")){
                            // only get coordinators of the file without comma & brackets
                            String[] coordinators = line.substring(6).replace("(","").replace(")","").split(",");
                            ArrayList<Location> list = new ArrayList<>();
                            for (int i = 0; i < coordinators.length;){
                                double lat = Double.parseDouble(coordinators[i++]);
                                double lon = Double.parseDouble(coordinators[i++]);
                                list.add(Location.newFromLatLon(lat,lon));
                            }
                            locations.add(list);
                        }
                        line =bufferedReader.readLine();  // go to the next line within one polygon data
                    }
                 polygonSet.add(new Polygon(type,label,endLevel,cityIdx,locations));
                 bufferedReader.readLine();    // skip the empty line between each polygon;
                 line = bufferedReader.readLine();
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(polygons+ "File has not been found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }

    }
    //redraw method which is used after movement of map
    public void redraw(Graphics graphics, Location currentOrigin, double currentScale, Dimension dimension){
       //redraw all nodes
        for (Node node:nodeMap.values()){
            node.draw(graphics,currentOrigin,currentScale,dimension);
        }
        //redraw  road segments
        for (RoadSegment segment: segmentSet){
           segment.draw(graphics,currentOrigin,currentScale,dimension);
       }


        if(!polygonSet.isEmpty()){
            for (Polygon polygon: polygonSet){
                polygon.draw(graphics,currentOrigin,currentScale,dimension);
            }
        }
    }
    //find intersections by linear search
    //@param  location represents the location clicked
    public Node findNode(Location location){
        //initialisation
        double shortestPath = Double.MAX_VALUE;
        Node closestIntersection = null;
        for (Node node: nodeMap.values()){
            double distance = location.distance(node.location);
            if(distance<shortestPath){
                shortestPath = distance;
                closestIntersection = node;
            }
        }
        // if closestIntersection exist, then return the intersection node
        // to be used for highlight, and it can be clicked
        if(location.distance(closestIntersection.location)>CLICKED_RANGE||closestIntersection==null)
            return null;
        else
            return closestIntersection;

        //*******figure out quard-tree version later
    }
}
//store user input, and search matching roads with same prefix by tries data structure
public List<Road> search(String input){
    return roadTrie.find(input);
}

