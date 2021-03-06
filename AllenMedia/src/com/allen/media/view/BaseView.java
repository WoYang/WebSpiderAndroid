package com.allen.media.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ViewFlipper;

public class BaseView extends ViewFlipper implements OnClickListener {
	private final static String TAG = "BaseView";

	private boolean defaultFocus = false;
	Handler mCallbackHandler = new Handler() {

		public void handleMessage(Message msg) {
			Log.d(TAG, "msg.obj handleMessage");
			switch (msg.what) {
			case 0:
				if (msg.obj != null) {
					Log.d(TAG, "msg.obj set viewid" + getId());
					try {
						setBackground(new BitmapDrawable((Bitmap) msg.obj));
					} catch (Exception e) {
						// TODO: handle exception
						Log.d(TAG, "setBackground erro:" + e);
					}
				}
				break;
			default:
				break;
			}
		};
	};

	public BaseView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setOnClickListener(this);
		initParams(null);
	}

	public BaseView(Context context, String sceneId) {
		super(context);
		// TODO Auto-generated constructor stub
		setOnClickListener(this);
		initParams(null);
	}

	public BaseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setOnClickListener(this);
		initParams(attrs);
	}

	private void initParams(AttributeSet attrs) {
		obtainInfoFromNet();
	}

	private void obtainInfoFromNet() {
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	}
}
