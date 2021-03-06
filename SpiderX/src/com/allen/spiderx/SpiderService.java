/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.allen.spiderx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import us.codecraft.webmagic.Spider;

import com.allen.douyu.DouYuDirectoryProcesser;
import com.allen.douyu.DouYuItemProcesser;
import com.allen.douyu.DouYuPageProcesser;
import com.allen.douyu.Parse;
import com.allen.spiderx.aidl.SpiderCallBack;
import com.allen.spiderx.aidl.SpiderX;
import com.allen.spiderx.callback.ResponseCallback;
import com.allen.spiderx.utils.FileUtils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.PowerManager;
import android.os.RemoteException;
import android.util.Log;

// BEGIN_INCLUDE(service)
public class SpiderService extends Service {

	private final static String TAG = "SpiderService";

	public final static int TYPE_DOUYU_DIRECTORY = 0;
	public final static int TYPE_DOUYU_PAGE = TYPE_DOUYU_DIRECTORY + 1;
	public final static int TYPE_DOUYU_ITEM = TYPE_DOUYU_DIRECTORY + 2;
	private SpiderCallBack mCallback;
	private String mFilePath;
	
	public class LocalBinder extends SpiderX.Stub {

		@Override
		public int startProcess(int type, String url, String ext)
				throws RemoteException {
			// TODO Auto-generated method stub
			switch (type) {
			case TYPE_DOUYU_DIRECTORY:
				Spider.create(new DouYuDirectoryProcesser())
						.addUrl(DouYuDirectoryProcesser.REQUEST_URL)
						.pipeline(
								new Parse(mFilePath+"/www.douyu.com/",
										callback)).thread(5).run();
				break;
			case TYPE_DOUYU_PAGE:
				Spider.create(new DouYuPageProcesser())
						.addUrl(url)
						.pipeline(
								new Parse(mFilePath+"/www.douyu.com/",
										callback)).thread(5).run();
				break;
			case TYPE_DOUYU_ITEM:
				if (url.contains("www.douyu.com")) {
					// moblie use m.douyu.com for 302
					url = url.replace("www.douyu.com/",
							"m.douyu.com/html5/live?roomId=");
				}
				Spider.create(new DouYuItemProcesser())
						.addUrl(url)
						.pipeline(
								new Parse(mFilePath+"/www.douyu.com/",
										callback)).thread(5).run();
				break;
			default:
				break;
			}
			return 0;
		}

		@Override
		public void setSpiderCallBack(SpiderCallBack callbcak)
				throws RemoteException {
			// TODO Auto-generated method stub
			mCallback = callbcak;
		}
	}

	ResponseCallback callback = new ResponseCallback() {

		@Override
		public void onResponse(int type, String requestUrl, String fileUrl) {
			// TODO Auto-generated method stub
			FileUtils.chmodFile(fileUrl);

			try {
				if (mCallback != null) {
					mCallback.onCallBack(type, requestUrl, fileUrl);
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	};

	@Override
	public void onCreate() {
		Log.i(TAG, " service start ");
		try {
			mFilePath = getApplication().getApplicationInfo().dataDir + "/files";
			FileUtils.checkDir2Make(mFilePath);
		} catch (Exception e) {
			Log.i(TAG, " creat file dir fail "+e);
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "Received start id " + startId + ": " + intent);
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "bindintent :" + intent);
		return new LocalBinder();
	}

	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onRebind :" + intent);
		super.onRebind(intent);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i(TAG, " onUnbind ");
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, " onDestroy ");
	}
}
// END_INCLUDE(service)
