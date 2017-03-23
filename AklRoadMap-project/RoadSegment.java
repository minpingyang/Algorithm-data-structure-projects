package code.comp261.ass1;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class RoadSegment{
	//the road which the segments belong to
	public final Road road;
	//id of the road which the segments belong to
	public final int roadId;
	//two ends of the segments
	public Node startNode, endNode;
	//length of the segment
	public final double lengthOfSegment;
	//(x,y) the order of the coordinatorOfSegments is very important, so I use List instead of Set
	public final List<Location> coordsOfNodesOnTheSegment;
	//highlight segments when we type in on the search box the segment matching the same prefix
	private Color color;
	public static final Color DEFAULT_COLOUR = new Color(132, 130, 147);
	public static final Color SEARCHED_COLOUR = new Color(222, 3, 255);
	/**constructor
	 * This is used by loadSegment method of Graph class
	 * **/
	public RoadSegment(String data, Map<Integer, Node> nodeMap, Map<Integer, Road>roadMap){


		String[] values = data.split("\t");
		roadId = Integer.parseInt(values[0]);
		road = roadMap.get(roadId);
		lengthOfSegment = Double.parseDouble(values[1]);
		startNode = nodeMap.get(Integer.parseInt(values[2]));

		endNode = nodeMap.get(Integer.parseInt(values[3]));
		//initialise
		coordsOfNodesOnTheSegment = new ArrayList<>();
		//the location of start node is added as first element in the collection
		coordsOfNodesOnTheSegment.add(startNode.location);
		//add other coordinates into the ArrayList
		for (int i = 4; i < values.length;) {
			double lat = Double.parseDouble(values[i++]);
			double lon = Double .parseDouble(values[i++]);
			coordsOfNodesOnTheSegment.add(Location.newFromLatLon(lat, lon));
		}
		coordsOfNodesOnTheSegment.add(endNode.location);
		color = DEFAULT_COLOUR;
	}

	/***
	 * draw the road segments based on center of display panel
	 * use graphics class to drawLine and set Color
	 * draw the line between the two adjacent points
	 * asPoint method has already concerned about scale changing
	 * ******/

	public void draw(Graphics graphics, Location currentOrigin, double currentScale, Dimension dimension){
		// coordinates of the centre of panel by using dimension class
		int centrXofPanel = (int)(dimension.getWidth()/2);   //fixed !
		int centrYofPanel = (int)(dimension.getHeight()/2);
		graphics.setColor(color);
		// last one is i+1, so loop condition should be < size-1
		for (int i = 0; i < coordsOfNodesOnTheSegment.size()-1; i++) {

			Point initialPoint = coordsOfNodesOnTheSegment.get(i).asPoint(currentOrigin, currentScale);
			Point nextPoint = coordsOfNodesOnTheSegment.get(i+1).asPoint(currentOrigin, currentScale);
			// create the changed points of polygon base on centre of display panel as origin, not left-top original
			Point changedInitialPoint = new Point(initialPoint.x + centrXofPanel, initialPoint.y+centrYofPanel);
			Point changeNextPoint = new Point(nextPoint.x+centrXofPanel, nextPoint.y+centrYofPanel);
			//for security, prevent from drawing will be out of screen

			if ((changedInitialPoint.x < 0 && changeNextPoint.x <0)
					||(changedInitialPoint.x>dimension.getWidth()&& changeNextPoint.x > dimension.getWidth())
					||(changedInitialPoint.y < 0 && changeNextPoint.x <0)
					||(changedInitialPoint.y>dimension.getHeight()&& changeNextPoint.y > dimension.getHeight())) {//return;
				continue;  //directly step over next loop at this point    //fixed! x--> y for height
			}

			//System.out.println("draw segement"+(count++));
			graphics.drawLine(changedInitialPoint.x,changedInitialPoint.y, changeNextPoint.x, changeNextPoint.y);

		}
	}
	/**set segments color**/
	public void setColor(Color color){
		this.color = color;
	}

	/**
	 * just return the other end(/start) node of the segment
	 *
	 * */
	public  Node neighbourNode(Node node){
		if(node.nodeId != startNode.nodeId && node.nodeId!= endNode.nodeId)
			return null;

		if (node.nodeId == startNode.nodeId)
			return endNode;   // fixed~  return end node not node..
		else
			return startNode;
	}

}
