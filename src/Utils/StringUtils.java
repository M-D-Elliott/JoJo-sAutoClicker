package Utils;

public final class StringUtils {
	public static String repeatString(int n, String s) {
		return String.format("%0" + n + "d", 0).replace("0", s);
	}
	
	public static boolean empty(String s) {
		return s.toString() != null && s.toString().length() == 0;
	}
}
