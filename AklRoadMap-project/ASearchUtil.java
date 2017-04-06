package code.comp261.ass1;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

/**
 * This utility class provided public static method to find a shortest path between two nodes
 * A* search
 * Created by minpingyang
 */
public class ASearchUtil {
    /**
     * */
    public static List<RoadSegment> findShortestPath(Node startNode, Node endNode, boolean shortestDistance){
        //store all visitedNode
        HashSet<Integer> vistiedNodeSet =new HashSet<>();

        PriorityQueue<ASearchNode> fringe = new PriorityQueue<>();
        //enqueue * node
        fringe.offer(new ASearchNode(startNode,null,endNode,shortestDistance));
        while(!fringe.isEmpty()){
            ASearchNode visitNode = fringe.poll();
            //add it to visitedNodeSet collection
            vistiedNodeSet.add(visitNode.node.nodeId);


        }
        return null;

    };
}
