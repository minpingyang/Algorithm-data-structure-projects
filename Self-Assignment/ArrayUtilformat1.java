//The answer must have balanced parentesis
class ArrayUtil{

  String opening="["; String closing="]"; String separator=", ";


  public final String format(Object[]array){

    String result=opening;

    if(array.length==0)
      return result+closing;

    for(int i=0;i<array.length-1;i++){
      result+=array[i]+separator;
    }

    return result+array[array.length-1]+closing;
  }
}
public class Exercise{
  public static void main(String [] arg){
    ArrayUtil u= new ArrayUtil(){
      String separator="; ";


    };
    assert "[1; 2; 3]".equals(u.format(new Integer[]{1,2,3}));
    assert "[4; 5; 6]".equals(u.format(new Integer[]{4,5,6}));
    assert "[7; 8; 9]".equals(u.format(new Integer[]{7,8,9}));
  }
}
