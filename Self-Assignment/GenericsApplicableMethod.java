import java.util.*;
public class Exercise{
  public static void main(String [] arg){
    List<String> sl = new ArrayList<String>();
    List<Integer> il = new ArrayList<Integer>();
    sl.add("foo");sl.add("bar");
    il.add(1);il.add(2);
    System.out.println(myToString(sl));
    System.out.println(myToString(il));
    // assert myToString(sl).equals("foobar");
    // assert myToString(il).equals("12");
  }
  static String myToString (List<?> list){
  	String result = "";
  	String temp ="";
  	boolean isInt = false;
  	for(Object t:list){
  		if(t instanceof Integer){
  			isInt = true;
  			temp = Object.toString(t);
  			result +=temp;
  		}
  	}
  	if(isInt){
  		return result;
  	}
   	for(String t:list){
   		String s = t;
   		result +=s;
   	}
   	return result;

  }
}
