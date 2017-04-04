/**
user-defined Exception
**/
//import java.io.*;


//first way: throws exception
class NoIpException extends Exception{
	NoIpException(){

	}
	NoIpException(String message){
		super(message);
	}
}
class ExceptionDemo1{
	public static void main(String[] args) throws NoIpException{
		System.out.println();
		String ip = "192.168.10.252";
		ip = null;
		try{
			Lb(ip);
		}catch (NoIpException e) {
			System.out.println("programme ends");
		}
		
	}
	public static void Lb(String ip) throws NoIpException{
	if (ip == null){
		throw new NoIpException("offline---------");
	}
	System.out.println("wake up, course starts");
}

}


// 问题：现实中会出现新的病，就需要新的描述。
// 	分析： java的面向对象思想将程序中出现的特有问题进行封装。
// 	案例:  定义功能模拟凌波登录。(例如：lb(String ip))需要接收ip地址
// 1.	当没有ip地址时，需要进行异常处理。
// 1. 当ip地址为null是需要throw new Exception("无法获取ip"); 
//                	2. 但Exception是个上层父类，这里应该抛出更具体的子类。
// 				3. 可以自定义异常
// 2.	自定义描述没有IP地址的异常（NoIpException）。
// 1. 和sun的异常体系产生关系。继承Exception类，自定义异常类名也要规范，结尾加上Exception,便于阅读







