package start;

import grammaranalyze.GrammarAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import morphologyanalyze.LscAnalyzer;
import preprocess.Preprocesser;

public class Client {

	public static void main(String[] args) {
		File file = new File(System.getenv("user.dir"), "files/expression.sc");
		File preprocessFile = new File(System.getenv("user.dir"),
				"files/preprocessFile.sc");
		File lscFile = new File(System.getenv("user.dir"),
				"files/lscResultFile.sc");

		try {
			new Preprocesser().preprocesser(file, Charset.forName("utf-8"),
					preprocessFile);
		} catch (UnsupportedEncodingException | FileNotFoundException e1) {
			e1.printStackTrace();
			return;
		}

		try {
			new LscAnalyzer().scanFile(preprocessFile,
					Charset.forName("utf-8"), lscFile);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			new GrammarAnalyzer().onlyGrammarParse(lscFile, Charset.forName("utf-8"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
	}
}
