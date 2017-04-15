package code.comp261.ass1;

import java.util.*;

/**
 * This class is used to find Articulation points by passing a graph of nodes
 *
 * Created by minpingyang
 */
public class ArticulationNode {

    public  static Stack<ArticulationStackObj> stackObjs;

    public static Set<Node> findArticulationPoints(Collection<Node> nodes){
       //connect nodes with their neighbour nodes together
        for(Node node: nodes){
            node.setNeighbours();
        }
        Set<Node> unvisitedNodes = new HashSet<>(nodes);
        Set<Node> articulationPoints = new HashSet<>(nodes);
        while(!unvisitedNodes.isEmpty()){
            int numberOfSubtrees = 0;
            Node startNode = unvisitedNodes.iterator().next();
            startNode.depth = 0;
            for(Node neighbourNode : startNode.neighbourNodeSet){
                if(neighbourNode.depth == Integer.MAX_VALUE){
                    //!search articulation points
                    stackObjs = new Stack<>();
                    stackObjs.push(new ArticulationStackObj(neighbourNode,1 , new ArticulationStackObj(startNode,0,null)));
                    while (!stackObjs.isEmpty()){

                        ArticulationStackObj obj = stackObjs.peek();
                        Node node =obj.node;
                        if(obj.children == null){
                            node.depth = obj.reach =obj.depth;
                            obj.children =new ArrayList<>();
                            for(Node neighbour : node.neighbourNodeSet){
                                if(neighbour.nodeId != obj.parent.node.nodeId){
                                    obj.children.add(neighbour);
                                }
                            }



                        }else if(!obj.children.isEmpty()){
                            Node child = obj.children.remove(0);
                            if(child.depth < Integer.MAX_VALUE){
                                obj.reach = Math.min(obj.reach,child.depth);
                            }else {
                                stackObjs.push(new ArticulationStackObj(child,node.depth+1, obj));

                            }

                        }else {
                            if(node.nodeId != neighbourNode.nodeId){
                                if(obj.reach >= obj.parent.depth){
                                    articulationPoints.add(obj.parent.node);
                                }
                                obj.parent.reach = Math.min(obj.parent.reach,obj.reach);
                            }
                            stackObjs.pop();
                            unvisitedNodes.remove(obj.node);


                        }

                    }
                    numberOfSubtrees++;
                }
            }

            if (numberOfSubtrees>1){
                articulationPoints.add(startNode);
            }

            unvisitedNodes.remove(startNode);

        }




        return articulationPoints;
    }



    /****inner class for pushing object into stack*/
    private  static class ArticulationStackObj{
        Node node;
        int reach;
        int depth;
        ArticulationStackObj parent;
        List<Node> children;
        public ArticulationStackObj(Node node, int depth, ArticulationStackObj parent){
            this.node = node;
            this.depth =depth;
            this.parent = parent;
            this.children = null;
            this.reach =Integer.MAX_VALUE;
        }
    }

}

