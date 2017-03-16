package code.comp261.ass1;

import javax.swing.text.Segment;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
* Graph class means the graph of linking nodes and roads
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
            System.out.print("IO Exception");
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
                nodeMap.put(road.roadId,road);
                line = bufferedReader.readLine(); // next line
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(roads+ "File has not been found");
        } catch (IOException e) {
            System.out.print("IO Exception");
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
                
                //node is indexed by its ID
                nodeMap.put(node.nodeId,node);
                line = bufferedReader.readLine(); // next line
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(nodes+ "File has not been found");
        } catch (IOException e) {
            System.out.print("IO Exception");
        }
    }
    private void loadPolygons(File polygons) {
    }

}
