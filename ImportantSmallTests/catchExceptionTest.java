/**
this test shown if the exception is caught, then the programme will just throw the exception and still run the remaing code
**/
class ExceptionDemo1{
	public static void main(String[] args) {
		div(2,0);
		System.out.println("over");
	}
	public static void div(int x, int y){
		try{
			//put the code which potentially has a exception in try block 
			System.out.println( x / y);
		} catch (ArithmeticException e) {
			//show the information of exception
			System.out.println(e.toString());
			System.out.println(e.getMessage());
				e.printStackTrace();
				System.out.println("divisor can't be zero");

		}
		System.out.println("division operation");
		
	}
}