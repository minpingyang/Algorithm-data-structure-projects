package code.comp261.ass1;
/*
 * Node class represents node of the graph
 * @author MinPing
 * */

import java.awt.*;



public class Node {
	public final int nodeId;
	public final Location location;

	private Color color;
	/*default color: blue;
	 * selected corlor: orange;
	 * navigation: red
	 * */
	public static final Color DEFAULT_COLOR = new Color(83, 141, 213);
	public static final Color SELECTED_COLOR = new Color(255, 192, 0);
	public static final Color NAVIGATION_COLOR = new Color(255, 0, 0);

	/*two constant for adjusting shape of node during the process of zooming
	 * */
	public static final int NODE_WIDTH = 1;
	public static final double NODE_LEAN = 0.7;
	/*constructor
	 * @parma string:  the data which is loaded from files
	 * */
	public Node(String string) {
		String[] data = string.split("\t");
		nodeId = Integer.parseInt(data[0]);
		double lat = Double.parseDouble(data[1]);
		double lon = Double.parseDouble(data[2]);
		// use the newFromLation function defined in location class to transfer lat&lone --> Location Object
		location = Location.newFromLatLon(lat, lon);
		color = DEFAULT_COLOR;  //Initialise color

			
	}
	public void draw(Graphics graphics,Location currentOrigin, double currentScale, Dimension dimension){
		//coordinators of centre of panel
		int centrXofPanel = (int) (dimension.getWidth() / 2);
		int centrYofPanel = (int) (dimension.getHeight() / 2);
		// transfer Locations into Points
		Point point = location.asPoint(currentOrigin,currentScale);
		// changed Points after zooming
		Point changedPoint =new Point(point.x + centrXofPanel, point.y + centrYofPanel);
		//prevent changedPoints out of screen
		if(changedPoint.x<0 || changedPoint.x> dimension.width || changedPoint.y <0 || changedPoint.y >dimension.height)
			return;
		int size = (int)(Math.log(currentScale)*NODE_LEAN + NODE_WIDTH);
		graphics.setColor(color);
		graphics.fillOval(changedPoint.x - size/2, changedPoint.y-size/2,size,size);
	}
	//override java documentation toString method
	@Override
	public String toString() {

		return "Node [nodeId =" + nodeId;
		
	}
	
	/*color setter
	 * */
	public void setColor(Color color) {
		this.color = color;
	}
		
		
}
