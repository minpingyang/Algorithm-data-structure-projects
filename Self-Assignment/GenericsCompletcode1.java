//The answer must have balanced parentesis
public class Exercise{
  public static void main(String [] arg){
    CustomList<Float> l1=new CustomList<Float>();
    CustomList<String> l2=new CustomList<String>();
    assert(l1.isEmpty());
    l1.add(3f);
    assert(l1.first()==3f);
    l1.add(4f);
    l1.add(5f);
    assert(l1.first()==3f);
    assert(l1.last()==5f);
    assert(l1.popLast());
    assert(l1.first()==3f);
    assert(l1.last()==4f);
    assert(l1.popLast());
    assert(l1.first()==3f);
    assert(l1.last()==3f);
    assert(l1.popLast());
    assert(l1.isEmpty());
    assert(!l1.popLast());
    try{l1.popLast();assert false;}
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
[???]