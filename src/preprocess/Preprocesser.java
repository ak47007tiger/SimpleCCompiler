package preprocess;

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

public class Preprocesser {
	char[] escs = { '\n', '\r', '\\', '\'' };
	String[] keys = "\\n,\\r,\\\\,\\'".split(",");

	public Preprocesser() {
	}

	public void preprocesser(File file, Charset cs, File outFile)
			throws UnsupportedEncodingException, FileNotFoundException {
		File noRemarkFile = new File(System.getProperty("user.dir"),"files/temp_noremark.sc");
		removeAllRemark(file, cs, noRemarkFile);
		replaceCharWithNumber(noRemarkFile, cs, outFile);
	}

	public void removeAllRemark(File file, Charset cs, File outFile)
			throws UnsupportedEncodingException, FileNotFoundException {
		BufferedReader reader = null;
		PrintStream printStream = null;
		printStream = new PrintStream(new FileOutputStream(outFile), false,
				"utf-8");
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(
				file), cs));
		try {
			int ic;
			// find a string match '//'
			while (-1 != (ic = reader.read())) {
				char c = (char) ic;
				if ('/' == c) {
					c = (char) reader.read();
					if ('/' == c) {
						// find remark end
						while ('\n' != reader.read()) {
						}
						printStream.print('\r');
						printStream.print('\n');
					} else {
						printStream.print('/');
						printStream.print(c);
					}
				} else {
					printStream.print(c);
				}
			}
			printStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != printStream) {
				printStream.close();
			}
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void replaceCharWithNumber(File file, Charset cs, File outFile)
			throws UnsupportedEncodingException, FileNotFoundException {
		BufferedReader reader = null;
		PrintStream printStream = null;
		printStream = new PrintStream(new FileOutputStream(outFile), false,
				"utf-8");
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(
				file), cs));
		try {
			int ic;
			// find a string match '.' or '\.'
			boolean hasGetLeft = false;
			while (-1 != (ic = reader.read())) {
				char c = (char) ic;
				if ('\'' == c) {// find left or right '
					if (hasGetLeft) {// get next character of left '
						// for find next left '
						hasGetLeft = false;
						// get next character
						continue;
					} else {// get left '
						c = (char) reader.read();
						if ('\\' == c) {// match '\.'
							c = (char) reader.read();
							char[] buf = { '\\', c };
							String key = new String(buf);
							int index = 0;
							while (!keys[index].equals(key)) {
								index++;
							}
							printStream.print((int) escs[index]);
						} else {// match '.'
							printStream.print((int) c);
						}
						hasGetLeft = true;
					}
				} else {
					printStream.print(c);
				}
			}
			printStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != printStream) {
				printStream.close();
			}
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
