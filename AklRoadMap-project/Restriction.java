package code.comp261.ass1;

import java.util.Map;

/**
 * this class is used for restriction
 * the restrictions are some intersection which does not allow
 *
 * Created by minpingyang on 12/04/17.
 */
public class Restriction {
    Node nodeOne;
    Road roadOne;
    Node rstNode;
    Road roadTwo;
    Node nodeTwo;
    public Restriction(String line,Map<Integer,Node>nodeMap, Map<Integer,Road>roadMap){
        String[] values = line.split("\t");
        this.nodeOne = nodeMap.get(Integer.parseInt(values[0]));
        this.roadOne = roadMap.get(Integer.parseInt(values[1]));
        this.rstNode = nodeMap.get(Integer.parseInt(values[2]));
        this.roadTwo = roadMap.get(Integer.parseInt(values[3]));
        this.nodeTwo = nodeMap.get(Integer.parseInt(values[4]));

    }

}
