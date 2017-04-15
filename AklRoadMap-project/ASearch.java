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
    public static List<RoadSegment> findShortestPath(Node startNode, Node targetNode, boolean shortestDistance, Set<Restriction> restrictions){
       //create a Set for storing all visited node
        HashSet<Integer> vistiedNodeSet =new HashSet<>();
        //use priority queue to allow the small cost of node out first
        PriorityQueue<ASearchNode> fringe = new PriorityQueue<>();

        //enqueue start from the navigation start node
        fringe.offer(new ASearchNode(startNode,null,targetNode,shortestDistance));


        //literate through the priority queue, until it poll out all elements
        while(!fringe.isEmpty())
        {

            ASearchNode visitNode = fringe.poll();  //firstly, it is start node
            //mark it visited and add it to the set
            vistiedNodeSet.add(visitNode.node.nodeId);

            /***find all segments of the shortest path****/
            //poll node until the node is the target node
            if(visitNode.node.nodeId == targetNode.nodeId){
                //create a list of path to store all segment orderly
                List<RoadSegment> shortestPath = new ArrayList<>();
                //last node is also the target node
                ASearchNode lastNode = visitNode;
                //add all edges from the last node to its " ancestor " node
                while(lastNode.parentNode != null){
                    //store all the edges
                    shortestPath.add(lastNode.edge);
                    //go to find its grandparent
                    lastNode = lastNode.parentNode;
                }

                //revers the order of the arraylist
                Collections.reverse(shortestPath);
                return shortestPath;
            }
            /***********************************************/

            Node startNodeOfSegment =visitNode.node;
            //literally go through all segements from current node
          Segmentloop:   for(RoadSegment roadSegment:visitNode.node.linkedSegments)
          {
                //find its other end node with same segement;
                Node endNodeOfSegment = roadSegment.getEndNode(startNodeOfSegment);
                //check the constraints of the road segment
                //check notFor car


                if(roadSegment.road.notForCar){
                    continue; //check next segment;
                }
                //check if (it isOneWay, but the direction of the path is wrong
                if(roadSegment.road.isOneWay && startNodeOfSegment.nodeId == roadSegment.endNode.nodeId
                        && endNodeOfSegment.nodeId == roadSegment.startNode.nodeId)
                {
                    continue;//check next segment;
                }
                //check if this node is restriction intersection
                 for(Restriction restriction: restrictions){
                     boolean flag;
                     flag = startNodeOfSegment.nodeId == restriction.rstNode.nodeId
                             && visitNode.parentNode != null && visitNode.edge != null
                             && visitNode.parentNode.node.nodeId == restriction.nodeOne.nodeId
                             && visitNode.edge.roadId == restriction.roadOne.roadId
                             && roadSegment.roadId == restriction.roadTwo.roadId
                             && endNodeOfSegment.nodeId == restriction.nodeTwo.nodeId;
                    if(flag)
                    {
                        continue Segmentloop;  //check next segment ---not next Restriction...
                    }
                 }


                //check if current neighbour node is visited yet.

                if(!vistiedNodeSet.contains(endNodeOfSegment.nodeId))
                {
                    ASearchNode neighbourNode = new ASearchNode(endNodeOfSegment,visitNode,targetNode,shortestDistance);
                    boolean isOfFringe = false; // mark it not belong to the priority queue yet

                    for(ASearchNode nodeOfFringe : fringe)
                    {
                            //if this end node of the segment is added in the fringe already
                        if(nodeOfFringe.node.nodeId == endNodeOfSegment.nodeId)
                        {

                            //case1 : it is already in fringe, check if its cost need to be updated(its neighbour has small G cost)
                            if(neighbourNode.GcostFromStart < nodeOfFringe.GcostFromStart)
                            {
                                nodeOfFringe.parentNode =neighbourNode.parentNode;
                                nodeOfFringe.setEdge();
                                nodeOfFringe.setGCostFromStart(shortestDistance);
                            }
                            isOfFringe =true;
                            break ; //break the update inner for loop
                        }
                    }
                    //Otherwise, add neighbournode int
                    if(!isOfFringe)
                    {
                        fringe.offer(neighbourNode);
                    }

                }


          }



        }
        //pathfind fails case:
        return new ArrayList<>();

    }


}
