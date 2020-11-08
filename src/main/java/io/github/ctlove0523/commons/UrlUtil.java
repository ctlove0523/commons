package io.github.ctlove0523.commons;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UrlUtil {
	public static String urlQueryParametersFormat(String path, Map<String, String> queryParas) {
		if (queryParas == null || queryParas.isEmpty()) {
			return path;
		}

		String queryString = queryParas.entrySet().stream()
				.sorted(new Comparator<Map.Entry<String, String>>() {
					@Override
					public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
						return o1.getKey().compareTo(o2.getKey());
					}
				})
				.map(new Function<Map.Entry<String, String>, String>() {
					@Override
					public String apply(Map.Entry<String, String> entry) {
						return entry.getKey() + "=" + entry.getValue();
					}
				}).collect(Collectors.joining("&"));

		return path + "?" + queryString;

	}

	public static String urlPathFormat(String path, String... pathVariables) {
		if (path == null || path.isEmpty() || pathVariables.length == 0) {
			return path;
		}

		String url = path;
		for (int i = 0; i < pathVariables.length; i++) {
			url = url.replaceFirst("\\{([^}]*)}", pathVariables[i]);
		}

		return url;
	}
}
