package code.comp261.ass1;

import java.util.*;

/*Try to write tries structure of roads from lecture introduced today
* roads can be indexed by their label or the city they belong to
* plan to use a nested-class structure,which is learnt from last year
* internal class is RoadTrieNode i guess
* outside class is RoadTrie.
* */
public class RoadTrie {

    //internal class
    public class RoadTrieNode{

        public boolean isMarked;
        public Set<Road> roads;
        public Map<Character, RoadTrieNode> children;

        //constructor of internal class
        public RoadTrieNode(){
            isMarked = false;
            roads = new HashSet<>();
            children =new HashMap<>();
        }

        /*Firstly, checking isMark(True) literally through for-LOOP
        *
        *
        * */
        public void getAllFromNode(ArrayList<Road> roads){
            //exit condition of recursion
            if(!isMarked || roads.isEmpty()) return;
            roads.addAll(this.roads);
            
        }

    }


    private  RoadTrieNode root;
    //constructor  EZ
    public RoadTrie(){
        root = new Road
    }
}
