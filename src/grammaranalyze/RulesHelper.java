package grammaranalyze;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class RulesHelper {
	Map<String,String> ruleTable = new HashMap<String, String>();
	public RulesHelper() {
		initRuleTable();
	}

	public String testReduceable(Stack<String> typeSymbolStack){
		StringBuffer sb = new StringBuffer();
		Stack<String> symbolStack = new Stack<String>();
		while(!typeSymbolStack.isEmpty()){
			String type = typeSymbolStack.pop();
			sb.append(type);
			symbolStack.push(type);
		}
		//restore types symbol stack
		while(!symbolStack.isEmpty()){
			typeSymbolStack.push(symbolStack.pop());
		}
		
		String symbolGroup = sb.toString();
		Iterator<String> iterator = ruleTable.keySet().iterator();
		while(iterator.hasNext()){
			String symbol = iterator.next();
			if(symbolGroup.matches(ruleTable.get(symbol))){
				System.out.println(symbol);
				return symbol;
			}
		}
		return null;
	}
	
	//reduce and push new symbol to stack
	public void reduce(Stack<String> typeSymbolStack) {
		
	}
	
	String statement_reg = "(statement)*((decFunction)|(defFunction)|(flowStatement))*(statement)*";
	String decFunction_reg = "typeIdentifieridentifier\\(typeIdentifieridentifier(,typeIdentifieridentifier)*\\);";
	String defFunction_reg = "typeIdentifieridentifier\\(((typeIdentifieridentifier(,typeIdentifieridentifier)*))\\)flowStatement";
	String decVariable_reg = "typeIdentifieridentifier;";
	String defVariable_reg = "(typeIdentifiergiveValStatement;)|(typeIdentifieridentifier=((expression)|(number));)";
	String flowStatement_reg = "\\{((decVariable)|(defVariable)|(giveValStatement;)|(callFunction)|(branchStatement)|(repeatStatement))((return((identifier)|(number))){0,1})\\}";
	String branchStatement_reg = "(if\\(expression\\)flowStatementelseflowStatement)|(if\\(expression\\)flowStatement)";
	String repeatStatement_reg = "while\\(expression\\)flowStatement";
	String giveValStatement_reg = "identifier=(expression|identifier);";
	String expression_reg = "((number)|(functionExpr)|(((expression)|(identifier))operator((expression)|(identifier))))";
	String functionExpr = "identifier\\(((void)|(identifier(,identifier)*))\\)";
	String callFunction_reg = "identifier\\(((void)|(identifier(,identifier)*))\\);";
	String typeIdentifier_reg = "(char)|(int)|(char\\*)|(int\\*)|(void)";
	String operator_reg = "\\+|\\-|\\*|/|>|<|(==)|(<<)|(>>)|&";

	String valtype_reg = "(callFunction)|(identifier)|(number)";
	String returnStatement_reg = "(returnexpression){0,1}";
	
	void initRuleTable() {
		ruleTable.put("statement", statement_reg);
		ruleTable.put("decFunction", decFunction_reg);
		ruleTable.put("defFunction", defFunction_reg);
		ruleTable.put("decVariable", decVariable_reg);
		ruleTable.put("defVariable", defVariable_reg);
		ruleTable.put("flowStatement", flowStatement_reg);
		ruleTable.put("branchStatement", branchStatement_reg);
		ruleTable.put("repeatStatement", repeatStatement_reg);
		ruleTable.put("giveValStatement", giveValStatement_reg);
		ruleTable.put("expression", expression_reg);
		ruleTable.put("callFunction", callFunction_reg);
		ruleTable.put("functionExpr", functionExpr);
		ruleTable.put("typeIdentifier", typeIdentifier_reg);
		ruleTable.put("operator", operator_reg);
	}
	
	void test(){
		String ie = "identifier";
		String reg = decFunction_reg;
		String s = "typeIdentifieridentifier(int*, identifier, ,, int*, identifier, ), ;";
		System.out.println("defVariable".matches(statement_reg));
	}
	public static void main(String[] args) {
		new RulesHelper().test();
	}

	public boolean isStatement(Stack<String> symbolStack) {
		return symbolStack.size() == 1 && symbolStack.peek().matches(statement_reg);
	}
}
