package code.comp261.ass1;

/**
 * ASearch node is used in the priority queue in A* search,
 *
 * Created by minpingyang on 5/04/17.
 */
public class ASearchNode implements Comparable<ASearchNode>{
    public Node node;
    ASearchNode parentNode;
    RoadSegment edge;
    //g,h cost value
    double GcostFromStart = 0;
    double HcosttoTarget = 0;


    /**constructor
     *
     * GcostFromStart and HeuristicCostToTarget both of them are initialised and updated by the constructor
     * **/
    public ASearchNode(Node node,ASearchNode parentNode, Node targetNode, boolean shortestDistance) {
    this.node = node;
    this.parentNode = parentNode;
    this.setEdge();
    this.setGCostFromStart(shortestDistance);
    this.setHcosttoTarget(targetNode,shortestDistance);

    }
    /**
     * set H cost of distance/time from this node to target node.
     * */
    private void setHcosttoTarget(Node targetNode, boolean shortestDistance) {
        if(shortestDistance){
            this.HcosttoTarget =  this.node.location.distance(targetNode.location);
        }else{
            //100 km/h ---constance of speed is used to turn out the HuristicCose
            this.HcosttoTarget = this.node.location.distance(targetNode.location) / 100.0;
        }
    }
/***
 * set G cost which is the distance/time cost from part node to current node.
* ***/
    public void setGCostFromStart(boolean shortestDistance) {

        //this == start node case, which mean it does not have parent node
        if(parentNode == null){
            this.GcostFromStart = 0;
        }else{
            if(shortestDistance){
                //length cost + parent Gcost
                this.GcostFromStart = this.edge.lengthOfSegment + this.parentNode.GcostFromStart;
            }else {
                // time = distance / speed.
                this.GcostFromStart = (this.edge.lengthOfSegment / getRoadSpeed(this.edge)) + this.parentNode.GcostFromStart;
            }
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

/**
 * this method is used for affecting the order of the priority queue
 * **/
    @Override
    public int compareTo(ASearchNode otherNode){
        double costNode1 = this.GcostFromStart + this.HcosttoTarget;
        double costNode2 = otherNode.GcostFromStart + otherNode.HcosttoTarget;
        if(costNode1 > costNode2){
            return 1;
        }else if (costNode1 < costNode2){
            return -1;
        }else{
            return  0;
        }
    }
}
