package swen221.shapedrawer.shapes;

/***
 * Rectangle class is an implementation class of Shape interface
 * 
 * This class has the coordinator <i>x, y</i> of left-top position of the rectangle shape
 * <i>width</i>     --- >represent how wide the rectangle is <br>
 * <i>height</i>     --- >represent how high the rectangle is <br>
 * 
 * @author minping yang 
 * *****/
public class Rectangle implements Shape {
	private final int x;
	private final int y;
	private final int width;
	private final int height;
/*
 * construct a rectangle with passing the coordinator
 * of left-top position, the width and 
 * the height of the rectangle
 * 
 * @param x
 * @param y
 * @param width
 * @param height
 * */
public Rectangle(int x,int y,int width,int height) 
{	
	//check all invalid case, and throw exception first
	if(x < 0 )
	{
		throw new IllegalArgumentException("x should not less than 0");
	}
	if(y < 0 )
	{
		throw new IllegalArgumentException("y should not less than 0");
	}
	
	if(width <= 0)
	{
		throw new IllegalArgumentException("width should not <= 0");
		
	}
	if(height <= 0)
	{
		throw new IllegalArgumentException("height should not <= 0");
	}
	
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
	
}
	
	
/**
 * The method implements the <i>contains</i> method of Shape interface
 * @param x 
 * @param y
 * @return a boolean value to determine whether or not the 
 * given point is contained within this shape.
 * **/

	@Override
	public boolean contains(int x, int y) {
		boolean flag = (x >= this.x)
					  &&(x < this.x + width)
					  && (y >= this.y) 
					  && (y < this.y + height);
		return flag ;
	}
	/**
	 * The method implements the <i>boundingBox</i> method of Shape interface
	 * A bounding box is a box that will fit around the entire shape and, hence, can be used to
	 * determine the maximum width and height of the shape. This is useful when
	 * it comes to drawing the shape!
	 * 
	 * **/
	@Override
	public Rectangle boundingBox() {
		return this;
	}



	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}



	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}



	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}



	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}



	

}
