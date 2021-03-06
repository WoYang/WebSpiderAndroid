package com.allen.media.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;

public class SkImageView extends BaseView implements OnFocusChangeListener{
	private final static String TAG = "SkImageView";
	private int duration_long = 0, duration_short = 0;
	private float origenalScale = 1.0f;
	private float neededScale = 1.08f;
	private ViewGroup parent = null;

	public SkImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setOnFocusChangeListener(this);
	}
	
	public SkImageView(Context context,String sceneId) {
		// TODO Auto-generated constructor stub
		super(context, sceneId);
		setOnFocusChangeListener(this);
	}

	public SkImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setOnFocusChangeListener(this);
	}
		
	@Override
	public void onFocusChange(View view, boolean hasfocus) {
		// TODO Auto-generated method stub
		parent = (ViewGroup) view.getParent();
		parent.setSelected(hasfocus);
		if (hasfocus) {
			changeItemState((View) view.getParent(), duration_long,
					origenalScale, neededScale);
		} else {
			changeItemState((View) view.getParent(), duration_long,
					neededScale, origenalScale);
		}
	}

	private void changeItemState(View view, int duration, float startRate,
			float endRate) {
		// TODO Auto-generated method stub
		animStart(view, duration, startRate, endRate);
	}

	public void animStart(View view, int duration, float startRate,
			float endRate) {
		// TODO Auto-generated method stub
		view.bringToFront();
		zoomAnim(view, startRate, endRate, startRate, endRate, duration, true,
				null);
	}

	private void zoomAnim(View view, float fromX, float toX, float fromY,
			float toY, int duration, boolean fillAfter,
			AnimationListener animListener) {
		// TODO Auto-generated method stub
		ScaleAnimation scaleAnimation = new ScaleAnimation(fromX, toX, fromY,
				toY, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(0);
		scaleAnimation.setFillAfter(fillAfter);
		scaleAnimation.setAnimationListener(animListener);
		view.startAnimation(scaleAnimation);
	}
}
