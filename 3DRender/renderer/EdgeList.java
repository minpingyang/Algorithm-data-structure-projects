package code.renderer;

/**
 * EdgeList should store the data for the edge list of a single polygon in your
 * scene. A few method stubs have been provided so that it can be tested, but
 * you'll need to fill in all the details.
 *
 * You'll probably want to add some setters as well as getters or, for example,
 * an addRow(y, xLeft, xRight, zLeft, zRight) method.
 */
public class EdgeList {
	private int startY, endY;
	private float[] xLeft,xRight,zLeft,zRight;
	public EdgeList(int startY, int endY) 
	{
		// TODO fill this in.
		this.startY =startY;
		this.endY = endY;
		int changeY= endY - startY;
		
		//??
		xLeft = new float[changeY]; 
		zLeft = new float[changeY]; 
		xRight = new float[changeY]; 
		zRight = new float[changeY]; 
		
		for(int i = 0; i < changeY; i++)
		{
			xLeft[i] = Float.POSITIVE_INFINITY;
			zLeft[i] = Float.POSITIVE_INFINITY;
			xRight[i] = Float.NEGATIVE_INFINITY;
			zRight[i] = Float.POSITIVE_INFINITY;
			
		}
		
	}

	
	
	
	
	public int getStartY() {	
		return startY;
	}

	

	
	public int getEndY() {
		return endY;
	}

	public float getXLeft(int y) {
		return xLeft[y];
	}

	public float getXRight(int y) {
		return xRight[y];
	}

	public float getZLeft(int y) {
		return zLeft[y];
	}

	public float getZRight(int y) {
		return zRight[y];
	}
	//?
	public void addRow (int y, float x, float z){
		if (x <= this.xLeft[y]) {
			this.xLeft[y] = x;
			this.zLeft[y] = z;
		}
		if (x>=this.xRight[y]) {
			this.xRight[y] = x;
			this.zRight[y] = z;
		}
	}
	
}

// code for comp261 assignments
