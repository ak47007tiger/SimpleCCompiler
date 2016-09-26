package test;

import base.Constant;

public class TestReg {

	public static void main(String[] args) {
		testBracket();
	}
	static void testIdReg(){
		System.out.println("in".matches(Constant.id_reg));
	}
	static void testBracket(){
		String reg = "\\([a-z]+he";
		System.out.println("(gghe".matches(reg));
		String reg0 = "while\\((true){1}\\)\\{[a-z]+\\}";
		System.out.println("while(true){dosomething}".matches(reg0));
	}
}
