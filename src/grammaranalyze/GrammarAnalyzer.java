package grammaranalyze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Stack;

public class GrammarAnalyzer {
	RulesHelper rulesHelper = new RulesHelper();
	public void onlyGrammarParse(File lscFile, Charset cs)
			throws FileNotFoundException {
		Stack<String> symbolStack = new Stack<String>();
		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(
				lscFile), cs));
		String firstWordOfEachLine;
		try {
			int lineNo = 0;
			while (null != (firstWordOfEachLine = getFirstWordOfLine(reader
					.readLine()))) {
				lineNo ++;
				symbolStack.push(firstWordOfEachLine);
				reduceTop(symbolStack);
			}
			if(symbolStack.size() == 1){
				System.out.println("grammar is ok");
			}else{
				/*System.out.println("lineNo: " + lineNo);
				Stack<String> temp = new Stack<String>();
				while(!symbolStack.isEmpty()){
					temp.push(symbolStack.pop());
				}
				while(!temp.isEmpty()){
					System.out.print(temp.pop());
					System.out.println();
				}*/
				System.out.println("grammar is wrong");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//if find max symbol order can be reduce then reduce once else restore symbol stack
	void reduceTop(Stack<String> symbolStack) {
		if(rulesHelper.isStatement(symbolStack)){
			return;
		}
		Stack<String> typeSymbolStack = new Stack<String>();
		while(!symbolStack.isEmpty()){
			String top = symbolStack.pop();
			typeSymbolStack.push(top);
		}
		while(!typeSymbolStack.isEmpty()){
			String symbol;
			if(null != (symbol = rulesHelper.testReduceable(typeSymbolStack))){
				typeSymbolStack.clear();
				symbolStack.push(symbol);
				reduceTop(symbolStack);
			}else{
				symbolStack.push(typeSymbolStack.pop());
			}
		}
	}

	public String getFirstWordOfLine(String line) {
		if (null == line) {
			return null;
		}
		int endIndex = line.indexOf(' ');
		if (-1 == endIndex) {
			endIndex = line.indexOf(':');
		}
		if (-1 != endIndex) {
			return line.substring(0, endIndex);
		}
		return null;
	}
}
