package io.github.ctlove0523.commons;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class UrlUtilTest {

	@Test
	public void test_urlPathFormat_success() {
		String path = "/api/v1/namespaces/{namespace}/services/{name}";
		String formatPath = UrlUtil.urlPathFormat(path, "default", "test");
		Assert.assertEquals(formatPath,"/api/v1/namespaces/default/services/test");
	}

	@Test
	public void test_urlQueryParametersFormat_success() {
		String path = "/api/v1/namespaces/default/services/test";
		Map<String, String> queryParas = new HashMap<>();
		queryParas.put("pretty", "true");
		queryParas.put("limit", "10");
		String url = UrlUtil.urlQueryParametersFormat(path, queryParas);

		Assert.assertEquals(url,"/api/v1/namespaces/default/services/test?limit=10&pretty=true");

	}
}
