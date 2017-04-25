package code.comp261.ass1;

import java.util.*;

/**
 * This class is used to find Articulation points by passing a graph of nodes
 *
 * Created by minpingyang
 */
public class ArticulationNode {



    /**
     * This method finds all articulation points in the given node collection
     *
     * @param nodes
     *            a collection of nodes within which this method will search for
     *            articulation points
     * @return a set of nodes containing all articulation points
     */
    public static Set<Node> findArticulationPoints(Collection<Node> nodes) {
        // make connections between between nodes, give them neighbours
        for (Node node : nodes) {
            node.setNeighbours();
        }

        Set<Node> unvisitedNodes = new HashSet<>(nodes);
        Set<Node> articulations = new HashSet<>();

        while (!unvisitedNodes.isEmpty()) {
            int numSubtrees = 0;
            Node start = unvisitedNodes.iterator().next();
            start.count = 0;
            for (Node neighbour : start.neighbourNodeSet) {
                //initially, count of all unvisited nodes equal MAX_COUNT;
                if (neighbour.count == Node.MAX_COUNT) {
                    iterArtPts(neighbour, start, articulations, unvisitedNodes);
                    numSubtrees++;
                }
            }
//            At root:
//            if there is more than one edge to an unvisited node,
//            then  root is an articulation point.

            if (numSubtrees > 1) {
                articulations.add(start);
            }

            unvisitedNodes.remove(start);
        }
        // could return empty set
        return articulations;
    }




    /**
     * Iterative version of articulation points search
     *
     * @param neighbourNode
     * @param startNode
     * @param artPoints
     * @param unvisitedNodes
     */
    private static void iterArtPts(Node neighbourNode, Node startNode, Set<Node> artPoints, Set<Node> unvisitedNodes) {
        Stack<ArtStackObject> stack = new Stack<>();
        stack.push(new ArtStackObject(neighbourNode, 1, new ArtStackObject(startNode, 0, null)));

        while (!stack.isEmpty()) {
            ArtStackObject peekElem = stack.peek(); // look not remove,  pop -->return and remove
            Node node = peekElem.node;
            // first time: initialise and construct children
            if (peekElem.children == null) {
                node.count = peekElem.reachBack = peekElem.count;
                peekElem.children = new ArrayList<>();
                for (Node neighbour : node.neighbourNodeSet) {
                    if (neighbour.nodeId != peekElem.parent.node.nodeId) {
                        peekElem.children.add(neighbour);
                    }
                }
                // children to process
            } else if (!peekElem.children.isEmpty()) {
                Node child = peekElem.children.remove(0); // remove until children is empty
                if (child.count < Node.MAX_COUNT) {
                    //update reachBack,if visited
                    peekElem.reachBack = Math.min(peekElem.reachBack, child.count);
                } else {
                    //else push on fringe
                    stack.push(new ArtStackObject(child, node.count + 1, peekElem));
                }
                // last time:determine if parent is articulation point, update parent's reachBack
            } else {
                if (node.nodeId != neighbourNode.nodeId) {
                    if (peekElem.reachBack >= peekElem.parent.count) {
                        artPoints.add(peekElem.parent.node);
                    }
                    peekElem.parent.reachBack = Math.min(peekElem.parent.reachBack, peekElem.reachBack);
                }
                stack.pop(); //remove the first node
                unvisitedNodes.remove(peekElem.node); // remove the node from unvisitedNode
            }
        }
    }

    /**
     * This class represents the object pushed into the stack for
     * articulation points algorithm.
     * @author minping
     *
     */
    private static class ArtStackObject {

        Node node;
        int reachBack;
        ArtStackObject parent;
        int count;
        List<Node> children;

        public ArtStackObject(Node node, int count, ArtStackObject parent) {
            this.node = node;
            this.reachBack = Node.MAX_COUNT;
            this.parent = parent;
            this.count = count;
            this.children = null;
        }
    }


}

