package io.github.ctlove0523.commons;

import java.util.Collection;
import java.util.Collections;

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

	public static <T> T notNull(T element, String message) {
		if (element == null) {
			throw new IllegalArgumentException(message);
		}

		return element;
	}

	public static <T> Collection<T> notEmpty(Collection<T> collection, String message) {
		if (collection == null || collection.isEmpty()) {
			throw new IllegalArgumentException(message);
		}

		return collection;
	}
}
