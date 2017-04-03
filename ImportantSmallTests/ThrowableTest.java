class ExceptionDemo1{
	public static void main(String[] args) {
		Throwable able = new Throwable("sick...");
		System.out.println("1"+able.toString()+"1end");//output the name of exception
		System.out.println("2"+able.getMessage()+"2end"); //info 
		able.printStackTrace(); 
	}
	// public void div(int a, int b){
	// 	int c = a/b;
	// 	System.out.println("~~~~~~~~~"+c);
	// }
}