package prepare;

public class StoreEsc {

	public static void main(String[] args) {
		char[] escs = {'\n','\r','\\','\''};
		String[] keys = "\\n,\\r,\\\\,\\'".split(",");
		String[] keys2 = "n,r,\\,'".split(",");
		for(int i = 0; i < keys.length; i++){
			System.out.println(keys2[i] + "=" + (int)escs[i]);
		}
	}
}
