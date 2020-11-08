package io.github.ctlove0523.commons;

import java.util.Map;
import java.util.stream.Collectors;

public class UrlUtil {
	public static String urlQueryParametersFormat(String path, Map<String, String> queryParas) {
		if (queryParas == null || queryParas.isEmpty()) {
			return path;
		}

		String queryString = queryParas.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.map(entry -> entry.getKey() + "=" + entry.getValue())
				.collect(Collectors.joining("&"));

		return path + "?" + queryString;

	}

	public static String urlPathFormat(String path, String... pathVariables) {
		if (path == null || path.isEmpty() || pathVariables.length == 0) {
			return path;
		}

		String url = path;
		for (String pathVariable : pathVariables) {
			url = url.replaceFirst("\\{([^}]*)}", pathVariable);
		}

		return url;
	}
}
