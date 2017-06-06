/**
 * A new KMP instance is created for every substring search performed. Both the
 * pattern and the text are passed to the constructor and the search method. You
 * could, for example, use the constructor to create the match table and the
 * search method to perform the search itself.
 */
public class KMP {
	String pattern;
	String text;
	static int version = 2;//version control
	/*for boyerMoore search*/
	private static int range = 300;
	private static int searchingWindow[] = new int[range];
	public KMP(String pattern, String text) {
		this.pattern =pattern;
		this.text = text;
	}

//	
//	/**
//	 * Perform KMP substring search on the given text with the given pattern.
//	 * 
//	 * This should return the starting index of the first substring match if it
//	 * exists, or -1 if it doesn't.
//	 */
	public static int search(String pattern, String text) {
		//if empty or invalid case
		if (pattern.length() == 0 || text.length() == 0|| pattern.length() > text.length()) {
            return -1;
        }
		char[] p =pattern.toCharArray();
		char[] t =text.toCharArray();
		//else: store both length to vars
        int pLen = pattern.length();
        int tLen = text.length(); 
        //initial both index
        int pIndex = 0;
        int tIndex = 0;
        boolean found=false;
        //brute force search version
        if(version == 0){
        	for(int k = 0; k <= tLen - pLen; k++){
    			found = true;
    			//go through the text each time
    			for(int i = 0; i <= pLen -1; i++){
    				if(p[i] != t[k+i]){
    					found = false;
    					break;
    				} 
    			}
    			if(found){
    				return k;
    			}
    		}
        	return -1;
        }
        //KMP versison
        else if(version == 1){
        	//compute match table(a array)
            int[] matchTable = matchTable(pattern);
    		// KMP Search version
            while (tIndex + pIndex < tLen) {
                if (pattern.charAt(pIndex) == text.charAt(tIndex + pIndex)) {
                    pIndex++;
                    if (pIndex == pLen) {
                        return tIndex;
                    }
                    
                } else if (matchTable[pIndex] == -1) {
                    pIndex = 0;
                    tIndex = tIndex + pIndex + 1;
                } 
                else {
                    tIndex = tIndex + pIndex - matchTable[pIndex];
                    pIndex = matchTable[pIndex];
                }
            }
        }
        //boyermoore search version
        else if(version == 2){
        	range = 2*pattern.length();
    		//validate, null or empty string is not allowed
    		if (text == null || text.length() == 0 || pattern == null || pattern.length() == 0 || text.length() < pattern.length())
    			return -1;
    		// build last occurrence index
    		initialPatternIndex(pattern);
    		
    		// searching from last character of the pattern
    		int start_ptr = pattern.length() - 1;
    		int end_ptr = text.length();
    		while (start_ptr < end_ptr) {
    			
    		int	position = start_ptr;
    			
    			for (int i = pattern.length() - 1; i >= 0; i--) {
    				
    				// if not match the last character
    				if (pattern.charAt(i) != text.charAt(position)) {
    					
    					// if the character of text exist in one of char of patter
    					if (getIndex(text.charAt(position)) != -1) {
    						
    						if (i - getIndex(text.charAt(position)) > 0)
    							// move match parts
    							start_ptr += (i - getIndex(text.charAt(position)));
    						else
    							// case 2
    							start_ptr += 1;
    						
    					} else {	
    						// move whole the pattern to right side of the chara of text
    						start_ptr += i + 1;
    					}
    					
    					break;
    				}
    				if (i == 0) {
    					// found pattern
    					return position;
    				}
    				
    				position--;
    			}
    		}
        }
       
        return -1;
	}
	 /**
     * Compute the match table for KMP search
     * 
     * @param pattern
     *            --- the String to be search for
     * @return --- the match table, which is an integer array with the same length as the argument
     *         string
     */
    private static int[] matchTable(String pattern) {

        if (pattern.length() == 0) {
            return null;
        } else if (pattern.length() == 1) {
            return new int[] { -1 };
        }

        int patLength = pattern.length();
        int[] matchTable = new int[patLength];
        matchTable[0] = -1;
        matchTable[1] = 0;
        int index = 0;
        int pos = 2;

        while (pos < patLength) {
            if (pattern.charAt(pos - 1) == pattern.charAt(index)) {
                matchTable[pos] = index + 1;
                pos++;
                index++;
            } else if (index > 0) {
                index = matchTable[index];
            } else {
                matchTable[pos] = 0;
                pos++;
            }
        }

        return matchTable;
    }
    
    private static void initialPatternIndex(String pattern) {	
		int length = pattern.length();
		//mark that does not find    -1
		for (int i = 0; i < range; i++)
			searchingWindow[i] = -1;
		//char -> index
		for (int i = 0; i < length; i++)
			searchingWindow[pattern.charAt(i)] = i;
	}
	
	private static int getIndex(char c) {
		
		return searchingWindow[c];
	}
}
