/**
user-defined Exception
**/
//import java.io.*;
// 案例：模拟吃饭没带钱的问题
// 1.	定义吃饭功能，需要钱。（例如：eat(double money)）
// 2.	如果钱不够是不能吃放，有异常。
// 3.	自定义NoMoneyException();继承Exception 提供有参无参构造，调用父类有参构造初始化。at 方法进行判断，小于10块，throw NoMoneyException("钱不够"); 
// 4.	eat 方法进行声明，throws NoMoneyException
// 5.	如果钱不够老板要处理。调用者进行处理。try{}catch(){} 。


//first way: throws exception
class NoMoneyException extends Exception{
	NoMoneyException(){

	}
	NoMoneyException(String message){
		super(message);
	}
}
class ExceptionDemo1{
	public static void main(String[] args) {
		System.out.println();

		try{
			eat(0);
		}catch (NoMoneyException e) {
			System.out.println("start work");
		}
		
	}
	public static void eat(double money) throws NoMoneyException{
	if (money < 10){
		throw new NoMoneyException("Not Enough money---------");
	}
	System.out.println("eat fried noodles");
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








