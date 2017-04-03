/**
this test shown any function does not work if there is an exception
**/
class ExceptionDemo1{
	public static void main(String[] args) {
		div(2,0);
		System.out.println("over");
	}
	public static void div(int a, int b){
		// int c = a/b;
		System.out.println(a / b);
		System.out.println("DIV METHOD");
		
	}
}