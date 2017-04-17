package swen221.shapedrawer.shapes;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input="";
		input = "x = [0,0,450,450]\ndraw x #010101\nz=[60,60,130,130]\nfill z #f1d3f3\n";
		                
		
		Interpreter interpreter = new Interpreter(input);
		System.out.println(interpreter.run().toString());
	}

}
