package swen221.shapedrawer.shapes;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input="";
		input = "fill [10,10,50,50] #000000";
		                
		
		Interpreter interpreter = new Interpreter(input);
		System.out.println(interpreter.run().toString());
	}

}
