package swen221.shapedrawer.shapes;



public class ShapeComposition implements Shape {
	
	private final Shape inputShape1;
	private final Shape inputShape2;
	private final char operator;
	//three characters specify different operator of shape expression
	private static final char OPERATOR_UNION = '+';
	private static final char OPERATOR_INTERSECTION = '&';
	private static final char OPERATOR_DIFFERENCE = '-';
	
	public ShapeComposition(Shape inputShape1,Shape inputShape2,Character operator) 
	{
		this.inputShape1 =inputShape1;
		this.inputShape2 = inputShape2;
		if(operator != OPERATOR_DIFFERENCE && operator != OPERATOR_INTERSECTION
				&& operator != OPERATOR_UNION){
			throw new IllegalArgumentException("invalid operator");
		}
		this.operator = operator;
	}
	
	@Override
	public boolean contains(int x, int y) {
		if(operator == OPERATOR_DIFFERENCE)
		{
			return inputShape1.contains(x, y)&&!inputShape2.contains(x, y);	
		}else if(operator == OPERATOR_UNION)
		{
			return inputShape1.contains(x, y)||inputShape2.contains(x, y);	
		}else
		{
			return inputShape1.contains(x, y)&&inputShape2.contains(x, y);
		}
	}

	@Override
	public Rectangle boundingBox() {
		Rectangle boundingBox1 = inputShape1.boundingBox();
		Rectangle boundingBox2 = inputShape2.boundingBox();
		if(boundingBox1 == null && boundingBox2 == null)
		{
			return null;
		}else if(boundingBox1 == null && boundingBox2 != null)
		{
			if(operator == OPERATOR_UNION){
				return boundingBox2;
			}else{
				return null;
			}
		}else if(boundingBox1 != null && boundingBox2 == null)
		{
			if(operator != OPERATOR_INTERSECTION){
				return boundingBox1;
			}else{
				return null;
			}
		}
		//boudaries for input shape 1 and shape 2
		int leftBodary1 = boundingBox1.getX();
		int rightBodary1 = leftBodary1 + boundingBox1.getWidth();
		int topBodary1 = boundingBox1.getY();
		int bottomBodary1 = topBodary1 + boundingBox1.getHeight();
		
		int leftBodary2 = boundingBox2.getX();
		int rightBodary2 = leftBodary2 + boundingBox2.getWidth();
		int topBodary2 = boundingBox2.getY();
		int bottomBodary2 = topBodary2 + boundingBox2.getHeight();
		//
		int leftMost = Math.min(leftBodary1, leftBodary2);
		int rightMost = Math.max(rightBodary1, rightBodary2);
		int bottomMost = Math.max(bottomBodary1, bottomBodary2);
		int topMost = Math.min(topBodary1, topBodary2);
		if (operator == OPERATOR_DIFFERENCE) {
			return boundingBox1;
		}else if (operator == OPERATOR_UNION) {
			return new Rectangle(leftMost,topMost,rightMost-leftMost,bottomMost-topMost);
		}else{
			// intersection is null
			if(rightBodary1 < leftBodary2 || leftBodary1 > rightBodary2
					|| bottomBodary1 < topBodary2 || topBodary1 > bottomBodary2){
				return null;
			}
			//else
			//four boundaries of the intersection shape
			int newLeftBodary = Math.max(leftBodary1, leftBodary2);
			int newRightBodary = Math.min(rightBodary1, rightBodary2);
			int newBottomBodary = Math.min(bottomBodary1, bottomBodary2);
			int newTopBodary = Math.max(topBodary1, topBodary2);
			return new Rectangle(newLeftBodary, newTopBodary, newRightBodary- newLeftBodary, newBottomBodary-newTopBodary);
		}
	}

}
