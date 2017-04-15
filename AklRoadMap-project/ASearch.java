package code.comp261.ass1;

import java.util.*;

/**
 * This utility class provided public static method to find a shortest path between two nodes
 * A* search
 * Created by minpingyang
 */


















public class ASearch {
    /**
     * */
    public static List<RoadSegment> findShortestPath(Node startNode, Node targetNode, boolean shortestDistance){
        //collect all visitedNodes
        HashSet<Integer> vistiedNodeSet =new HashSet<>();

        PriorityQueue<ASearchNode> fringe = new PriorityQueue<>();

        //enqueue start from the navigation start node
        fringe.offer(new ASearchNode(startNode,null,targetNode,shortestDistance));


        //
        while(!fringe.isEmpty()){
            //first in first out
            ASearchNode visitNode = fringe.poll();  //firstly, it is start node
            //mark it visited
            vistiedNodeSet.add(visitNode.node.nodeId);

            /***find all segments of the shortest path****/
            //poll node until the node is the target node
            if(visitNode.node.nodeId == targetNode.nodeId){
                List<RoadSegment> shortestPath = new ArrayList<>();
                ASearchNode lastNode = visitNode;
                //add all edges from the last node to its " ancestor " node
                while(lastNode.parentNode != null){
                    shortestPath.add(lastNode.edge);
                    lastNode = lastNode.parentNode;
                }
                //revers the order of the arraylist
                Collections.reverse(shortestPath);
                return shortestPath;
            }
            /***********************************************/

            Node startNodeOfSegment =visitNode.node;
             for(RoadSegment roadSegment:visitNode.node.linkedSegments){
                Node endNodeOfSegment = roadSegment.getEndNode(startNodeOfSegment);
                //check if isForCar
                if(roadSegment.road.notForCar){
                    continue;
                }
                //check if (it isOneWay, but the direction of the path is wrong
                if(roadSegment.road.isOneWay && startNodeOfSegment.nodeId == roadSegment.endNode.nodeId
                        && endNodeOfSegment.nodeId == roadSegment.startNode.nodeId)
                {
                    continue;
                }
                //go through all neighbour nodes which are not visited yet.
                //if the endNodeOf this segment has not visited yet

                if(!vistiedNodeSet.contains(endNodeOfSegment.nodeId)){
                    ASearchNode neighbourNode = new ASearchNode(endNodeOfSegment,visitNode,targetNode,shortestDistance);
                    boolean isOfFringe = false;

                    for(ASearchNode nodeOfFringe : fringe){
                            //if this end node of the segment is added in the fringe already
                        if(nodeOfFringe.node.nodeId == endNodeOfSegment.nodeId){

                            //case1 : it is already in fringe, check if its cost need to be updated(its neighbour has small G cost)
                            if(neighbourNode.GcostFromStart < nodeOfFringe.GcostFromStart){
                                nodeOfFringe.parentNode =neighbourNode.parentNode;
                                nodeOfFringe.setEdge();
                                nodeOfFringe.setGCostFromStart(shortestDistance);
                            }
                            isOfFringe =true;
                            break ; //break the update inner for loop
                        }
                    }
                    //Otherwise, add neighbournode int
                    if(!isOfFringe){
                        fringe.offer(neighbourNode);
                    }

                }
            }



        }
        //pathfind fails case:
        return new ArrayList<>();

    };
}
