//The answer must have balanced parentesis
public class Exercise{
  public static void main(String [] arg){
    CustomList<Float> l1=new CustomList<Float>();
    CustomList<String> l2=new CustomList<String>();
    assert(l1.isEmpty());  ///pas
    l1.add(3f);    //add  3f
    assert(l1.first()==3f);// return first
    l1.add(4f); // 3f  4f   5f
    l1.add(5f); 
    assert(l1.first()==3f);    //3f
    assert(l1.last()==5f);  //5f
    assert(l1.popLast()); // 3 4
    assert(l1.first()==3f);//3
    assert(l1.last()==4f);//4
    assert(l1.popLast());
    assert(l1.first()==3f); //3
    assert(l1.last()==3f); //3
    assert(l1.popLast()); // empty  count ==0
    assert(l1.isEmpty());// true    
    assert(!l1.popLast());//fa      count == -1
    try{l1.popLast();assert false;} // count ==0
    catch(Throwable t){}
    assert(l1.isEmpty());
    l2.add("3f");
    assert(l2.first().equals("3f"));
    l2.add("4f");
    l2.add("5f");
    assert(l2.first().equals("3f"));
    assert(l2.last().equals("5f"));
    assert(l2.popLast());
    assert(l2.first().equals("3f"));
    assert(l2.last().equals("4f"));
    assert(l2.popLast());
    assert(l2.first().equals("3f"));
    assert(l2.last().equals("3f"));
    assert(l2.popLast());
    assert(l2.isEmpty());
    assert(!l2.popLast());
    try{l2.popLast();assert false;}
    catch(Throwable t){}
  }
}
class CustomList<T>{
  private T[] data;
  private int count;

  public CustomList(){
    data = (T[]) new Object[10];
    count = 0;
  }
  public void add(T item){
    if(item == null){
       throw new IllegalArgumentException();
    }else{
      data[count++] = item;

    }

  }

  public T first(){
    if(count > 0){
      return data[0];
    }else{
      throw new IllegalArgumentException();
    }
  }

  public T last(){
    if(count > 0){
      return data[count - 1];
    }else { 

      throw new IllegalArgumentException();
    }
  }
  public boolean popLast(){

    if(count > 0){
      data[count-1] = null;
      count--;
      return true;
    }else if(count == 0){ 
      count--;
      return false;
    }else{
      count =0;
      throw new IllegalArgumentException();
    }    
    
  }
  public boolean isEmpty(){
    if(count == 0){
      return true;
    }
    return false;
  }

}