package com.allen.media.view;

import com.allen.media.MainApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class AsynImageView extends ImageView {
	private final static String TAG = "AsynImageView";
	private Context mContext;

	Handler mCallbackHandler = new Handler() {

		public void handleMessage(Message msg) {
			Log.d(TAG, "msg.obj handleMessage");
			switch (msg.what) {
			case 0:
				if (msg.obj != null) {
					Log.d(TAG, "msg.obj set viewid" + getId());
					setImageBitmap((Bitmap) msg.obj);
				}
				break;
			default:
				break;
			}
		};
	};

	public AsynImageView(Context context) {
		super(context);
		this.mContext = context;
	}

	public AsynImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}

	public AsynImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
	}

	public void postImage(String url) {
		if (url != null && !url.equals("")) {
			ImageLoader.getInstance().loadImage(url,
					MainApplication.options, imageLoading);
		}
	}
	
	ImageLoadingListener imageLoading = new ImageLoadingListener() {

		@Override
		public void onLoadingStarted(String arg0, View arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			// TODO Auto-generated method stub
			if (mCallbackHandler != null) {
				Log.d(TAG, "hander send");
				Message msg = mCallbackHandler.obtainMessage();
				msg.what = 0;
				msg.obj = arg2;
				mCallbackHandler.sendMessage(msg);
			}
		}

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			// TODO Auto-generated method stub

		}
	};
}
