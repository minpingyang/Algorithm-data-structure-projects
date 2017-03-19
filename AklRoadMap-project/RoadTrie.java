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
        public Map<Character, RoadTrieNode> children;
        public boolean isMarked;
        public Set<Road> roads;


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
            if ( tempRootNode.children.get(c)==null) tempRootNode.children.put(c, new RoadTrieNode());
            tempRootNode = tempRootNode.children.get(c);
        }

         tempRootNode.isMarked = true;
        tempRootNode.roads.add(road);
        // test add road if succeed
        for(Road r: tempRootNode.roads){
            System.out.println("add road:  "+r.label);
        }

    }
    // find a list of roads which matching input prefix
    public List<Road> find(String string){

        RoadTrieNode temRootNode = root;
        string = string.trim();   //remove white space at the leading and trailling of the string
        System.out.println("find string: "+string);
        for (int i = 0; i<string.length(); i++){
            char c = string.charAt(i);
            //System.out.println("Trie Node children: "+(temRootNode.children.get(c)==null));
            for(Road r: temRootNode.children.get(c).roads){
                System.out.println("Find temRootNode.Children: "+r.label);
            }


            if (temRootNode.children.get(c)!=null){
                //System.out.println("111111111");
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


}
