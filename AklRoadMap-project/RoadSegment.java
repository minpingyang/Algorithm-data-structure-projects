package code.comp261.ass1;

import java.awt.Color;
import java.awt.List;
import java.util.ArrayList;
import java.util.Map;

public class RoadSegment {
	public final int raodId;  // which road contain the road segment
	public Node startNode, enNode;  // ends of the segment
	public final Road road;
	public final double lengthOfSegment;
	public final ArrayList<Location> coordsOfNodesOnTheSegment;
	private Color color;
	//same as node color
	public static final Color DEFAULT_COLOUR = new Color(130, 130, 130);
    public static final Color SELECT_COLOUR = new Color(255, 192, 0);
    public static final Color NAVI_COLOUR = new Color(255, 0, 0);
	/**
	 * constructor
	 * */
    public RoadSegment(String data, Map<Integer, Node> nodeMap, Map<Integer, Road>roadMap) {
		coordsOfNodesOnTheSegment = new ArrayList<>();
		
	}
	
}
/*
 * return the neighbor node which is linked with node which call this function by the road segments
 * */

public Node neighborNode(Node node){
	//later
	return node2;
}