package base;

public interface Constant {
	//key word
	String if_key = "if";
	String else_key = "else";
	String while_key = "while";
	String add_key = "+";
	String sub_key = "-";
	String mul_key = "*";
	String dev_key = "/";
	String less_key = "<";
	String more_key = ">";
	String equls_key = "==";
	
	String key_word = "if|else|while|\\+|\\-|\\/|<|>|==|\\{|\\}|\\[|\\]|\\(|\\)";
	//identifier
	String id_reg = "[a-zA-Z]{1}[a-zA-Z_]*";
	
	//number 
	String num_reg = "[0-9]+|[0-9]+\\.[0-9]+";
	String int_reg = "[0-9]+";
	String float_reg = "[0-9]+\\.[0-9]+";
	//expression
	String str_exp_reg = ".*";
	String bool_exp_reg = "true|false";
	//method
	String methodcall_reg = "[a-zA-Z]{1}[a-zA-Z_]*\\(\\)";
	//base type
	/*
	 * char 16
	 * int 32
	 * float 32
	 * double 64
	 * long 64
	 * array 64
	 */
}
