package com.allen.media;

import java.util.ArrayList;
import java.util.List;

import com.allen.media.view.WaitAnim;
import com.allen.spiderx.aidl.SpiderCallBack;
import com.allen.spiderx.aidl.SpiderX;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MainApplication extends Application{
		
	private final static String TAG = "MainApplication";

	//keep with SpiderService.java
	public final static int TYPE_DOUYU_DIRECTORY = 0;
	public final static int TYPE_DOUYU_PAGE = TYPE_DOUYU_DIRECTORY + 1;
	public final static int TYPE_DOUYU_ITEM = TYPE_DOUYU_DIRECTORY + 2;
	
	public final static int TYPE_SERVICEBINDER_SUCESS = 1000;
	
	public static DisplayImageOptions options;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		doBindService();
		initImageLoaderConfig();
	}
	
	private void initImageLoaderConfig(){
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
		ImageLoader.getInstance().init(config);
		options =  new DisplayImageOptions.Builder().cacheInMemory(true).build(); 
	}
	
	public static SpiderX mSpiderX;
	void doBindService() {
		Intent intent = new Intent("com.allen.spiderx.binder.action");
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}

	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			// TODO Auto-generated method stub
			try {
				mSpiderX = SpiderX.Stub.asInterface(service);
				if(mSpiderX != null && spiderCallback != null){
					mSpiderX.setSpiderCallBack(spiderCallback);
				}
				for(EventBus handler:mEventBus){
					handler.onEvent(TYPE_SERVICEBINDER_SUCESS,"","");
				}
			} catch (RemoteException e) {
				Log.d(TAG, " "+e);
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub

		}
	};
	
	public SpiderCallBack.Stub spiderCallback = new SpiderCallBack.Stub() {
		
		@Override
		public int onCallBack(int type, String url, String ext)
				throws RemoteException {
			// TODO Auto-generated method stub
			for(EventBus handler:mEventBus){
				handler.onEvent(type,url,ext);
			}
			return 0;
		}
	};
	
	public interface EventBus{
		 public abstract void onEvent(int type, String url, String ext);
	}
	public static List<EventBus> mEventBus = new ArrayList<EventBus>();
	public static void registerEventBus(EventBus event){
		mEventBus.add(event);
	}
	public static void unregisterEventBus(EventBus event){
		mEventBus.remove(event);
	}
}
