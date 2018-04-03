package com.example.controller.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class GsonHelper {
	private static final Gson gson;

	static {
		gson = new Gson();
	}

	public static String objectToJson(Object obj) {
		return gson.toJson(obj);
	}

	public static <T> T jsonToObject(String jobj, Class<T> resBean) {
		try {
			return gson.fromJson(jobj, resBean);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object jsonToObject(String jobj, Type type) {
		return gson.fromJson(jobj, type);
	}
}
