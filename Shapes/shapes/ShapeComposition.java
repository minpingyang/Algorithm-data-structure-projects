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
		// TODO Auto-generated method stub
		return null;
	}

}
