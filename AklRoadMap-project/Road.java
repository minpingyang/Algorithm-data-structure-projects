package code.comp261.ass1;

import java.util.HashSet;
import java.util.Set;

public class Road{
    public final int roadId,roadType,speedLimit,roadClass;
    //set notforCar and notForPede and notForBicy into boolean value by transfer 1-> true, 0--> false;
    public final boolean isOneWay,isForCar,isForBicy,isForPed;
    //each road is made by several road segments(order does not matter)
    public final Set<RoadSegment> roadSegments;
    //basic information of the road
    public final String city,label;
    /**constructor
     * ananlyzing the information of every line from files, and use field to store the information
     * **/
    public Road(String data){
        String[] values = data.split("\t");
        roadId =Integer.parseInt(values[0]);
        roadType =Integer.parseInt(values[1]);
        label = values[2];
        city =values[3];
        //use Integer class to transfer string to int type
        isOneWay = Integer.parseInt(values[4]) == 1? true : false;
        speedLimit = Integer.parseInt(values[5]);
        roadClass =Integer.parseInt(values[6]);
        isForCar =Integer.parseInt(values[7]) == 1? true : false;
        isForPed = Integer.parseInt(values[8]) == 1 ? true : false;
        isForBicy =Integer.parseInt(values[9]) == 1 ? true : false;
        roadSegments =new HashSet<>();

    }
}