package code.comp261.ass1;

/**
 * ASearch node is used in the priority queue in A* search,
 *
 * Created by minpingyang on 5/04/17.
 */
public class ASearchNode {
    public Node node;
    ASearchNode parentNode;
    RoadSegment edge;
    //g,h cost value
    double GcostFromStart = 0;
    double HcosttoTarget = 0;


    /**constructor**/
    public ASearchNode(Node startNode,ASearchNode parentNode, Node targetNode, boolean shortestDistance) {
    this.node = node;
    this.parentNode = parentNode;
    this.setEdge();
    this.setGCostFromStart(shortestDistance);
    this.setHeuristicCost(targetNode,shortestDistance);

    }
    /**
     * set H cost of distance/time from this node to target node.
     * */
    private void setHeuristicCost(Node targetNode, boolean shortestDistance) {
        if(shortestDistance){
            this.HcosttoTarget =  this.node.location.distance(targetNode.location);
        }else{
            this.HcosttoTarget = this.node.location.distance(targetNode.location) / 100.0;
        }
    }
/***
 * set G cost which is the distance/time cost from part node to current node.
* ***/
    private void setGCostFromStart(boolean shortestDistance) {
        if(parentNode == null){
            this.GcostFromStart = 0;
        }
        if(shortestDistance){
            //length cost + parent Gcost
            this.GcostFromStart = this.edge.lengthOfSegment + this.parentNode.GcostFromStart;
        }else {
            // time = distance / speed.
            this.GcostFromStart = (this.edge.lengthOfSegment / getRoadSpeed(this.edge)) + this.parentNode.GcostFromStart;
        }
    }

/***
 * get the reasonable road speed which is related road class and road limited speed
 * road class is already changed into a percentage number of speed limitation.
 * **/
    private double getRoadSpeed(RoadSegment edge) {
        return edge.road.roadClass*edge.road.speedLimit;
    }

    /**
     * This method find the edge between this node and its parentNode
     * **/
    public void setEdge(){
        if (this.node == null || this.parentNode == null) {
            return;
        }
        for (RoadSegment segment : this.node.linkedSegments) {
            if (this.parentNode.node.linkedSegments.contains(segment)) {
                this.edge = segment;
                return;
            }
        }
    }
}
