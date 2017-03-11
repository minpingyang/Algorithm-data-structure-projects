package code.comp261.ass1;

import java.util.Map;

public class Singularity {
	Node nodeFrom, nodeTo, badNode;
	Roads roadFrom,roadTo;
	public Singularity(String line, Map<Integer, Node> nodeMap, Map<Integer, Roads> roadMap){
		String[] values = line.split("\t");
		nodeFrom =nodeMap.get(Integer.parseInt(values[0]));
		roadFrom = roadMap.get(Integer.parseInt(values[1]));
		badNode = nodeMap.get(Integer.parseInt(values[2]));
		roadTo = roadMap.get(Integer.parseInt(values[3]));
		nodeTo = nodeMap.get(Integer.parseInt(values[4]));
	}
	//description info of bade nodes
	public String toString(){
		return "Restriction [nodeFrom=" + nodeFrom.nodeID + ", roadFrom =" + roadFrom.roadID +", badNode ="+ badNode.nodeID + ", roadTo=" 
		   + roadTo.Roa
				
	}
    
}
