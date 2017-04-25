package code.comp261.ass1;


import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Node {

	public static final int MAX_COUNT = Integer.MAX_VALUE ;
	public final int nodeId;
	//node file provide the lat-lon data of each node, but we need the transfer the lat-lon data into Location object
	public final Location location;

	//color is used for highlight the clicked nodes, and unclicked nodes
	private Color color;
	public static final Color DEFAULT_COLOR = new Color(104, 145, 213);
	public static final Color CLICKED_COLOR = new Color(255, 15, 254);
	public static final Color NAVIGA_COLOR = new Color(211, 11, 26);
	public static final Color ARTICULATION_COLOR = new Color(254, 255, 0);

	// if nodes are linked with a node, then they are the neighbours of the node.
	//linkedSegments is used to find all the segments of a node which is clicked.
	//use Set data structure for linkedSegments, which is also a good way to avoid duplicates of segments.

	public final Set<RoadSegment> linkedSegments;  // it is used for storing all of the segment of a node belong to

	//two constant for adjusting shape and size of node during the process of zooming
	public static final int NODE_WIDTH = 1;
	public static final double NODE_LEAN = 0.6;
	public Set<Node> neighbourNodeSet;
	public int count;

	/***constructor
	 * @param  string is a line of information of the node, including nodeID, lat-lon data.
	 * r****/
	public Node(String string) {
		String[] data = string.split("\t");
		nodeId = Integer.parseInt(data[0]);
		double lat = Double.parseDouble(data[1]);
		double lon = Double.parseDouble(data[2]);
		// transfer lat-lone --> Location Object
		location = Location.newFromLatLon(lat, lon);
		color = DEFAULT_COLOR;  //Initialise color
		linkedSegments = new HashSet<>();
		neighbourNodeSet = new HashSet<>();

		this.count = MAX_COUNT;


	}
    /***
     * draw the road segments based on center of display panel
     * asPoint method has already concerned about scale changing
     * but the size of node (Oval) will be changed after zooming
     * use graphics class to fillOvall and set Color
     * ******/

    public void draw(Graphics graphics,Location currentOrigin, double currentScale, Dimension dimension){
        //coordinators of centre of panel
        int centrXofPanel = (int) (dimension.getWidth() / 2);
        int centrYofPanel = (int) (dimension.getHeight() / 2);
        // transfer Locations into Points
        Point point = location.asPoint(currentOrigin,currentScale);
        // create the changed points of polygon base on centre of display panel as origin, not left-top original
        Point changedPoint =new Point(point.x + centrXofPanel, point.y + centrYofPanel);
        //prevent changedPoints out of screen
        if(changedPoint.x<0 || changedPoint.x> dimension.width || changedPoint.y <0 || changedPoint.y >dimension.height)
            return;
        int size = (int)(Math.log(currentScale)*NODE_LEAN + NODE_WIDTH);
        graphics.setColor(color);
        graphics.fillOval(changedPoint.x - size/2, changedPoint.y-size/2,size,size);
    }
    /**used to highlight node when the node is clicked*/
    public void setColor(Color color) {
        this.color = color;
    }

    public void setNeighbours(){
    	for(RoadSegment segment: this.linkedSegments){
    		Node endNodeOfSegment =segment.getEndNode(this);
			neighbourNodeSet.add(endNodeOfSegment);

		}
	}


}