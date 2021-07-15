package com.udacity.jwdnd.course1.cloudstorage.config;

import java.util.HashMap;
import java.util.Map;

public class ConfigStore {
	private static Map<String, String> configMap = new HashMap<>();

	public static void addValue(String key, String value) {
		if (configMap.containsKey(key)) {
			configMap.putIfAbsent(key, value);
		}
	}

	public static String getValue(String key) {
		return configMap.get(key);
	}
}
