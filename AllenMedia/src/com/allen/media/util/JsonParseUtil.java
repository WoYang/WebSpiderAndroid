package com.allen.media.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import com.google.gson.Gson;
import android.content.Context;
import android.util.Log;

public class JsonParseUtil {
	private static final String TAG = "JsonParseUtil";
	private static JsonParseUtil instance;
	private Context context;

	public static JsonParseUtil getInstance(Context paramContext) {
		if (instance == null) {
			instance = new JsonParseUtil(paramContext);
		}
		return instance;
	}

	private JsonParseUtil(Context paramContext) {
		this.context = paramContext;
	}

	private String readDownloadJson(String paramString) {
		InputStream stream = null;
		String result = null;
		HttpURLConnection connection = null;
		try {
			// download
			connection = HttpUtil.createDownloadConnection(paramString, 0);
			if (connection == null) {
				throw new Exception("cann not creat connection");
			}
			stream = connection.getInputStream();
			result = StringUtil.Inputstr2Str(stream, null);
		} catch (Exception e) {
			Log.d(TAG, "" + e);
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (Exception e) {
				Log.d(TAG, "" + e);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> T parseJson(Class<T> paramClass, String paramString) {
		String str = null;
		if(paramString == null){
			return null;
		}
		if(paramString.contains(".zip")){
			//hlj huawei  support json response & zip response
		}else{
			if(paramString.startsWith("http:") || paramString.startsWith("https:")){
				str = readDownloadJson(paramString);
				Log.d(TAG, "parseDownloadJson --> parse json by download, urlPath=" + paramString);
			}else{
				str = readLocalJson(paramString);
				Log.d(TAG, "parseDownloadJson --> parse json by local, LocalPath="+ paramString);
			}
		}
		if (!StringUtil.isEmpty(str)) {
			Gson gson = new Gson();
			Object localObject = gson.fromJson(str, paramClass);
			if (localObject != null) {
				return (T) localObject;
			}
		}
		return null;
	}
	
	private String readLocalJson(String paramString) {
		String content = null;
		FileInputStream filein = null;
		try {	
			filein = new FileInputStream(paramString);
			content = StringUtil.Inputstr2Str(filein, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "readLocalJson fail :" + e);
		} finally {
			try {
				if (filein != null) {
					filein.close();
				}
			} catch (IOException e) {
				
			}
		}
		return content;
	}
}
