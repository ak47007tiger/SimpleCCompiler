package morphologyanalyze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class LscAnalyzer0 {
	File inputFile;
	File outPutFile;
	Charset cs;
	Reader reader;
	PrintWriter out;

	public void init(File inputFile, Charset cs, File outPutFile)
			throws FileNotFoundException, UnsupportedEncodingException {
		this.inputFile = inputFile;
		this.cs = cs;
		this.outPutFile = outPutFile;
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(
				inputFile), cs));
		out = new PrintWriter(outPutFile, cs.name());
	}

	char[] split = { ' ', '\n' };
	char[] skip = { '\t', '\r' };
	char[] end = { ',', '(',')','{','}', '=', ';', '+', '-', '*', '/', '>', '<' };

	public void scanFile() throws IOException {
		String word;
		while (null != (word = getWord())) {
			System.out.println(word);
			if (null == testPrintWord(word)) {
				out.println("unknow:" + word);
				break;
			}
		}
		out.flush();
		out.close();
		reader.close();
	}

	public void pWord(String abs, String word) {
		out.println(abs + ":" + word);
	}

	public Word testPrintWord(String word) {
		switch (word) {
		case "while":
			pWord("b8", word);
			return Word._while;
		case "if":
			pWord("b9", word);
			return Word._if;
		case "char":
			pWord("c1", word);
			return Word._char;
		case "int":
			pWord("c2", word);
			return Word._int;
		case "void":
			pWord("c3", word);
			return Word._void;
		case "=":
			pWord("c4", word);
			return Word._give;
		case ";":
			pWord("c5", word);
			return Word._end;
		case "(":
			pWord("c6", word);
			return Word._leftkh;
		case ")":
			pWord("c7", word);
			return Word._rightkh;
		case ",":
			pWord("c8", word);
			return Word._jux;
		case "{":
			pWord("c9", word);
			return Word._leftbrace;
		case "}":
			pWord("d1", word);
			return Word._rightbrace;
		}
		if (word.matches("[a-zA-Z]+[a-zA-Z0-9]*")) {
			pWord("d1", word);
			return Word._id;
		}
		if (word.matches("[1-9]+[0-9]*")) {
			pWord("d3", word);
			return Word._number;
		}
		if(word.matches("\\+|\\-|\\*|/|>|<")){
			pWord("d4", word);
			return Word._operator;
		}
		return null;
	}

	String getWord() throws IOException {
		StringBuffer stringBuffer = new StringBuffer();
		int ic;
		while (-1 != (ic = getChar())) {
			char c = (char) ic;
			if ('\t' == c || '\r' == c) {
				continue;
			}
			if (isSplit(c)) {
				if(stringBuffer.length() > 0){
					return stringBuffer.toString();
				}else{
					continue;
				}
			}
			if (isEnd(c)) {
				if (stringBuffer.length() == 0) {
					stringBuffer.append(c);
				} else {
					reader.reset();
				}
				return stringBuffer.toString();
			}
			stringBuffer.append(c);
		}
		return null;
	}

	boolean isEnd(char c) {
		return isInArray(c, end);
	}

	boolean isSplit(char c) {
		return isInArray(c, split);
	}

	boolean isInArray(char c, char[] array) {
		for (char each : array) {
			if (c == each) {
				return true;
			}
		}
		return false;
	}

	enum Word {
		_while, _if, _char, _int, _void, _id, _give, _number, _end, _leftkh, _rightkh, _jux, _leftbrace, _rightbrace,
		_operator
	}

	int getChar() throws IOException {
		reader.mark(8);
		int ic = reader.read();
		return ic;
	}

}
