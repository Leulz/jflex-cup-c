package util;

public class Logger {
	
	public static boolean VERBOSE = true;

	public static void print(String value) {
		if (VERBOSE)
			System.out.print(value);
	}
	
	public static void println(String value) {
		if (VERBOSE)
			System.out.println(value);
    }

}