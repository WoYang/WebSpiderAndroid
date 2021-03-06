package com.allen.media;

import com.allen.media.MainApplication.EventBus;
import com.allen.media.callback.ResponseCallback;
import com.allen.media.main.ConfigParser;
import com.allen.media.main.ConllectManager;
import com.allen.media.main.DirectoryLists;
import com.allen.media.main.DirectoryModel;
import com.allen.media.view.AsynImageView;
import com.allen.media.view.WaitAnim;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private final static String TAG = "MainActivity";

	private GridView list;
	private final static int PARSE_FINISH = 0;
	private boolean alreadyInit = false;
	private WaitAnim dialog;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PARSE_FINISH:
				resetConfig();
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
		dialog =new WaitAnim(this);  
		dialog.show(); 
		MainApplication.registerEventBus(event);
		initView();
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
						alreadyInit = true;
						MainApplication.mSpiderX.startProcess(MainApplication.TYPE_DOUYU_DIRECTORY,
								"https://www.douyu.com/directory", null);
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
				mHandler.sendMessage(mHandler.obtainMessage(PARSE_FINISH));
				if(dialog != null){
					dialog.dismiss();
				}
			} else {
				// parse fail
				Log.d(TAG, "callback fail");
			}
		}
	};

	public void resetConfig() {
		if (list != null) {
			ConfigParser config = ConfigParser.getInstance();
			DirectoryLists mConfig = config.getDirectoryLists();
			VideoAdapter adapter = new VideoAdapter(mConfig);
			list.setAdapter(adapter);
			list.setOnItemClickListener(clickListener);
		}
	}

	public class VideoAdapter extends BaseAdapter {
		private DirectoryLists list;

		public VideoAdapter(DirectoryLists list) {
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
			View view = View.inflate(MainActivity.this, R.layout.gridview_item,
					null);
			if (list != null && list.getInfos() != null) {
				if (arg0 > list.getInfos().size()) {

				} else {
					DirectoryModel video = list.getInfos().get(arg0);
					AsynImageView img = (AsynImageView) view
							.findViewById(R.id.filmimage);
					img.postImage(video.getImg());
					TextView title = (TextView) view
							.findViewById(R.id.filmtitle);
					title.setText(video.getTitle());
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
				String url = ((DirectoryModel) list.getItemAtPosition(arg2)).getUrl();
				Intent intent = new Intent();
				intent.putExtra("url", url);
				intent.setClass(MainActivity.this, SubActivity.class);
				startActivity(intent);
			}
		}
	};
	
	EventBus event = new EventBus() {
		
		@Override
		public void onEvent(int type, String url, String ext) {
			// TODO Auto-generated method stub
			Log.d(TAG, "event:"+type);
			if(MainApplication.TYPE_SERVICEBINDER_SUCESS == type || !alreadyInit){
				initParams();
			}
			if(MainApplication.TYPE_DOUYU_DIRECTORY == type){
				final String filePath = ext;
				new AsyncTask<Void, Void, String>() {

					@Override
					protected String doInBackground(Void... arg0) {
						// TODO Auto-generated method stub
						ConllectManager manager = new ConllectManager(
								getApplicationContext());
						// for test
						manager.requestDirectoryConfig(filePath, callback);
						return null;
					}
				}.execute();
			}
		}
	};
}
