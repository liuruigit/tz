package com.jhl.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * json操作类
 */
public class JsonUtil {

	/**
	 * 将jsonp转换成json
	 * @param jsonp
	 * @return
	 */
	public static String jsonpToJson(String jsonp) {
		if (StringUtils.isBlank(jsonp)) {
			return null;
		}
		int index = jsonp.indexOf("(");
		int lastIndex = jsonp.indexOf(")");
		String json = null;
		if (index != -1 && lastIndex != -1) {
			json = jsonp.substring(index + 1, lastIndex);
		}
		return json;
	}


	/**
	 * json转换成java对象
	 * @param json
	 * @param clazz
	 * @param <T>
	 * @return
	 * @throws com.fasterxml.jackson.core.JsonParseException
	 * @throws com.fasterxml.jackson.databind.JsonMappingException
	 * @throws java.io.IOException
	 */
	public static <T> T jsonToBean(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		T t = mapper.readValue(json, clazz);
		return t;
	}


	/**
	 * 将java对象转换成json
	 * @param t
	 * @param <T>
	 * @return
	 * @throws com.fasterxml.jackson.core.JsonGenerationException
	 * @throws com.fasterxml.jackson.databind.JsonMappingException
	 * @throws java.io.IOException
	 */
	public static <T> String beanToJson(T t) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(t);
		return json;
	}


	/**
	 * 将json转换成list
	 * @param jsonStr
	 * @param collectionClass
	 * @param elementClass
	 * @param <T>
	 * @return
	 * @throws com.fasterxml.jackson.core.JsonParseException
	 * @throws com.fasterxml.jackson.databind.JsonMappingException
	 * @throws java.io.IOException
	 */
	public static <T> List<T> jsonToList(String jsonStr, Class<?> collectionClass, Class<T> elementClass) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClass);
		List<T> list = mapper.readValue(jsonStr, javaType);
		return list;
	}


	/**
	 * 将json转换成map
	 * @param jsonStr
	 * @param collectionClass
	 * @param keyClass
	 * @param valueClass
	 * @param <K>
	 * @param <V>
	 * @return
	 * @throws com.fasterxml.jackson.core.JsonParseException
	 * @throws com.fasterxml.jackson.databind.JsonMappingException
	 * @throws java.io.IOException
	 */
	public static <K, V> Map<K, V> jsonToMap(String jsonStr, Class<?> collectionClass, Class<K> keyClass, Class<V> valueClass) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, keyClass, valueClass);
		Map<K, V> map = mapper.readValue(jsonStr, javaType);
		return map;
	}


}
