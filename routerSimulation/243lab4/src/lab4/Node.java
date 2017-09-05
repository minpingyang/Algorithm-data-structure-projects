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

import java.util.*;

public class Node {
    private String name; // the node name
    private int xPos, yPos; // the positions to draw the node
    private HashMap<String, Integer> neighbours; // the list of neighbours
    private boolean hasVisit;
    private HashMap<Node, Integer> parentDistance = new HashMap<>();
    private int max = Integer.MAX_VALUE;
    private Vector<Node> others = new Vector<>();
    private Vector<Node> allNodes= new Vector<>();
    private HashMap<String, List<Node>> path = new HashMap<>(); // key represents the toNode, path from this to ToNode
    private HashMap<Node, Integer> pathCost = new HashMap<>(); // key represents the toNode, value refers to the cost
    private int previouseCost = max;
    private Node previouseParent = null;
    private List<Node> recordPath = new ArrayList<>();

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
        this.neighbours = new HashMap<>();
        hasVisit = false;
    }


    /**
     * Initialises the routing table
     */
    public void initialise(Vector<Node> nodes) {
        allNodes=nodes;
        List<Node> temp = new ArrayList<>();
        temp.add(this);
        path.put(this.name, temp);
        pathCost.put(this,0);
        for (Node n : nodes) {
            if (this.name.equals(n.name)) {
                parentDistance.put(null, 0);
                previouseCost = 0;
                previouseParent = null;
            } else {
                n.parentDistance.put(null, max);
                n.previouseCost = max;
                n.previouseParent = null;
            }
            others.add(n);
        }
        startIter();

    }

    public void addToPath(Node minNode) {
        recordPath=new ArrayList<>();
        Node temp = minNode;
        while (temp != this) {
            recordPath.add(temp);
            temp = temp.previouseParent;
        }


    }

    public void startIter() {

        Node minNode = null;
        if (!others.isEmpty()) {minNode = findMinCost();}
        if (minNode != null) {
            minNode.hasVisit = true;
            if (minNode != this) {
                addToPath(minNode);
                Collections.reverse(recordPath);
                path.put(minNode.name, recordPath);
                pathCost.put(minNode, minNode.previouseCost);

            }

            iteration(minNode);
        }


    }
    public Node findMinCost() {
        int min = max;
        Node minNode = null;
        for (Node node : others) {
            if (node.previouseCost!=max&&min > node.previouseCost) {

                min = node.previouseCost;
                minNode = node;
            }
        }

        return minNode;
    }

    //update others
    public void iteration(Node currentNode) {
        others.remove(currentNode);
        if(!others.isEmpty()){
            for (Node node : others) {
                compareCost(currentNode, node);
            }

            startIter();
        }else{
            printShortestPath();
        }

    }

    public void compareCost(Node currentNode, Node toNode) {

        int cost2 = max;
        int newCost = isNeighbour(currentNode, toNode) ? currentNode.neighbours.get(toNode.name) + currentNode.previouseCost : cost2;
        //infinity
        if (newCost < toNode.previouseCost) {
            toNode.previouseParent = currentNode;
            toNode.previouseCost = newCost;

        } else if (toNode.previouseCost == max) {
            toNode.previouseParent = currentNode;
        }

        toNode.parentDistance.put(toNode.previouseParent, toNode.previouseCost);//A preivouse one
    }


    //check if the unsure node is one of the neighbour of current node
    public boolean isNeighbour(Node currentNode, Node unsureNode) {
        Set<String> neighbourNames = currentNode.neighbours.keySet();
        for (String neighNa : neighbourNames) {
            if (unsureNode.getName().equals(neighNa)) {
                return true;
            }
        }

        return false;
    }




    public void printShortestPath() {
        UI.println("Table for "+this.name);
        UI.println("From: "+this.name);
        for(Node node:allNodes){
            UI.println("To: "+node.name);
            UI.print("path: "+this.name);
            List<Node> tempList = path.get(node.name);
            for(Node n:tempList){
                UI.print("->"+n.name);
            }


            UI.println("    total Cost: "+pathCost.get(node));
            UI.println();
        }
        UI.println();
        UI.println("------------------------------------------------");
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
     * @return the xPos
     */
    public int getxPos() {
        return xPos;
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
