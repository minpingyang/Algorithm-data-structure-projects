package code.comp261.ass1;

import java.awt.Color;
import java.awt.List;
import java.util.ArrayList;
import java.util.Map;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.w3c.dom.NamedNodeMap;

public class RoadSegment {
	public final int roadId;
	public Node start, end;
	public final Roads road;
	public final List<Location> locations;
	private Color color; //setter-getter later
	//set unique color to highlight different actions;
	public static final Color GENERAL_COLOUR = new Color(215, 226, 192); //green
	public static final Color SELECTED_COLOUR = new Color(25, 55, 114); //blue
	public static final Color NAVIGATION_COLOUR = new Color(186, 87, 16); //brown
	
	public RoadSegment (String line, Map<Integer, Node> nodeMap. Map<Integer, Roads> roadMap ){
		this.locations = new ArrayList<>();
		String[] values = line.split("\t");
		this.roadId = Integer.parseInt(values[0]);
		this.road = roadMap.get(roadId);
		start = nodeMap.get(Integer.parseInt(values[2]));
		end = nodeMap.get(Integer.parseInt(values[3]));
		for (int i =4; i < values.length; i++){
			double lat =Double.parseDouble(values[i]);
			double lon =Double.parseDouble(values[i]);
			locations.add(Location.newFromLatLon(lat, lon));
		}
		locations.add(end.location);
		 color = GENERAL_COLOUR;
	}
    public void draw()
	
}
