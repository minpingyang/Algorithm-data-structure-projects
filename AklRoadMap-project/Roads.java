package code.comp261.ass1;

import java.util.HashSet;
import java.util.Set;

import javax.jws.Oneway;

public class Roads {
	public int roadId, roadType, speedLimit;//road type hasnt found definition
	public double roadClass; //higher class are considered to be "faster" or at least "better".
	public boolean isOneWay, notForCar, notForPedestrian, notForBike;
	public String city, label;
	public Set<RoadSegment> roadSegments;
	public Roads(String line){
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
		// value of road class is used to be factor to decide how fast the car can run on the class road
		int readRoadClass = Integer.parseInt(values[6]);
		switch(readRoadClass){
		case 0:
			roadClass = 0.75;   //  75% of current speed Limit
			break;
		case 1:
			roadClass = 0.80;   //  80% of current speed Limit
			break;
		case 2:
			roadClass = 0.85;   //  85% of current speed Limit
			break;
		case 3:
			roadClass = 0.90;   //  90% of current speed Limit
			break;
		case 4:
		default:					// full
			roadClass= 1.00;
			break;			
		}
		
	}
	
	
}
