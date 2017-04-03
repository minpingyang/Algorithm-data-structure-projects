/**
Declare keyword "throws" in the function which potential exists exception
**/
//import java.io.*;


//first way: throws exception
class ExceptionDemo1{
	public static void main(String[] args) throws InterruptedException{
		Object ob = new Object();
		ob.wait();
		
	}

}







/*********second way: try catch*********/

// class ExceptionDemo1{
// 	public static void main(String[] args) {
// 		Object ob = new Object();
// 		try{
// 			ob.wait();
// 		}catch (InterruptedException e) {
// 			e.printStackTrace();
// 			System.out.println("over");
// 		}
		
// 	}

// }

// 总结
// 1.	try语句不能单独存在，可以和catch、finally组成 try...catch...finally、try...catch、try...finally三种结构。
// 2.	catch语句可以有一个或多个，finally语句最多一个，try、catch、finally这三个关键字均不能单独使用。
// 3.	try、catch、finally三个代码块中变量的作用域分别独立而不能相互访问。如果要在三个块中都可以访问，则需要将变量定义到这些块的外面。
// 4.	多个catch块时候，Java虚拟机会匹配其中一个异常类或其子类，就执行这个catch块，而不会再执行别的catch块。（子类在上，父类在下）。
// 5.	throw语句后不允许有紧跟其他语句，因为这些没有机会执行。
// 6.	如果一个方法调用了另外一个声明抛出异常的方法，那么这个方法要么处理异常，要么声明抛出。

