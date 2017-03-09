// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 103, Assignment 7
 * Name:     
 * Usercode:
 * ID:
 */
import java.awt.Color;
import ecs100.*;
import java.util.*;

/** 
 *  GeneralTreeNode represents a simple node in a tree. The node stores references 
 * to children and a parent (the latter is null in the case of the root node).
 * 
 * For the purposes of one technique for drawing on the screen, there is a location field as well.
 * This is purely for drawing purposes, and is not directly related to the concept of the tree structure.
 * NOTE: There are other techniques for drawing the tree that don't require the location field, so you 
 * may decide not to use the location field/methods at all.
 * 
 * @author Thomas Kuehne
 * 
 * Based on code written by Stuart Marshall and Monique Damitio
 */

public class GeneralTreeNode {
    // The name that is stored at the node.
    private String name;

    // A reference to the parent node of this node.  
    private GeneralTreeNode parent;

    // Since this is a general tree, we don't have a limit on the number of children a node may have, so we use a set.
    private Set<GeneralTreeNode> children = new HashSet<GeneralTreeNode>();

    // This field is only needed for one particular technique for drawing the tree on the screen.
    private Location location;  //location of the center of the node on the screen

    /**
     * Creates a new node
     * 
     * @param newName the name of the new node
     */
    public GeneralTreeNode(String newName) {
        name = newName;
    }

    // getter and setter methods

    public String getName() {
        return name;
    }

    public GeneralTreeNode getParent() {
        return parent;
    }

    private void setParent(GeneralTreeNode newParent) {
        parent = newParent;
    }

    public Set<GeneralTreeNode> getChildren() {
        return children;
    }

    public void setLocation(Location newLocation) {
        location = newLocation;
    }

    public Location getLocation() {
        return location;
    }

    /**
     * Adds a child to the receiver node
     * 
     * @param newChild the node/subtree to be added as a new child
     * 
     */
    public void addChild(GeneralTreeNode newChild) {
        // add to set of children
        children.add(newChild);

        // set new parent reference
        newChild.setParent(this);    /// 
    }

    /**
     * Finds the node whose name is equal to the target string
     * 
     * CORE.
     * 
     * If the target string appears multiple times, then just return the first one encountered. 
     * The 'addNode' method from class GeneralTree should guarantee that duplicate strings aren't added anyway.
     * 
     * HINT: The most natural implementation of this method is recursive.
     * 
     * @param  targetName the name of the node we are looking for
     * @return the node that contains the target name, or null if no such node exists.
     */
    public GeneralTreeNode findNode(String targetName) {
        /*# YOUR CODE HERE */
        //return first node it finds which mathches the target string
        //this is the child whose name equal targetName
        if (name.equals(targetName)) return this;                 
        //recursive step
        for (GeneralTreeNode c : children) {
            GeneralTreeNode node =c.findNode(targetName);
            if(node!=null){return node;}
           // if (c.findNode(targetName) != null) return c.findNode(targetName);
        }
        // no node containing the string could be found
        return null;  
    }

    /**
     * Removes the receiver node from the list of children of its parent
     * 
     * CORE.
     * 
     */
    public void remove() {
        /*# YOUR CODE HERE */ 
        parent.children.remove(this);    //remove the node from its parent and use the romove method by the set of the children 
    }

    /**
     * Adds children from another donor node to this node
     * 
     * CORE.
     * 
     * This method is used to implement method 'moveSubtree' in class General Tree.
     * 
     * @param donorNode the node that has the children to be added
     */
    public void addChildrenFromNode(GeneralTreeNode donorNode) {
        /*# YOUR CODE HERE */ 
        for(GeneralTreeNode c : donorNode.getChildren()) 
            addChild(c);
    }

    /** 
     *  Prints the strings of all the nodes under the given target node 
     *  (including the target node itself)
     *  
     *  CORE.
     * 
     *  HINT: The most natural version of this method is recursive.
     */
    public void printSubtree() {
        /*# YOUR CODE HERE */ 
        UI.println(getName());  //BASE CASE
        //RECRUSIVE STEP
        for (GeneralTreeNode c : children)
            c.printSubtree();
    }

