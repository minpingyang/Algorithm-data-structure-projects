import java.util.*;

class Point{ 
  int x;int y;
  Point(int x, int y){this.x=x;this.y=y;}
  public String toString(){return "["+x+","+y+"]";}
}
public class Exercise{

  public static void main(String [] arg){

    ArrayList<Point> ps=new ArrayList<Point>();
    ps.add(new Point(2,2));
    ps.add(new Point(1,2));
    ps.add(new Point(3,2));
    ps.add(new Point(2,5));

    Collections.sort(ps,new Comparator<Point>(){ 

    	public int compare(Point a, Point b){
    		int weight1 = 100*(a.x) + 10*(a.y);
    		int weight2 = 100*(b.x) + 10*(b.y);
    		if(weight2 > weight1){
    			return -1;
    		}else{
    			return 1;
    		}

    	}

    });

    assert(ps.toString().equals("[[1,2], [2,2], [2,5], [3,2]]")):ps.toString();
  }
}
