package test;

public class SimpleTest {

	public static void main(String[] args) {
		testEsc();
	}
	static void testEsc(){
		char[] buf = {'\\','n'};
		String s = new String(buf);
		System.out.println(s.length());
		System.out.println(s);
	}
	static void testLjava(){
	}
	static void testNotInitCharArray(){
		char[] a = new char[10];
		System.out.println(a[2]);
		System.out.println((int)a[2]);
		System.out.println((int)' ');
		System.out.println(a[2] == ' ');
	}
	
	static void testIdLength(){
		int a1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111;
		a1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111 = 10;
		System.out.println(a1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111);
	}
	static void ioEnd(){
		System.out.println((int)(char) -1);
	}
}