    /** 
     *  Returns true if the subtree whose root starts with the receiver contains the node of the parameter
     *   
     *  COMPLETION.
     * 
     *   This method is used by moveSubtree(...), to ensure that we aren't trying to move a node 
     *  (and hence the subtree rooted at that node) in a way that makes it become a child of one
     *  of it's existing descendants. 
     *  
     *  HINT: The most natural version of this method is recursive.
     *  
     *  @param node the node to check for
     *  @return true if the node is in the subtree, and false otherwise
     *  
     */
    public boolean contains(GeneralTreeNode node) {
        /*# YOUR CODE HERE */ 
        //BASE SETP
        if (this == node) return true;
        //RECRUSIVE STEP
        for (GeneralTreeNode c : children){
            if (c.contains(node)) return true;
        }
        // this subtreee does not contain 'node'
        return false;
    }

    /** 
     *  Prints the names of all the nodes in the path from the target node to the root of the entire tree 
     *  
     *  COMPLETION.
     * 
     */

    public void printPathToRoot() {
        /*# YOUR CODE HERE */ 
        UI.println(getName()+" ");
        //use recursion to make the parent become the root, then return; UPWARD FROM TARGET NODE
        if (parent != null) parent.printPathToRoot();
        else return;
    }

    /** 
     *  Prints all the names of all the nodes at the given depth 
     *  
     *  COMPLETION.
     *  
     *  Prints nothing if there are no nodes at the specified depth.
     * 
     *  HINT: The most natural version of this method is recursive.
     *  
     *  @param depth the depth of the tree whoses nodes are to be listed. The root is at depth 0.
     */

    public void printAllAtDepth(int targetDepth, int currentDepth) {
        /*# YOUR CODE HERE */ 
       
        if (currentDepth == targetDepth)  {
            UI.println(getName()+" ");
            return;
        }
        //was wonderring if currentDepth > targetDepth
        //but question said that we should increase the currentDepth each further recursion. 
        for (GeneralTreeNode c : children)
            c.printAllAtDepth(targetDepth, currentDepth + 1);
    }

    /**
     * Draws all the nodes in the subtree that has the receiver as the root on the Graphics pane
     * 
     *  CORE.
     * 
     *  The provided code just draws the tree node; you need to make it draw all the nodes.
     *  Make sure that parents and their children are connected by lines .
     *  
     *  HINT: The most natural version of this method is recursive.
     *  
     *  HINT: Use UI.drawLine(...) to draw the connecting lines and pay 
     *  attention in what order you 'paint' on the canvas in order to get a good looking result. 
     *  
     */
    public void redrawSubtree() {
        /*# YOUR CODE HERE */ 
        double px=this.getLocation().getX();
        double py=this.getLocation().getY();
        //draw lines between parent and children

        for (GeneralTreeNode c : children){
            UI.drawLine(px, py, c.getLocation().getX(), c.getLocation().getY());
        }
        //draw the parent of node 
        this.redrawNode();
        //check if there is a child
        if (this.children == null) return;
        //recursive the method
        for (GeneralTreeNode child : children)
            child.redrawSubtree();

        this.redrawNode();
    }

    /**
     * Draws a node at the location stored in that node. Drawing the node consists of drawing an oval, and writing the name string
     * out "in" that oval. Note that no consideration is given to the length of the string, so this could look ugly.
     * 
     * @param node  the node to be draw on the canvas. This node should already have had it's location set earlier on.
     */
    private void redrawNode() {
        double x = this.getLocation().getX();
        double y = this.getLocation().getY();

        UI.setColor(Color.white);
        UI.fillOval(x-2*GeneralTree.nodeRad, y-GeneralTree.nodeRad, GeneralTree.nodeRad*4-1, GeneralTree.nodeRad*2-1);

        UI.setColor(Color.black);
        UI.drawOval(x-2*GeneralTree.nodeRad, y-GeneralTree.nodeRad, GeneralTree.nodeRad*4-1, GeneralTree.nodeRad*2-1);

        UI.drawString(name, x-GeneralTree.nodeRad-4, y+5);
    }
}
