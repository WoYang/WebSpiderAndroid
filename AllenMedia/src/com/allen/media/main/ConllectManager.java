package com.allen.media.main;

import com.allen.media.callback.ResponseCallback;
import com.allen.media.util.JsonParseUtil;

import android.content.Context;
import android.util.Log;

public class ConllectManager {
	public final static String TAG = "ConllectManager";
	private Context mContext;
	
	public ConllectManager(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}
	
	public void requestDirectoryConfig(String url,
			final ResponseCallback callback) {
		try {
			Log.d(TAG, "LauncherConfig url:" + url);
			JsonParseUtil parseUtil = JsonParseUtil.getInstance(mContext);
			DirectoryLists struct = parseUtil.parseJson(DirectoryLists.class,
					url);
			ConfigParser config = ConfigParser.getInstance();
			config.setDirectoryLists(struct);
			if (callback != null && struct != null) {
				callback.onResponse("200",null);
			}
		} catch (Exception e) {
			Log.d(TAG, "parse fail:" + e);
			if (callback != null) {
				callback.onResponse("fail",null);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> void requestObjectConfig(Class<T> paramClass,String url,
			final ResponseCallback callback) {
		try {
			Log.d(TAG, "LauncherConfig url:" + url);
			JsonParseUtil parseUtil = JsonParseUtil.getInstance(mContext);
			T struct = parseUtil.parseJson(paramClass,
					url);
			if (callback != null && struct != null) {
				callback.onResponse("200",struct);
			}
		} catch (Exception e) {
			Log.d(TAG, "parse fail:" + e);
			if (callback != null) {
				callback.onResponse("fail",null);
			}
		}
	}
}
