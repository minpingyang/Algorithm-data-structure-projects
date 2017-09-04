/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab4;

/**
 * @author aliahmed
 */

import ecs100.UI;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

public class Node {
    private String name; // the node name
    private int xPos, yPos; // the positions to draw the node
    private HashMap<String, Integer> neighbours; // the list of neighbours
    private boolean hasVisit;
    private HashMap<String, Integer> parentDistance = new HashMap<>();
    private int max = Integer.MAX_VALUE;
    private Vector<Node> others=new Vector<>();
    // Think of what data structure to use to store the routing table
    // 2D array, vector, priorityQueue, etc.

    /**
     * @param n:  the node name
     * @param xp: the x position
     * @param yp: the y position
     */
    public Node(String n, int xp, int yp) {
        this.name = n;
        this.xPos = xp;
        this.yPos = yp;
        this.neighbours = new HashMap<String, Integer>();
        hasVisit = false;
    }


    /**
     * Initialises the routing table
     */
    public void initialise(Vector<Node> nodes) {
        hasVisit=true;
        for (Node n : nodes) {
            if (this.getName().equals(n.getName())) continue;
            n.parentDistance.put(this.getName(), max);
            others.add(n);
        }
        checkNeighbour();
    }

    public void checkNeighbour() {
        Set<String> neighbourNames = neighbours.keySet();
        for (Node n : others) {
            for (String neighNa : neighbourNames) {
                if(n.getName().equals(neighNa)){
                    n.parentDistance.put(this.getName(),this.neighbours.get(neighNa));
                }
            }
        }
        String minName= findMinCost(others);
        if(minName!=" "){
            Node node= findNodeByName(minName,others);
           if(node!=null) {
               node.hasVisit=true;
               printTable(node);
           }

        }
    }


    public Node findNodeByName(String name,Vector<Node> nodes){
        for(Node node:nodes){
            if(node.getName().equals(name)){
                return node;
            }
        }
        return null;
    }

    public String findMinCost(Vector<Node> nodes){
        int min = max;
        String minName = " ";
        for(Node node:nodes){

            int cost =node.parentDistance.get(this.getName()) ;
            if(cost!=max&&min>cost){
                min=cost;
                minName=node.getName();
            }
        }
       return minName;
    }

    public void printTable(Node n){
        UI.println("From: "+this.name+" To: "+n.getName());
        UI.println("Path: "+this.name+"-"+n.getName());
        UI.println("Cost: "+n.parentDistance.get(this.getName()));
    }

    /**
     * Updates the routing table
     */
    public void updateRoutingTable(String fromNode, String nodeData, int newCost) {


    }


    public void simulateNewLink(String fromNode, String nodeData, int newCost) {

    }

    /**
     * @param nodeName:the neighbour node name
     * @param cost:        the cost of the link
     */
    public void addNeighbour(String nodeName, int cost) {
        this.neighbours.put(nodeName, cost);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the xPos
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * @param xPos the xPos to set
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    /**
     * @return the yPos
     */
    public int getyPos() {
        return yPos;
    }

    /**
     * @param yPos the yPos to set
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    /**
     * @return the neighbours
     */
    public HashMap<String, Integer> getNeighbours() {
        return neighbours;
    }

    /**
     * @param neighbours the neighbours to set
     */
    public void setNeighbours(HashMap<String, Integer> neighbours) {
        this.neighbours = neighbours;
    }
}
