package swen221.shapedrawer.shapes;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input="";
		input = "fill [10,10,50,50] #000000";
		Canvas canvas = new Interpreter(input).run();        

		canvas.show();
		

		
		//System.out.println(canvas.toString());
	}

}
