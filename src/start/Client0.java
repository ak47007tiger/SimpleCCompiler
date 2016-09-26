package start;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import morphologyanalyze.LscAnalyzer0;

public class Client0 {
	public static void main(String[] args) {
		File file = new File(System.getenv("user.dir"), "files0/l.sc");
		File lscFile = new File(System.getenv("user.dir"),
				"files0/lscResultFile0.sc");
		LscAnalyzer0 analyzer0 = null;
		try {
			analyzer0 = new LscAnalyzer0();
			analyzer0.init(file, Charset.forName("utf-8"), lscFile);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			analyzer0.scanFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
