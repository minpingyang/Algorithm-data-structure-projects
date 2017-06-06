import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * A new instance of HuffmanCoding is created for every run. The constructor is
 * passed the full text to be encoded or decoded, so this is a good place to
 * construct the tree. You should store this tree in a field and then use it in
 * the encode and decode methods.
 */
public class HuffmanCoding {
	
	private HuffmanTree huffmanTree;
    private HashMap<Character, Integer> frequencyMap;
    private HashMap<Character, String> encodeMap;
	/**
	 * constructor
	 */
	public HuffmanCoding(String text) {
		initialise(text);
	}
	
	/**initialise*/
	private void initialise(String text) {
		this.frequencyMap = analyseFrequency(text.toCharArray());
		this.huffmanTree = convertIntoTree(frequencyMap);
		this.encodeMap = encodeTree(huffmanTree);
	}
	/*
	 * print out the tree
	 * */
	@SuppressWarnings("null")
	public void writeFile(HuffmanNode rootNode)
	{
	    FileOutputStream outputStream = null;
	    PrintWriter printWriter = null;

	    try
	    {

	        outputStream = new FileOutputStream("BinaryTree.txt");
	        printWriter = new PrintWriter(outputStream); 

	        write(rootNode, printWriter);

	        printWriter.flush();

	  }catch(IOException e)
	  {
	     System.out.println("An error occured");
	    	  printWriter.close();
	     
	  }

	}

	 public void write(HuffmanNode mainNode, PrintWriter w)
	 {
	     if(mainNode != null){
	      write(mainNode.getLeftNode(), w);
	      w.print(mainNode);
	      write(mainNode.getRightNode(), w); 
	    }
	 }
	private HashMap<Character, String> encodeTree(HuffmanTree huffmanTree) {
		//declare a hashMap used for recording and return later
        HashMap<Character, String> encodingMap = new HashMap<>();
        Stack<HuffmanNode> huffNodeStack = new Stack<>();
        huffNodeStack.push(huffmanTree.getRoot());
        //add all leaves to the map
        while (!huffNodeStack.isEmpty()) {
            HuffmanNode node = huffNodeStack.pop();
            HuffmanNode left = node.getLeftNode();  
            HuffmanNode right = node.getRightNode();
            if (left != null) { 
            	// if left is not null, then right mustnt be null
                left.setCode(node.getCode() + '0');
                huffNodeStack.push(left);
                right.setCode(node.getCode() + '1');
                huffNodeStack.push(right);
            } else {
                encodingMap.put(node.getCharacter(), node.getCode());
            }
        }
        return encodingMap;
	}
	/**
     * Build the binary coding tree used in huffman coding algorithm.
     * 
     * @param 	---------- frequencyMap
     * @return  ---------- Huffman binary Tree
     */
	private HuffmanTree convertIntoTree(HashMap<Character, Integer> frequencyMap2) {
		//create a priority queue of message/node
		PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
        // add all huffNode into the priority queue as leaves
        for (char c : frequencyMap.keySet()) {
            HuffmanNode huffNode = new HuffmanNode(c);
            huffNode.setFrequence(frequencyMap.get(c));
            queue.offer(huffNode);
        }
        // Iterate through all nodes.
        int size = queue.size();
        for (int i = 1; i < size; i++) {
            HuffmanNode huffNode1 = queue.poll();
            HuffmanNode huffNode2 = queue.poll();
            //create a template parent
            HuffmanNode parentNode = new HuffmanNode('\0');
            parentNode.setFrequence(huffNode1.getFrequence() + huffNode2.getFrequence());
            parentNode.setLeftNode(huffNode1);
            parentNode.setRightNode(huffNode2);
            huffNode1.setParent(parentNode);
            huffNode2.setParent(parentNode);
            //insert the meanningful parent finally
            queue.offer(parentNode);
        }
        // finally, return the root of the tree
        HuffmanNode root =queue.poll();
        writeFile(root);
        return new HuffmanTree(root);
	}
	/**
     * analyse the frequency of all characters in the given text.
     * 
     * @param arrayText  --- an array of char from text
     * @return --- a HashMap <charcter , it's frequency number>
     */
	private HashMap<Character, Integer> analyseFrequency(char[] arrayText) {
		HashMap<Character, Integer> frequencyMap = new HashMap<Character, Integer>();
		for (char c : arrayText) {
            if (frequencyMap.containsKey(c)) {
                frequencyMap.put(c, frequencyMap.get(c) + 1);
            } else {
                frequencyMap.put(c, 1);
            }
        }
        return frequencyMap;
	}

