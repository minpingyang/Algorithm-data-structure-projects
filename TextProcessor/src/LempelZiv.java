import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A new instance of LempelZiv is created for every run.
 */
public class LempelZiv {
	private static int slidingWindown_size = 1000;
	/**
	 * Take uncompressed input as a text string, compress it, and return it as a
	 * text string.
	 */
	public String compress(String input) {
		StringBuilder stringBuilder = new StringBuilder();
		//initial cursor
        int cursor = 0;
        //SEARCH_WINDOW_SIZE = input.length() -1;
        while (cursor < input.length()) {
            int lookAhead = 0;
            int preMatch = cursor > slidingWindown_size ? slidingWindown_size : cursor;
            //loop
            do {
                int match = stringMatch(input, cursor, lookAhead);
                //match succeeded
                if (match >= 0) {
                    preMatch = match;
                    lookAhead++;

                } else {
                	//safely initialise  windowsize
                    int windowSize = cursor > slidingWindown_size ? slidingWindown_size : cursor;
                    int offset = windowSize - preMatch;
                    Character nextChar;//object character
                    if (cursor + lookAhead < input.length()) {
                        nextChar = input.charAt(cursor + lookAhead);
                    } else {
                        nextChar = null;
                    }
                    stringBuilder.append(new Tuple(offset, lookAhead, nextChar).toString());
                    cursor += lookAhead + 1; // move forward with the text
                    break;
                }
            }while(true);
        }

        return stringBuilder.toString();
	}
	/*
	 * own-defined Tuple class
	 * */
	 private class Tuple {

	        private int offset;
	        private int length;
	        private Character nextChar;

	        public Tuple(int offset, int length, Character nextChar) {
	            this.offset = offset;
	            this.length = length;
	            this.nextChar = nextChar;
	        }

	        public Tuple(String text) {
	            String[] tokens = text.split("\\,");
	            this.offset = Integer.valueOf(tokens[0]);
	            this.length = Integer.valueOf(tokens[1]);
	            // in case it's the end of text
	            if (tokens.length > 2) {
	                this.nextChar = tokens[2].charAt(0);
	            }
	        }

	        @Override
	        public String toString() {
	            if (nextChar == null) {
	                return offset + "," + length + ",]";
	            }
	            return offset + "," + length + "," + nextChar + "]";
	        }
	    }
	
	private int stringMatch(String input, int cursor, int lookAhead) {
        // ensure start index >= 0
		//searchStart was changing dynamically
        int searchStart = cursor - slidingWindown_size < 0 ? 0 : cursor - slidingWindown_size;
        String searchBuffer = input.substring(searchStart, cursor);
        // out of bounds 
        if (cursor + lookAhead + 1 > input.length()) {
            return -1;//no match string
        }

        String lookAheadBuffer = input.substring(cursor, cursor + lookAhead + 1);
		return KMP.search(lookAheadBuffer, searchBuffer);
    }
	/**
	 * Take compressed input as a text string, decompress it, and return it as a
	 * text string.
	 */
	public String decompress(String compressed) {
		StringBuilder stringBuilder = new StringBuilder();
        int cursor = 0;
        //generate the list of tuples
        List<Tuple> tuples = buildListTuple(compressed);
        for (Tuple tuple : tuples) {
            if (tuple.offset == 0 && tuple.length == 0) {
                stringBuilder.append(tuple.nextChar);
                cursor++;
            } else {
                stringBuilder.append(stringBuilder.substring(cursor - tuple.offset, cursor - tuple.offset + tuple.length));
                cursor += tuple.length;
                if (tuple.nextChar != null)
                    stringBuilder.append(tuple.nextChar);
                cursor++;
            }
        }

        return stringBuilder.toString();
    }

    private List<Tuple> buildListTuple(String compressed) {
        Scanner scan = new Scanner(new BufferedReader(new StringReader(compressed)));
        scan.useDelimiter("\\]");
        List<Tuple> tuples = new ArrayList<>();
        while (scan.hasNext()) {
            String str = scan.next();
            tuples.add(new Tuple(str));
        }
        scan.close();
        return tuples;
	}

	/**
	 * The getInformation method is here for your convenience, you don't need to
	 * fill it in if you don't want to. It is called on every run and its return
	 * value is displayed on-screen. You can use this to print out any relevant
	 * information from your compression.
	 */
	public String getInformation() {
		return "";
	}
	public static void setWindowSize(int size) {
        slidingWindown_size = size;
    }

    public static int getWindowSize() {
        return slidingWindown_size;
    }
	
}
