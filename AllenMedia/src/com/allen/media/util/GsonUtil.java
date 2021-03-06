package com.allen.media.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
	private static Gson gson = null;

	public static Gson getGsonInstance() {
		if (gson == null) {
			GsonBuilder localGsonBuilder = new GsonBuilder();
			localGsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
			localGsonBuilder.disableHtmlEscaping();
			gson = localGsonBuilder.create();
		}
		return gson;
	}
}
