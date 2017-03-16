package code.comp261.ass1;

import java.io.*;
import java.util.*;

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
    public static final double CLICKED_RANGE = 0.2;

    public Graph(){
        nodeMap = new HashMap<>();
        roadMap = new HashMap<>();
        polygonSet = new HashSet<>();
        roadTrie = new RoadTrie();
    }
    /**
     * wrapper method for loading different files
     * @param
     * @param
     * @param
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
            bufferedReader = new BufferedReader(new FileReader());
            String line =bufferedReader.readLine();
            while (line!=null){
                if (line.equals("[POLYGON]")){
                    String label = null;
                    Integer type =null;
                    Integer endLevel = null;
                    Integer cityIdx = null;
                    Set<List<Location>> locations = new HashSet<>();
                    line = bufferedReader.readLine();
                    while (!line.equals("[END]")){
                        if(line.startsWith("Type")){
                            //cover the hexdecimal integer which is after "type=0x" into decimal
                            type = Integer.parseInt(line.substring(7),16);

                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println(polygons+ "File has not been found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }

    }

}
