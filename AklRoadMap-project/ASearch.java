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
        //store all visitedNodes
        HashSet<Integer> vistiedNodeSet =new HashSet<>();

        PriorityQueue<ASearchNode> fringe = new PriorityQueue<>();
        //enqueue * node
        fringe.offer(new ASearchNode(startNode,null,targetNode,shortestDistance));
        while(!fringe.isEmpty()){
            //first in first out
            ASearchNode visitNode = fringe.poll();
            //mark it visited
            vistiedNodeSet.add(visitNode.node.nodeId);

            //once target Node was polled--->found the path
            if(visitNode.node.nodeId == targetNode.nodeId){
                List<RoadSegment>shortestPath = new ArrayList<>();
                ASearchNode backTraceNode = visitNode;
                //from end to start
                while(backTraceNode.parentNode != null){
                    shortestPath.add(backTraceNode.edge);
                    backTraceNode = backTraceNode.parentNode;
                }
                //revers the cpllection of segments;
                Collections.reverse(shortestPath);
                return shortestPath;
            }

            Node startNodeOfSegment =visitNode.node;
            outLoop: for(RoadSegment roadSegment:visitNode.node.linkedSegments){
                Node endNodeOfSegment = roadSegment.getEndNode(startNodeOfSegment);
                //check if isForCar
                if(roadSegment.road.isForCar){
                    continue;
                }
                //check if isOneWay (be careful about the direction )
                if(roadSegment.road.isOneWay && startNodeOfSegment.nodeId == roadSegment.startNode.nodeId
                        && endNodeOfSegment.nodeId == roadSegment.endNode.nodeId)
                {
                    continue;
                }
                if(!vistiedNodeSet.contains(endNodeOfSegment.nodeId)){
                    ASearchNode neighbourNode = new ASearchNode(endNodeOfSegment,visitNode,targetNode,shortestDistance);
                    boolean isOfFringe = false;
                    for(ASearchNode nodeOfFringe : fringe){
                        if(nodeOfFringe.node.nodeId == endNodeOfSegment.nodeId){
                            //case1 : it is already in fringe, check if its cost need to be updated
                            if(neighbourNode.GcostFromStart < nodeOfFringe.GcostFromStart){
                                nodeOfFringe.parentNode =neighbourNode.parentNode;
                                nodeOfFringe.setEdge();
                                nodeOfFringe.setGCostFromStart(shortestDistance);
                            }
                            isOfFringe =true;
                            break ;
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
