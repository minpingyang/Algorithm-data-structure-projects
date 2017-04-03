/**
this test shown if the exception is caught, then the programme will just throw the exception and still run the remaing code
**/
class ExceptionDemo1{
	public static void main(String[] args) {
		System.out.println();
		int[] arr = {1 , 2};
		//arr = null;
		print (-1, 0, arr);
		System.out.println("Over");
	}
	public static void print(int x, int y, int[] arr){
		try{
			//put the code which potentially has a exception in try block 
			System.out.println( arr[1] );
			System.out.println( arr[x / y] );
		} catch (ArithmeticException e) {
			//show the information of exception
			e.toString();
			e.getMessage();
			e.printStackTrace();
			System.out.println("Arithmetic Exception");

		} catch(ArrayIndexOutOfBoundsException e){
			e.toString();
			e.getMessage();
			e.printStackTrace();
			System.out.println("Array Index OutOf Bounds Exception");
		} catch(NullPointerException e){
			e.toString();
			e.getMessage();
			e.printStackTrace();
			System.out.println("Null Pointer Exception Exception");
		}
		System.out.println("function done");
		
	}
}
// 1.	程序中有多个语句可能发生异常，可以都放在try语句中。并匹配对个catch语句处理。
// 2.	如果异常被catch匹配上，接着执行try{}catch(){} 后的语句。没有匹配上程序停止。
// 3.	try中多个异常同时出现，只会处理第一条出现异常的一句，剩余的异常不再处理。
// 4.	使用多态机制处理异常。
// 1.	程序中多态语句出现不同异常，出现了多个catch语句。简化处理（相当于急诊）。
// 2.	使用多态，使用这些异常的父类进行接收。（父类引用接收子类对象）


// 多个catch语句之间的执行顺序。
// 1.	是进行顺序执行，从上到下。
// 2.	如果多个catch 内的异常有子父类关系。
// 1.	子类异常在上，父类在最下。编译通过运行没有问题
// 2.	父类异常在上，子类在下，编译不通过。(因为父类可以将子类的异常处理，子类的catch处理不到)。
// 3.	多个异常要按照子类和父类顺序进行catch
