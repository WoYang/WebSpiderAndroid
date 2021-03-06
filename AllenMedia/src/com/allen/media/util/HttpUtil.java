package com.allen.media.util;


import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	
	public static HttpURLConnection createDownloadConnection(String strUrl, int offset) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			if (offset > 0) {
				conn.setRequestProperty("RANGE","bytes=" + String.valueOf(offset) + "-");
			}
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.connect();
		} catch (Exception e) {
			return null;
		}
		return conn;
	}	
}