	/**
	 * Take an input string, text, and encode it with the stored tree. Should
	 * return the encoded text as a binary string, that is, a string containing
	 * only 1 and 0.
	 */
	public String encode(String text) {
		 char[] arrayText = text.toCharArray();
	        StringBuilder encoding = new StringBuilder();
	        for (char c : arrayText) {
	            encoding.append(encodeMap.get(c));
	        }
	       return encoding.toString();
	}

	/**
	 * Take encoded input as a binary string, decode it using the stored tree,
	 * and return the decoded text as a text string.
	 */
	public String decode(String encoded) {
		StringBuilder decoded = new StringBuilder();
        char[] arrayText = encoded.toCharArray();

        // traverse the huffman tree according to the encoded binary string
        int index = 0;
        HuffmanNode root = huffmanTree.getRoot();
        HuffmanNode nodePointer = root;
        while (index < arrayText.length) {
            char charPointer = arrayText[index];
            if (charPointer == '0') {
                nodePointer = nodePointer.getLeftNode();
                if (nodePointer.getLeftNode() == null) {
                    decoded.append(nodePointer.getCharacter());
                    nodePointer = root;
                }
            } else if (charPointer == '1') {
                nodePointer = nodePointer.getRightNode();
                if (nodePointer.getLeftNode() == null) {
                    decoded.append(nodePointer.getCharacter());
                    nodePointer = root;
                }
            }
            index++;
        }
        return decoded.toString();
	}

	/**
	 * The getInformation method is here for your convenience, you don't need to
	 * fill it in if you don't wan to. It is called on every run and its return
	 * value is displayed on-screen. You could use this, for example, to print
	 * out the encoding tree.
	 */
	public String getInformation() {
		// prints all the leaves of the encoding tree, i.e. the binary string of
        // each char
        StringBuilder sb = new StringBuilder("\nThe encoding of each char:\n");

        for (Map.Entry<Character, String> entry : encodeMap.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        return sb.toString();
	}
	/***internal class*/
	private class HuffmanTree {
        private HuffmanNode root;

        public HuffmanTree(HuffmanNode root) {
            this.root = root;
        }

        public HuffmanNode getRoot() {
            return root;
        }
    }
	@SuppressWarnings("unused")
    private class HuffmanNode implements Comparable<HuffmanNode> {
        private HuffmanNode parent;
        private HuffmanNode leftNode;
        private HuffmanNode rightNode;
        private final char c;
        private String code = "";
        private int frequency = 0;
        
        public HuffmanNode(char c) {
            this.c = c;
        }
        
        @Override
        public int compareTo(HuffmanNode other) {
            return this.frequency - other.frequency;
        }
        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;

        }


        public int getFrequence() {
            return frequency;
        }

        public void setFrequence(int frequence) {
            this.frequency = frequence;
        }

        public char getCharacter() {
            return c;
        }

       
        public void setParent(HuffmanNode parent) {
            this.parent = parent;
        }

        public HuffmanNode getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(HuffmanNode leftNode) {
            this.leftNode = leftNode;
        }

        public HuffmanNode getRightNode() {
            return rightNode;
        }

        public void setRightNode(HuffmanNode rightNode) {
            this.rightNode = rightNode;
        }

        @Override
        public String toString() {
            return "char: " + this.c + ", coding: " + this.code + ".";
        }

    }
}
