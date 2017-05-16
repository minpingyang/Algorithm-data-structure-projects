//The answer must have balanced parentesis and not use "+"
//Write a function that takes a map CityName -> Population
//and return the name of the most populated city 
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
public class Exercise{
  public static  String findMostPopulated(HashMap<String,Integer> that){
    int maxPop = 0;
    String city = "";
    
    if(that.isEmpty()){
        throw new java.util.NoSuchElementException();
    }
    for(int p:that.values()){
    	if(maxPop < p){
    		maxPop = p;
    	}
    }

    for(String s: that.keySet()){
    	if(that.get(s) == maxPop){
    		city = s;
    	}
    }
    
    return city;

  }

  public static void check(String expected,List<String>city,List<Integer>population){

    HashMap<String,Integer>map=new HashMap<>();
    
    for(int i=0;i<city.size();i++){ 
    	map.put(city.get(i),population.get(i));
    }
    System.out.println(" find:  "+findMostPopulated(map));

    assert expected.equals(findMostPopulated(map)):"expected "+expected+" but was "+findMostPopulated(map);
  }

	  public static void main(String [] arg)
	   {

		    check("Rome",Arrays.asList("Rome","Milan","Venice"),Arrays.asList(500,100,10));
		    check("Milan",Arrays.asList("Rome","Milan","Venice"),Arrays.asList(100,500,10));
		    check("Milan",Arrays.asList("Milan"),Arrays.asList(100));

			    try{
			    	findMostPopulated(new HashMap<String,Integer>());
			    	// System.out.println(" ERRO!!!!! ");
			    	assert false;
			    }
			    catch (java.util.NoSuchElementException e){

			    }
	  }


}