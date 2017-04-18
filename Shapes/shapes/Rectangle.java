package swen221.shapedrawer.shapes;

public class Rectangle implements Shape {
	private final int x;
	private final int y;
	private final int width;
	private final int height;
/*
 * constructor
 * @param x
 * @param y
 * @param width
 * @param height
 * */
public Rectangle(int x,int y,int width,int height) 
{	
	
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
	
	
	
	@Override
	public boolean contains(int x, int y) {
		boolean flag = (x >= this.x)
					  &&(x <= this.x + width)
					  && (y >= this.y) 
					  && (y < this.y + height);
		return flag ;
	}

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
