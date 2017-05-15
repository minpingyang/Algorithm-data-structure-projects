//The answer must have balanced parentesis and not use "+"
//Using "findMostPopulated" as defined in the former question, write a function that takes a map CityName -> Population
//and return the list of cities, orderd by population
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
public class Exercise{
  public static  String findMostPopulated( HashMap<String,Integer> that){
    /*omitted*/
    }
  public static  List<String> orderByPopulation( HashMap<String,Integer> that){
      List<String> temp = new ArrayList<String>();
      int maxPop = 0;
      int minPop = Integer.MAX_VALUE;
      int secondP = 0;
      String lastCity = "";
      String secondCity = "";
    
      if(that.isEmpty()){
        temp.add(lastCity);
        return temp;
      }

      for(int p:that.values()){
        if(maxPop < p){
          maxPop = p;
        }
        if(minPop > p){
          minPop = p;
        }
      }


      for(int p:that.values()){
        if(p != maxPop && p != minPop){
          secondP = p;
        }
      }

      for(String s: that.keySet()){

        if(that.get(s) == maxPop){
          temp.add(s); 
          int size = that.keySet().size();
          if(size == 1){
            return temp;
          }
        }

        if(that.get(s) == secondP){
          secondCity = s;
        }
        if(that.get(s) == minPop){
            lastCity = s;
        }
      }
      temp.add(secondCity);
      temp.add(lastCity);
      return temp;
  }
  public static void check( List<String>expected, List<String>city, List<Integer>population){
    HashMap<String,Integer>map=new HashMap<>();
    for(int i=0;i<city.size();i++){ map.put(city.get(i),population.get(i));}
    List<String>result=orderByPopulation(map);
    assert new ArrayList<String>(expected).equals(result):
      "expected "+expected+" but was "+result;
  }
  public static void main(String [] arg){
    check(Arrays.asList("Rome","Milan","Venice"),
      Arrays.asList("Rome","Milan","Venice"),
      Arrays.asList(500,100,10));
    check(Arrays.asList("Milan","Rome","Venice"),
      Arrays.asList("Rome","Milan","Venice"),
      Arrays.asList(100,500,10));
    check(Arrays.asList("Milan"),
      Arrays.asList("Milan"),
      Arrays.asList(100));
    check(new ArrayList<String>(),
      new ArrayList<String>(),
      new ArrayList<Integer>());
    /*omitted*/
  }
}