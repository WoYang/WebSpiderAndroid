package com.allen.spiderx.utils;

import java.io.File;
import android.content.Context;
import android.util.Log;

public class FileUtils {
	private final static String TAG = "FileUtils";

	private Context mContext;

	public FileUtils(Context context) {
		this.mContext = context;
	}

	public static boolean checkDir2Make(String dir) {
		try {
			File f = new File(dir);
			if (!f.exists()) {
				f.mkdirs();
				// chmod the dir r w
				Process p = Runtime.getRuntime().exec(
						"busybox chmod 777 -R " + dir);
				p.waitFor();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "erro :" + e);
		}
		return false;
	}

	public static void chmodFile(String filepath) {
		try {
			Runtime runtime = Runtime.getRuntime();
			Process p = runtime.exec("chmod 777 " + filepath);
			p.waitFor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
}
