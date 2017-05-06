/*
* Test class for Findbugs custom detectors
*/

public class Test {

	//test for final (constant) variables not having all uppercase name
	public static final int number = 4;

	// test for special characters in method name
	public void f_o() {
		System.out.println("special chars in method name");
	}

	// test for long method name
	public void foooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo() {
		System.out.println("long method name");
	}

	// test for matching method names with different capitalization
	public void foo() {
		System.out.println("lowercase foo");
	}

	public void fOo() {
		System.out.println("uppercase foo");
	}
}