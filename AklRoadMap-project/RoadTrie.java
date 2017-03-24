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
     * Add all of character by Tries data structure
     * Add all roads of file by Tries data structure
     * each different character of input will be putted as keys of children by using loop
     * Once all roads were added, then call findMatchingRoads method
     * @param  roadLableType means the string contains "roadLable, roadType" of the loadNode method
     * @param  road means the information belong to which road
     * ****/
    public void add(String roadLableType, Road road){
        RoadTrieNode tempRootNode = root;
        roadLableType = roadLableType.trim(); // trim() is used to returns a copy of the string, with leading and trailing whitespace omitted.
        //add all of different character of input into keys of Children which is the Tries Structure
        for(int i = 0 ; i < roadLableType.length(); i++){
            char c = roadLableType.charAt(i);  // only add one character of string as a key step by step
            if ( tempRootNode.children.get(c)==null) tempRootNode.children.put(c, new RoadTrieNode());
            tempRootNode = tempRootNode.children.get(c);
        }
        //only mark final character of input into true
        tempRootNode.isMarked = true;
        //roads is a filed of roadTrieNode class. which a set collection of all matching all roads with same prefix
        //then add all of the road passing this add method.
        // it turns out all of roads of file added into the roads set collection, set is uesd for avoiding duplicates
        tempRootNode.roads.add(road); // this add method is add method of Set
        // test add road if succeed
        for(Road r: tempRootNode.roads){
            System.out.println("add road:  "+r.label);
        }
    }
    /***
     * Find all matching roads which has same prefix same as user input
     * This method is used by search method
     * @param  input user input in the search box
     * *****/

    public List<Road> findMatchingRoads(String input){
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
        //getAllFromNode method can put all of matching road into  roads list
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
            roads = new HashSet<>(); //matching roads of every character
            // key refers to all of character of information of roads
            //value refers to RoadTrieNode type which point to next children map
            children =new HashMap<>();

        }
        /**
         * The list of road passing as parameter of this function is used to store all the road
         * find all nodes which were marked as true under the current subtree of the node calling this method
         * **/
        public void getAllFromNode(List<Road> roads){

            //end the recursive     "this" refer to tempRootNode of findMatchingNode method
            if(isMarked && !this.roads.isEmpty()) {
                //add all find node into the set collection of roads
                roads.addAll(this.roads);   //fixed
            }

            //use recursion to store
            for(RoadTrieNode node: children.values()) {
                node.getAllFromNode(roads);
            }

        }


}


}