package code.comp261.ass1;

import java.util.HashSet;
import java.util.Set;

public class Road{
    public final int roadId,roadType,speedLimit;
    public final double roadClass;
    //set notforCar and notForPede and notForBicy into boolean value by transfer 1-> true, 0--> false;
    public final boolean isOneWay,notForCar,notForBicy,notForPed;
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
        int speed = Integer.parseInt(values[5]);
        int classOfRoad =Integer.parseInt(values[6]);
        notForCar =Integer.parseInt(values[7]) == 1? true : false;
        notForPed = Integer.parseInt(values[8]) == 1 ? true : false;
        notForBicy =Integer.parseInt(values[9]) == 1 ? true : false;
        roadSegments =new HashSet<>();

        /**transfer speed value into a real velocity value**/
        switch (speed){
            case 0:
                this.speedLimit = 30;
                break;
            case 1:
                this.speedLimit = 45;
                break;
            case 2:
                this.speedLimit = 50;
                break;
            case 3:
                this.speedLimit = 60;
                break;
            case 4:
                this.speedLimit = 80;
                break;
            case 5:
                this.speedLimit = 100;
                break;
            case 6:
                this.speedLimit = 110;
                break;
            case 7:
            default:  // let's assume a car's maximum speed is 120km/h
                this.speedLimit = 120;
                break;
        }
        /**transfer road class value into a percentage of road limited speed value**/
        switch (classOfRoad) {
            case 0:
                this.roadClass = 0.60;
                break;
            case 1:
                this.roadClass = 0.75;
                break;
            case 2:
                this.roadClass = 0.80;
                break;
            case 3:
                this.roadClass = 0.95;
                break;
            case 4:
            default:
                this.roadClass = 1.00;
                break;
        }

    }
}