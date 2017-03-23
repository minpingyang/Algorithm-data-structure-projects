package code.comp261.ass1;

import java.util.*;

/***
 *
 * internal class is created for this RoadTrie class, which is RoadTrieNode class.
 *This class is used to  finding matching road with same prefix
 *
 * @author  Minping
 * ***/
public class RoadTrie{


    //declare filed
    private RoadTrieNode root;
    /**constructor*/
    public RoadTrie(){root = new RoadTrieNode();}

    /***
     * Add information of a road by Tries structure
     * each different character of input will be putted as keys of children by using loop
     *
     * @param  roadLableType means the string contains "roadLable, roadType" of the loadNode method
     * @param  road means the information belong to which road
     * ****/
    public void add(String roadLableType, Road road){
        RoadTrieNode tempRootNode = root;
        roadLableType = roadLableType.trim(); // trim() is used to returns a copy of the string, with leading and trailing whitespace omitted.
        for(int i = 0 ; i < roadLableType.length(); i++){
            char c = roadLableType.charAt(i);  // only add one character of string as a key step by step
            if ( tempRootNode.children.get(c)==null) tempRootNode.children.put(c, new RoadTrieNode());
            tempRootNode = tempRootNode.children.get(c);
        }

    }

    public List<Road> find(String input){
        RoadTrieNode temRootNode = root; //initialise first
        //remove white space at the leading and trailling of the string for safety
        input = input.trim();
       // use for-loop to literate the string user input
        for (int i = 0; i<input.length(); i++){
            char c = input.charAt(i);
            if (temRootNode.children.get(c)!=null){
                //temp root go forward
                temRootNode = temRootNode.children.get(c);   //fixed
                //System.out.println("3333333"+(temRootNode==null));
            }else{
                return null;
            }
        }

        List<Road> roads = new ArrayList<>();
        temRootNode.getAllFromNode(roads);
        return roads;
    }





    /**************************Internal class***********************/

    public class RoadTrieNode{
        public Map<Character, RoadTrieNode> children;
        public boolean isMarked;
        public Set<Road> roads;


        //constructor of internal class
        public RoadTrieNode(){
            isMarked = false;
            roads = new HashSet<>();
            children =new HashMap<>();
        }
        /**
         * **/
        public void getAllFromNode(List<Road> roads){

            //// 'this' represent the object which call getAllFromNode
            if(isMarked && !this.roads.isEmpty()) {
                roads.addAll(this.roads);   //fixed
            }

            //use recursion to store
            for(RoadTrieNode node: children.values()) {
                node.getAllFromNode(roads);
            }

        }















}


}