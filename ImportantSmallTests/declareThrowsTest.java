/**
Declare keyword "throws" in the function which potential exists exception
**/
//import java.io.*;

class ExceptionDemo1{
	public static void main(String[] args) {
		try{
			div(2,0);
		}catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("OVER");
	}
	//throws declare there is a potential exception in the function
	public static void div(int x,int y) throws Exception{
		if( y == 0){
			//after the keyword of throw is that accept the concrete exception object
			throw new Exception("DIVISION CAN'T BE 0");
		}
		System.out.println( x / y);
		System.out.println("DIVISION OPERATION");
			
	}
}





// 功能内部不想处理，或者处理不了。就抛出使用throw new Exception("除数不能为0"); 
// 进行抛出。抛出后需要在函数上进行声明，
// 告知调用函数者，我有异常，你需要处理如果函数上不进行throws 声明，编译会报错
// 例如：未报告的异常 java.lang.Exception；
// 必须对其进行捕捉或声明以便抛出throw  new Exception("除数不能为0");


// /**
// Application of using catch
// **/
// import java.io.*;

// class ExceptionDemo1{
// 	public static void main(String[] args) throws Exception {
// 		read();
// 	}
// 	public static void read() throws Exception{
// 		FileInputStream in = null;
// 		try{
// 			in = new FileInputStream("E:\\a.txt");
// 		}catch(Exception e){
// 			in = new FileInputStream("E:\\b.txt");
// 		}
// 		int data = 0;
// 		while ((data = in.read()) != -1) {
// 			System.out.print((char)data);
// 		}
// 	}
// }

