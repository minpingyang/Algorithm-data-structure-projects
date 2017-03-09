// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 Assignment 2
 * Name:
 * Usercode:
 * ID:
 */

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ecs100.UI;

/** 
 * This class is provided as a bad example.
 * Don't do this at home!
 */
public class Order {

    /** the items that are wanted for the order */

    private List<String> wantList=new ArrayList<String>();
    private List<String> hasList=new ArrayList<String>();
    

    /** the items that have been added and are ready in the order */

    public Order() {

        double wantSize=10*Math.random();
        for(int i=0;i<wantSize;i++){
            double amount=Math.random();
            if(amount>0.7){
                wantList.add("Fish");

            }
            if(amount>0.3&&amount<=0.7){
                wantList.add("Chips");

            }
            if(amount<=0.3){
                wantList.add("Burger");

            }
        }

    }

    /** 
     *  The order is ready as long as every item that is
     *  wanted is also ready.
     */
    public boolean isReady() {

        if(wantList.isEmpty()){
            return true;
        }
        return false;
    }

    /** 
     *  If the item is wanted but not already in the order,
     *  then put it in the order and return true, to say it was successful.
     *  If the item not wanted, or is already in the order,
     *  then return false to say it failed.
     */
    public boolean addItemToOrder(String item){
        if (item.equals("Fish")) {

            if(wantList.contains("Fish")){
                wantList.remove("Fish");
                hasList.add("Fish");

                return true;
            }
        }
        if (item.equals("Chips")){

            if(wantList.contains("Chips")){
                wantList.remove("Chips");
                hasList.add("Chips");

                return true;
            }

        }
        if (item.equals("Burger")){

            if(wantList.contains("Burger")){
                wantList.remove("Burger");
                hasList.add("Burger");

                return true;
            }

        }
        return false;
    }

    /** 
     *  Computes and returns the price of an order.
     *  [CORE]: Uses constants: 2.50 for fish, 1.50 for chips, 5.00 for burger
     *  to add up the prices of each item
     *  [COMPLETION]: Uses a map of prices to look up prices
     */
    public double getPrice() {
        double price = 0;

        for(int i=0;i<hasList.size();i++){
            String has=hasList.get(i);
            price+=FastFood.priceMap.get(has);   
        }
        return price;
    }

    public void draw(int y) {

        for(int i=0;i<wantList.size();i++){
            String s1=wantList.get(i);

            if(s1.equals("Fish"))UI.drawImage("Fish-grey.png", 10+(i+hasList.size())*40, y);
            if(s1.equals("Chips"))UI.drawImage("Chips-grey.png", 10+(i+hasList.size())*40, y);
            if(s1.equals("Burger"))UI.drawImage("Burger-grey.png", 10+(i+hasList.size())*40, y);

        }

        for(int i=0;i<hasList.size();i++){

            String next1=hasList.get(i);

            if (next1.equals("Fish")) UI.drawImage("Fish.png", 10+i*40, y);
            if (next1.equals("Chips")) UI.drawImage("Chips.png",10+i*40, y);
            if (next1.equals("Burger")) UI.drawImage("Burger.png",10+i*40, y);

        }

    } 
}

