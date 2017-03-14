package code.comp261.ass1;

import java.util.HashSet;
import java.util.Set;

import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;

public class Road {
	public final int roadId,roadType,speedLimit;
	public final double roadClass;
	public final boolean isOneWay, notForCar, notForPed, notForBicy;
	public final String city, label;
	public final Set<RoadSegment> roadSegments;
	/*Constructor. 
	 * */
	public Road(String data){
		String[] values = data.split("\t");
		roadId =Integer.parseInt(values[0]);
		roadType =Integer.parseInt(values[1]);
		label = values[2];
		city =values[3];
		isOneWay = Integer.parseInt(values[4]) == 1? true : false;
		notForCar =Integer.parseInt(values[7]) == 1? true : false;
		notForPed = Integer.parseInt(values[8]) == 1 ? true : false;
		notForBicy =Integer.parseInt(values[9]) == 1 ? true : false;
		roadSegments =new HashSet<>();
		
		int speed = Integer.parseInt(values[5]);
		// the max speed is probably 6 by checking the small data road file
		//other case, limit speed will go for default which is max speed of car I assumed
		switch (speed) {
		case 0:
			speedLimit = 5;
			break;
		case 1:
			speedLimit = 25;
			break;
		case 2:
			speedLimit = 30;
			break;
		case 3:
			speedLimit = 45;
			break;
		case 4:
			speedLimit = 60;
			break;
		case 5:
			speedLimit = 75;
			break;
		case 6:
			speedLimit = 110;
			break;
		default:
			speedLimit = 125;  
			break;
		}
		/*this part will transfer road class number -----> the percentage of limit speed 
		 * allowed in this class of road
		 * for example, if road class = 0.80, and current limit speed is 100 km/h, then the maxium
		 * speed of driving on this class of road is 100*80% = 800 km/h
		 * */
		int  road_class = Integer.parseInt(values[6]);
		switch(road_class){
		case 0:
			roadClass = 0.80;
			break;
		case 1:
			roadClass = 0.85;
			break;
		case 2:
			roadClass = 0.90;
			break;
		case 3:
			roadClass = 0.95;
			break;
		default:
			roadClass = 1.00;
			break;
				
		}
		
		
	}
}
