package io.github.ctlove0523.commons;

public class Predications {

	public static String stringNotNull(String str, String message) {
		if (str == null || str.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
		return str;
	}
}
