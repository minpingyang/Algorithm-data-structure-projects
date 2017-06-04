/**
 * A new KMP instance is created for every substring search performed. Both the
 * pattern and the text are passed to the constructor and the search method. You
 * could, for example, use the constructor to create the match table and the
 * search method to perform the search itself.
 */
public class KMP {
	String pattern;
	String text;
	static boolean kmpVersion = true;//version control
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
        if(!kmpVersion){
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
        
        for (int i = 0; i < matchTable.length; i++) {
            System.out.println("" + i + " : " + matchTable[i]);
        }

        return matchTable;
    }
	
}
