class Point { 
  int x; int y;
  public Point(int x, int y) {this.x = x; this.y = y;}
  
  public boolean equals(Object other){
    if(other instanceof Point){
      Point temp = (Point) other;
     if( this.x == temp.x){
      return true;
      }
    }
    

    return false;
  }
}
public class Exercise{

  public static void main(String [] arg){
    Point p1 = new Point(1,2);
    Point p2 = new Point(1,2);
    assert p1.equals(p2);
    p2.x=10;
    assert !p1.equals(p2);
    assert !p1.equals(new Object());
    assert !p1.equals(new String());
    assert !p1.equals(Point.class);
  }
}