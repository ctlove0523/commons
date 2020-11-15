package io.github.ctlove0523.commons;

public class Predications {

	public static String stringNotNull(String str, String message) {
		if (str == null || str.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
		return str;
	}

	public static int checkNetworkPort(int port, String message) {
		if (port < 0 || port > 65535) {
			throw new IllegalArgumentException(message);
		}
		return port;
	}
}
