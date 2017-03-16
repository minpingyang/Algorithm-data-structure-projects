package code.comp261.ass1;

import java.util.*;

/*Try to write tries structure of roads from lecture introduced today
* roads can be indexed by their label or the city they belong to
* plan to use a nested-class structure,which is learnt from last year
* internal class is RoadTrieNode i guess
* outside class is RoadTrie.
*
*
* author Minping
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
        /*This method:
        * Firstly,Find all nodes(isMarked=='true') of subtree of current node.
        * then , add them into a list "roads".
        *
        * */
        public void getAllFromNode(List<Road> roads){
            //exit condition of recursion
            if(!isMarked || roads.isEmpty()) return;
            roads.addAll(this.roads);  // 'this' represent which object call getAllFromNode
            //use recursion to store
            for(RoadTrieNode node: children.values()) {
                node.getAllFromNode(roads);
            }

        }

    }

    // fileds of RoadTries class
    private  RoadTrieNode root;
    //constructor  EZ
    public RoadTrie(){
        root = new RoadTrieNode();
    }
    /***This method is used to add a road to RoadTrie data structure by
    */
    public void add(String string, Road road){
        RoadTrieNode tempRootNode = root;
        string = string.trim(); // trim() is used to returns a copy of the string, with leading and trailing whitespace omitted.
        for(int i = 0 ; i < string.length(); i++){
            char c = string.charAt(i);  // only add one character of string as a key step by step
            if (tempRootNode ==null){ tempRootNode = tempRootNode.children.put(c,new RoadTrieNode());}
            tempRootNode = tempRootNode.children.get(c);
        }
        tempRootNode.isMarked = true;
        tempRootNode.roads.add(road);
    }
    public List<Road> find(String string){

        RoadTrieNode temRootNode = root;
        string = string.trim();
        for (int i = 0; i<string.length(); i++){
            char c = string.charAt(i);
            if (temRootNode.children.get(c)!=null){
                temRootNode.children.get(c);
            }else{
                return null;
            }
        }
        //use list instead of set, since list store data orderly
        List<Road> roads = new ArrayList<>();
        temRootNode.getAllFromNode(roads);
        return roads;

    }


}
