package code.comp261.ass1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.channels.FileLockInterruptionException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
	public static final double MAX_CLICKED_RANGE = 0.18;    // how far away of clicked location can be counted (0.18 JUST GUESS, PROBABLY CHANGE IT LATER
	// create collections to store node
	Map<Integer,Node> nodeMap;
	Map<Integer, Roads> roadMap;  //store roads;
	Set<RoadSegment> segments;   // store seg
	Set<Polygons> polygons;     // store polyg
	Set<Singularity> singularities;   // singularity points of the map
	RoadTrie roadTrie;
	public Graph(){
		nodeMap =new HashMap<>();
		roadMap = new HashMap<>();
		polygons = new HashSet<>();
		segments = new HashSet<>();
		singularities = new HashSet<>();
		roadTrie = new RoadTrie();
	}
	public void load(File nodeFile, File roadFile, File segmentFile, File polygonFile, File singularityFile){
		nodeMap = new HashMap<>();
		roadMap = new HashMap<>();
		polygons = new HashSet<>();
		segments = new HashSet<>();
		singularities = new HashSet<>();
		roadTrie = new RoadTrie();
		// declare corresponding function to load different data
		loadNodes(nodeFile);
		loadRoads(roadFile);
		loadSegments(segmentFile);
		
		if (polygonFile== null)  return;
		loadPolygons(polygonFile);
	    if (singularityFile==null)  return;
	    loadSingularity(singularityFile);		
	}
	private void loadSingularity(File singularityFile) {
		BufferedReader bufferedReader;
		try{
			bufferedReader = new BufferedReader(new FileReader(file));
			bufferedReader.readLine(); //ignore first line 
			String line =bufferedReader.readLine();
			while (line!= null ){
				Singularity singularity =new Singularity()
			}
		}
		
	}
	private void loadPolygons(File polygonFile) {
		// TODO Auto-generated method stub
		
	}
	private void loadSegments(File segmentFile) {
		// TODO Auto-generated method stub
		
	}
	private void loadRoads(File roadFile) {
		// TODO Auto-generated method stub
		
	}
	private void loadNodes(File nodeFile) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
