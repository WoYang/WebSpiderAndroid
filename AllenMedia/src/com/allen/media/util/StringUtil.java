package com.allen.media.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtil {

	public static boolean isEmpty(String paramString) {
		if ((paramString != null) && (!paramString.equals(""))) {
			return false;
		}
		return true;
	}
	
	public static boolean isHttpUrl(String paramString) {
		if ((paramString != null) && (paramString.contains("http:"))) {
			return true;
		}
		return false;
	}

	public static String Inputstr2Str(InputStream in, String encode) {
		String str = "";
		BufferedReader reader = null;
		try {
			if (encode == null || encode.equals("")) {
				encode = "utf-8";
			}
			reader = new BufferedReader(new InputStreamReader(in, encode));
			StringBuffer sb = new StringBuffer();
			while ((str = reader.readLine()) != null) {
				sb.append(str).append("\n");
			}
			str = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	
}
