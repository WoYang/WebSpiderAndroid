package com.allen.media;

import com.allen.media.MainApplication.EventBus;
import com.allen.media.callback.ResponseCallback;
import com.allen.media.main.ConllectManager;
import com.allen.media.main.DirectoryModel;
import com.allen.media.main.FilmLists;
import com.allen.media.main.FilmModel;
import com.allen.media.main.Video;
import com.allen.media.view.AsynImageView;
import com.allen.media.view.WaitAnim;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class SubActivity extends Activity {
	private final static String TAG = "SubActivity";
	private String sub_directory = "";
	private GridView list;
	private final static int PARSE_FINISH = 0;
	private final static int VIDEO_PLAY_ACTION = 1;
	private WaitAnim dialog;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PARSE_FINISH:
				try {
					resetConfig((FilmLists) msg.obj);
				} catch (Exception e) {
					Log.d(TAG, "" + e);
				}
				break;
			case VIDEO_PLAY_ACTION:
				try {
					Video video = (Video) msg.obj;
					String hls = video.getHls_url();
					Intent palyer = new Intent();
					palyer.putExtra("url", hls);
					palyer.setClass(SubActivity.this, VideoActivity.class);
					startActivity(palyer);
				} catch (Exception e) {
					Log.d(TAG, "" + e);
				}
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dialog = new WaitAnim(this);
		dialog.show();
		Intent data = getIntent();
		if (data != null) {
			sub_directory = data.getStringExtra("url");
			Log.d(TAG, "sub_directory:" + sub_directory);
		}
		initView();
		MainApplication.registerEventBus(ebus);
		initParams();
	}

	private void initView() {
		// TODO Auto-generated method stub
		list = (GridView) findViewById(R.id.grid);
	}

	private void initParams() {
		// TODO Auto-generated method stub
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				try {
					if (MainApplication.mSpiderX != null) {
						MainApplication.mSpiderX.startProcess(
								MainApplication.TYPE_DOUYU_PAGE, sub_directory,
								null);
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					Log.d(TAG, " " + e);
				}
				return null;
			}
		}.execute();
	}

	ResponseCallback callback = new ResponseCallback() {

		@Override
		public void onResponse(String status, Object object) {
			// TODO Auto-generated method stub
			if (status.equals("200")) {
				Log.d(TAG, "callback success");
				if(dialog != null){
					dialog.dismiss();
				}
				Message msg = mHandler.obtainMessage(PARSE_FINISH);
				msg.obj = object;
				mHandler.sendMessage(msg);
			} else {
				// parse fail
				Log.d(TAG, "callback fail");
			}
		}
	};
	
	ResponseCallback videoInfoCallback = new ResponseCallback() {

		@Override
		public void onResponse(String status, Object object) {
			// TODO Auto-generated method stub
			if (status.equals("200")) {
				Log.d(TAG, "callback success");
				if(dialog != null){
					dialog.dismiss();
				}
				Message msg = mHandler.obtainMessage(VIDEO_PLAY_ACTION);
				msg.obj = object;
				mHandler.sendMessage(msg);
			} else {
				// parse fail
				Log.d(TAG, "callback fail");
			}
		}
	};

	public void resetConfig(FilmLists films) {
		if (list != null) {
			VideoAdapter adapter = new VideoAdapter(films);
			list.setAdapter(adapter);
			list.setOnItemClickListener(clickListener);
		}
	}

	public class VideoAdapter extends BaseAdapter {
		private FilmLists list;

		public VideoAdapter(FilmLists list) {
			// TODO Auto-generated constructor stub
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (list != null && list.getInfos() != null) {
				return list.getInfos().size();
			}
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			if (list != null && list.getInfos() != null) {
				return list.getInfos().get(arg0);
			}
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			if (list != null && list.getInfos() != null) {
				return arg0;
			}
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			View view = View.inflate(SubActivity.this, R.layout.gridview_item,
					null);
			if (list != null && list.getInfos() != null) {
				if (arg0 > list.getInfos().size()) {

				} else {
					FilmModel video = list.getInfos().get(arg0);
					AsynImageView img = (AsynImageView) view
							.findViewById(R.id.filmimage);
					img.postImage(video.getImg());
					TextView title = (TextView) view
							.findViewById(R.id.filmtitle);
					title.setText(video.getEllipsis());
				}
			}
			return view;
		}
	}

	OnItemClickListener clickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if (list != null) {
				String url = ((FilmModel) list.getItemAtPosition(arg2))
						.getUrl();
				String rid = ((FilmModel) list.getItemAtPosition(arg2))
						.getRid();
				// request by room id
				final String request_rid = url.substring(0,
						url.lastIndexOf("/") + 1)
						+ rid;
				Log.d(TAG, "request_rid:" + request_rid);
				new AsyncTask<Void, Void, String>() {

					@Override
					protected String doInBackground(Void... arg0) {
						try {
							if (MainApplication.mSpiderX != null) {
								MainApplication.mSpiderX.startProcess(
										MainApplication.TYPE_DOUYU_ITEM,
										request_rid, null);
							}
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
					}
				}.execute();
			}
		}
	};

	EventBus ebus = new EventBus() {

		@Override
		public void onEvent(int type, String url, String ext) {
			// TODO Auto-generated method stub
			if (MainApplication.TYPE_DOUYU_PAGE == type) {
				final String filePath = ext;
				new AsyncTask<Void, Void, String>() {

					@Override
					protected String doInBackground(Void... arg0) {
						// TODO Auto-generated method stub
						ConllectManager manager = new ConllectManager(
								getApplicationContext());
						manager.requestObjectConfig(FilmLists.class,filePath, callback);
						return null;
					}
				}.execute();
			} else if (MainApplication.TYPE_DOUYU_ITEM == type) {
				final String filePath = ext;
				new AsyncTask<Void, Void, String>() {

					@Override
					protected String doInBackground(Void... arg0) {
						// TODO Auto-generated method stub
						ConllectManager manager = new ConllectManager(
								getApplicationContext());
						manager.requestObjectConfig(Video.class,filePath, videoInfoCallback);
						return null;
					}
				}.execute();
			}
		}
	};
	
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if(KeyEvent.KEYCODE_BACK == keyCode){
			MainApplication.unregisterEventBus(ebus);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	};
}
