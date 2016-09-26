package morphologyanalyze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import base.Constant;
import base.Type;

public class LscAnalyzer {

	char[] endChars = { ' ', ';', '+', '-', '*', '/', '{', '}', '(', ')' };

	String[] ops = { "+", "-", "*", "/", "<", ">", "<<", ">>", "==", "=", "&" };

	String[] typeWords = { "char", "int", "char*", "int*", "void" };
	String[] flowWords = { "if", "while", "{", "}", "(", ")", "return", ";",
			"," };

	int readAheadLimit = 64;
	int idLength = 128;

	public void scanFile(File file, Charset cs, File lscFile)
			throws FileNotFoundException, UnsupportedEncodingException {
		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(
				file), cs));
		PrintStream out = null;
		out = new PrintStream(new FileOutputStream(lscFile), false, "utf-8");
		int ic;
		char[] buf = new char[idLength];
		int fillIndex = 0;
		// repeat read a word by rules and print the type of word
		try {
			while (-1 != (ic = reader.read())) {
				char c = (char) ic;
				if(';' == c){
					System.out.println(c);
				}
				if (fillIndex == idLength) {
					System.out.println("identifier is too long");
					break;
				}
				// to get next character of current word
				if (fillIndex > 0) {
					buf[fillIndex] = c;
					fillIndex++;
					String word = new String(buf, 0, fillIndex);
					Type wordType;
					if (null == getWordType(word)) {
						fillIndex--;
						word = new String(buf, 0, fillIndex);
						wordType = getWordType(word);
						if (null == wordType) {
							pType("unknown", word, out);
							break;
						} else {
							switch (wordType) {
							case identifier:
								pType(wordType.name(), word, out);
								break;
							case typeIdentifier:
								pType(word, word, out);
								break;
							case number:
								pType(wordType.name(), word, out);
								break;
							case flowDivider:
								pType(word, word, out);
								break;
							case operator:
								pType(word, word, out);
								break;
							default:
								break;
							}
							// set read position for next word
							fillIndex = 0;
							reader.reset();
						}
					}
				} else {
					// search next start of word
					if (c != ' ' && c != '\n' && c != '\r' && c != '\t') {
						buf[fillIndex] = c;
						fillIndex++;
					}
				}
				reader.mark(readAheadLimit);
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	boolean canGetWord(String word, char c) {
		// word.length > 0 is always true
		if (word.length() == 1) {
			if (isOpWord(word)) {
				return true;
			}
		} else if (word.length() == 2) {

		} else {

		}
		return false;
	}

	Type getWordType(String word) {
		if (isOpWord(word)) {
			return Type.operator;
		}
		if (isFlowWord(word)) {
			return Type.flowDivider;
		}
		if (isTypeWord(word)) {
			return Type.typeIdentifier;
		}
		if (isIdWord(word)) {
			return Type.identifier;
		}
		if (isNumberWord(word)) {
			return Type.number;
		}
		return null;
	}

	void pType(String type, String word, PrintStream out) {
		out.println(String.format("%s:%s", type, word));
	}

	boolean isFlowWord(String word) {
		return isInArray(word, flowWords);
	}

	boolean isTypeWord(String word) {
		return isInArray(word, typeWords);
	}

	boolean isIdWord(String word) {
		return word.matches(Constant.id_reg);
	}

	boolean isInArray(String word, String[] array) {
		for (String s : array) {
			if (word.equals(s)) {
				return true;
			}
		}
		return false;
	}

	boolean isOpWord(String word) {
		return isInArray(word, ops);
	}

	boolean isNumberWord(String word) {
		return word.matches(Constant.num_reg);
	}
}
