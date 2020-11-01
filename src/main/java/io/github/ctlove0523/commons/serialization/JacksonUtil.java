package io.github.ctlove0523.commons.serialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JacksonUtil {
	private static final Logger log = LoggerFactory.getLogger(JacksonUtil.class);
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	static {
		OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES);
		OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		OBJECT_MAPPER.registerModule(new JodaModule());
	}

	private JacksonUtil() {
		throw new UnsupportedOperationException("can not create JacksonUtil object");
	}

	public static ObjectMapper getObjectMapper() {
		return OBJECT_MAPPER;
	}

	public static <T> T json2Object(String jsonString,Class<T> clazz) {
		if (jsonString == null || clazz == null) {
			return null;
		}

		try {
			return OBJECT_MAPPER.readValue(jsonString, clazz);
		} catch (JsonProcessingException e) {
			log.error("json string to object failed ", e);
		}

		return null;
	}

	public static String object2Json(Object o) {
		if (o == null) {
			return "";
		}

		try {
			return OBJECT_MAPPER.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			log.error("object to json string failed ",e);
		}

		return "";
	}

}
