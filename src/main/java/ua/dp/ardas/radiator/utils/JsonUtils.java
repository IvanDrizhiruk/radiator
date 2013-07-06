package ua.dp.ardas.radiator.utils;

import java.lang.reflect.Type;

import org.apache.commons.lang.StringUtils;

import com.google.gson.GsonBuilder;

public final class JsonUtils {
	
	private JsonUtils() {}
	
	public static <T> String toJSON(final T o) {
		return toJSON(o, null);
	}

	public static <T> String toJSON(final T o, final String dateFormat) {
		GsonBuilder gb = new GsonBuilder();
		if (StringUtils.isNotBlank(dateFormat)) {
			gb = gb.setDateFormat(dateFormat);
		}
		return gb.create().toJson(o);
	}

	public static <T> T fromJSON(String json, Type type) {
		return JsonUtils.<T> fromJSON(json, type, null);
	}

	public static <T> T fromJSON(String json, Type type, final String dateFormat) {
		GsonBuilder gb = new GsonBuilder();
		if (null != dateFormat) {
			gb.setDateFormat(dateFormat);
		}
		return gb.create().<T> fromJson(json, type);
	}
}
