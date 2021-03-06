package com.allen.media.view;

import com.allen.media.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class WaitAnim extends ProgressDialog {

	private AnimationDrawable mAnimation;
	private Context mContext;
	private ImageView mImageView;
	private String mLoadingTip;
	private TextView mLoadingTv;
	private int count = 0;
	private String oldLoadingTip;
	private int mResid;
	private Runnable animRunable;

	public WaitAnim(Context context) {
		this(context, context.getResources().getString(R.string.loading), R.anim.wait_anim);
	}

	public WaitAnim(Context context, String content, int id) {
		super(context);
		setCanceledOnTouchOutside(true);
		this.mContext = context;
		this.mLoadingTip = content;
		this.mResid = id;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}

	private void initView() {
		setContentView(R.layout.amin);
		mImageView = (ImageView) findViewById(R.id.loadingImg);
		mLoadingTv = (TextView) findViewById(R.id.loadingText);
	}

	private void initData() {
		mImageView.setBackgroundResource(mResid);
		mAnimation = (AnimationDrawable) mImageView.getBackground();
	}

	public void setContent(String str) {
		mLoadingTv.setText(str);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		mAnimation.start();
		mLoadingTv.setText(mLoadingTip);
	}
	
	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
		mAnimation.stop();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
