package code.comp261.ass1;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class RoadSegment {
	public final int roadId;  // which road contain the road segment
	public Node startNode, endNode;  // ends of the segment
	public final Road road;
	public final double lengthOfSegment;
	public final ArrayList<Location> coordsOfNodesOnTheSegment;
	private Color color;
	//same as color constant of Node class
	public static final Color DEFAULT_COLOUR = new Color(77, 127, 130);
    public static final Color CLICKED_COLOUR = new Color(255, 159, 7);
    public static final Color NAVI_COLOUR = new Color(255, 23, 6);
	/**Í
	 * constructor
	 * nodes and roads both are indexed by their Id
	 * */
    public RoadSegment(String data, Map<Integer, Node> nodeMap, Map<Integer, Road>roadMap) {
		coordsOfNodesOnTheSegment = new ArrayList<>();
		String[] values = data.split("\t");
		roadId = Integer.parseInt(values[0]);
		road = roadMap.get(roadId);
		lengthOfSegment = Double.parseDouble(values[1]);
		startNode = nodeMap.get(Integer.parseInt(values[2]));
		//the location of start node is added as first element in the collection
		coordsOfNodesOnTheSegment.add(startNode.location);
		endNode = nodeMap.get(Integer.parseInt(values[3]));
		//add all coordinates into the ArrayList
		for (int i = 4; i < values.length;) {
			double lat = Double.parseDouble(values[i++]);
			double lon = Double .parseDouble(values[i++]);
			coordsOfNodesOnTheSegment.add(Location.newFromLatLon(lat, lon));
		}
		coordsOfNodesOnTheSegment.add(endNode.location);
		color = DEFAULT_COLOUR;		
	}
    /*color setter
     * */
     public void setColor(Color color){
    	 this.color = color;
     }
     /*draw() -------> draw the segment
      * 
      * */
     public void draw(Graphics graphics, Location currentOrigin, double currentScale, Dimension dimension){
    	 // coordinates of the centre of panel by using dimension class
    	 int centrXofPanel = (int)(dimension.getWidth());
    	 int centrYofPanel = (int)(dimension.getHeight());
    	 graphics.setColor(color);
    	 // last one is i+1, so loop condition should be < size-1
    	 for (int i = 0; i < coordsOfNodesOnTheSegment.size()-1; i++) {
			// Transfer all the coordinate of the Node on the segment ------> Point object ----> easy to draw
    		 // Notice: the position of Points are changed after zooming in or zooming out either
    		 //we need to change the location the a pair of nodes which are linked with same segments at same time
    		 // to ensure the location of point changing with segment length changing after zooming in or zoom out
    		 Point initialPoint = coordsOfNodesOnTheSegment.get(i).asPoint(currentOrigin, currentScale);
    		 Point nextPoint = coordsOfNodesOnTheSegment.get(i+1).asPoint(currentOrigin, currentScale);
    		 
    		 Point changedInitialPoint = new Point(initialPoint.x + centrXofPanel, initialPoint.y+centrYofPanel);
    		 Point changeNextPoint = new Point(nextPoint.x+centrXofPanel, nextPoint.y+centrYofPanel);
    	 //for security, prevent from drawing will be out of screen

    		 if ((changedInitialPoint.x < 0 && changeNextPoint.x <0)
    			 ||(changedInitialPoint.x>dimension.getWidth()&& changeNextPoint.x > dimension.getWidth())
    			 ||(changedInitialPoint.y < 0 && changeNextPoint.x <0)
    			 ||(changedInitialPoint.x>dimension.getHeight()&& changeNextPoint.x > dimension.getHeight()))		 
    			 return;
    		 graphics.drawLine(changedInitialPoint.x,changedInitialPoint.y, changeNextPoint.x, changeNextPoint.y);	
    		
		}
     }
     
	public  Node otherNode(Node node){
     	if(node.nodeId != startNode.nodeId && node.nodeId!= endNode.nodeId)
     		return null;

     	if (node.nodeId == startNode.nodeId)
     		return node;
     	else
     		return startNode;
	}

    /*show information of road segments
     * */
    @Override
    public String toString(){
    	return "Segment [roadID ="+ roadId + ", startNode =" + startNode.nodeId + ", end node" + endNode.nodeId
    			+", Road =" + road.roadId + ", coordinates =" + coordsOfNodesOnTheSegment.toString() + "]";
    }
}
