package swen221.shapedrawer.shapes;

import static org.junit.Assert.fail;
/*
 * Main class is used to debug
 * */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		 TODO Auto-generated method stub
		
		String input="";
		input = "draw [10,10,150,150] #000000";
		Canvas canvas = new Interpreter(input).run();        

		canvas.show();
		
		
	
		
	}

}
