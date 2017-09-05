/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab4;

/**
 * @author aliahmed
 */

import ecs100.*;

import java.awt.Color;
import java.util.Scanner;
import java.io.*;
import java.util.Vector;
import java.util.Set;

public class Lab4 {
    Vector<Node> nodes = new Vector<Node>();

    public Lab4() {
        UI.addButton("load map", this::load);
        UI.addButton("Draw Nodes", this::draw);
        UI.addButton("Start", this::start);
        UI.addButton("Change a cost",this::changeCost);
    }
    public Node findNode(String name){
        for(Node node:nodes){
            if(node.getName().equals(name)){
                return node;
            }
        }
        return null;
    }

    public boolean isNeighbour(Node node1,Node node2){
        Set<String> keys = node1.getNeighbours().keySet();
        for(String key:keys){
            if(key.equals(node2.getName())){
                return true;
            }
        }
        return false;
    }

    public void changeCost(){

        String startNode_Name =UI.askString("Start node of the line: (eg, A, B, ..)");
        String toNode_Name = UI.askString("End node of the line: (eg, C, D, ..)");

        Node startNode = findNode(startNode_Name);
        Node toNode = findNode(toNode_Name);
        boolean isValid=startNode!=null&&toNode!=null;
        if(!isValid){
            UI.println("Error: Please input valid node!");
            return;}
        if(!isNeighbour(startNode,toNode)){
            UI.println("Error: There is not a line between these two nodes!");
            return;
        }
        int newCost= UI.askInt("input the new cost between the two nodes: ");
        if(newCost<=0){
            UI.println("Error: Please input a valid cost");
            return;
        }
        for(Node node:nodes){
            if(node.getName().equals(startNode_Name)){
                node.addNeighbour(toNode_Name,newCost);
            }else if(node.getName().equals(toNode_Name)){
                node.addNeighbour(startNode_Name,newCost);
            }
        }
        UI.clearText();
        UI.clearGraphics();
        UI.println("----------------repaint right panel ---------");
        UI.println("----------------reprint the table-------------");
        UI.println();
        draw();
        start();

    }
    public void start() {
        long start =System.currentTimeMillis();

        for (Node n : this.nodes) {
           n.initialise(nodes);
        }
        long end = System.currentTimeMillis();
        UI.println("Time Cost: "+(end-start)/1000.0+" s");
    }

    public void load() {
        try {
            Scanner scan = new Scanner(new File(UIFileChooser.open("Select Map File")));
            while (scan.hasNext()) {
                String n = scan.next();
                int x = scan.nextInt();
                int y = scan.nextInt();
                Node node = new Node(n, x, y);
                int count = scan.nextInt(); // the number of neighbouring nodes
                for (int i = 0; i < count; i++)
                    node.addNeighbour(scan.next(), scan.nextInt());

                this.nodes.add(node);
            }

            scan.close();
        } catch (IOException e) {
            UI.println("File Failure: " + e);
        }
    }

    public void draw() {
        for (Node n : this.nodes) {
            UI.setColor(Color.green);
            UI.fillOval(n.getxPos(), n.getyPos(), 40, 40);
            UI.setColor(Color.blue);
            UI.drawString(n.getName(), n.getxPos() + 5, n.getyPos() + 22);

            UI.setColor(Color.red);
            // loop on all neighbours
            Set<String> keys = n.getNeighbours().keySet();
            for (String s : keys) {
                //Search in the list of nodes for this node wth name "s"
                Node neighbour = null;
                for (int i = 0; i < this.nodes.size(); i++) {
                    if (this.nodes.get(i).getName().equals(s)) {
                        neighbour = this.nodes.get(i);
                        break;
                    }
                }

                if (neighbour != null) // there is a neighbour
                {
                    UI.drawLine(n.getxPos() + 20, n.getyPos() + 20, neighbour.getxPos() + 20, neighbour.getyPos() + 20);
                    //UI.drawString(n.getNeighbours().get(s), !, !);
                    int offX= neighbour.getxPos()-n.getxPos();
                    int offY = neighbour.getyPos()-n.getyPos();
                    int midX = offX>0?n.getxPos()+offX/2:neighbour.getxPos()-offX/2;
                    int midY= offY>0?n.getyPos()+offY/2:neighbour.getyPos()-offY/2;
                    UI.drawString(" "+n.getNeighbours().get(neighbour.getName()),20+midX,20+midY);

                }


            }

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new Lab4();
    }

}
