package code.comp261.ass1;

import java.util.HashSet;
import java.util.Set;

import javax.jws.Oneway;

public class Roads {
	public final int roadId, roadType, speedLimit;
	public final double roadClass; //no idea what is road class tho, guess bigger class number of the road, faster car can run 
	public final boolean isOneWay, notForCar, notForPedestrian, notForBike;
	public final String city, label;
	public final Set<RoadSegment> roadSegments;
	pulic Road(String line){
		String[] values = line.split("\t");
		roadId = Integer.parseInt(values[0]);
		roadType = Integer.parseInt(values[1]);
		label = values[2];
		city = values[3];
		int speed = Integer.parseInt(values[5]);
		isOneWay = Integer.parseInt(values[4]) == 1 ? true : false;
		notForCar =Integer.parseInt(values[7]) == 1 ? true : false;
		notForPedestrian = Integer.parseInt(values[8]) == 1 ? true : false;
		notForBike = Integer.parseInt(values[9]) == 1 ? true : false;
		roadSegments= new HashSet<>();
		// speed limit, for A* search
		switch (speed){
		case 0:
			speedLimit = 5;
			break;
		case 1:
			speedLimit = 20;
			break;
		case 2:
			speedLimit = 40;
			break;
		case 3:
			speedLimit = 60;
			break;
		case 4:
			speedLimit = 80;
			break;
		case 5:
			speedLimit = 100;
			break;
		case 6:
			speedLimit = 110;
			break;
		case 7:
		default:
			speedLimit =120;
			break;			
		}
		
	}
	
	
}
