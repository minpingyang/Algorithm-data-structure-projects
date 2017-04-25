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
            //poll out the small cost (g+H) node of the priority
            ASearchNode visitNode = fringe.poll();
            //mark it visited and add it to the set
            vistiedNodeSet.add(visitNode.node.nodeId);

                    /***find shortest path successfully case****/
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

                //the segment is valid
                //check if current neighbour node is visited yet.

                if(!vistiedNodeSet.contains(endNodeOfSegment.nodeId))
                {
                    //transfer neighbour node become A search node type which is used to add the priority queue
                    ASearchNode neighbourNode = new ASearchNode(endNodeOfSegment,visitNode,targetNode,shortestDistance);
                    // flag is used to check if the node is in the priority queue
                    boolean isOfFringe = false;

                    for(ASearchNode nodeOfFringe : fringe)
                    {
                       // check if the node is in the priority queue
                        if(nodeOfFringe.node.nodeId == endNodeOfSegment.nodeId)
                        {

                            // check if its start cost need to be updated( because it probably has smaller parent node cost, even it is same node)
                            if(neighbourNode.GcostFromStart < nodeOfFringe.GcostFromStart)
                            {   //if it is, then set the neighbour node to their priority que
                                nodeOfFringe.parentNode =neighbourNode.parentNode;
                                //set the edge between this Asearch node and its parent Asearch node as the segement between them.
                                nodeOfFringe.setEdge();
                                //set the smaller G cost to the current Asearch node
                                nodeOfFringe.setGCostFromStart(shortestDistance);
                            }
                            isOfFringe =true;
                            break ; //break the update inner for loop
                        }
                    }
                    //Otherwise, add all unvisited neighbour node to the priority queue through the loop
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
