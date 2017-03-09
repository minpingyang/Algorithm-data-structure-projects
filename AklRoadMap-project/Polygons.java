package code.comp261.ass1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

// Polygon class is used to make the road map beatiful

public class Polygons {
	public final String label;
	public final Integer type;
	public final Integer endLevel;
	public final Integer cityId;
	
	public final Set<ArrayList<Location>> location;
	private final Color color;
	
	public Polygons (Integer type, String label, Integer endLevel,Integer cityId, Set<ArrayList<Location>> location ){
		
		this.type = type;
		this.label = label;
		this.endLevel = endLevel ;
		this.cityId = cityId ;
		this.location = location;
		swith (this.type){
		case 0x07:
				
		}
	}
	
	
}
