/*
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
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

package com.allen.media;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnPreparedListener;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class VideoActivity extends Activity implements SurfaceHolder.Callback{
    private static final String TAG = "VideoActivity";

    private SurfaceHolder mHolder;
    private String hls_url;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        SurfaceView videoView = (SurfaceView)findViewById(R.id.video_view);
        mHolder = videoView.getHolder();
        Intent intent = getIntent();
        if(intent != null){
        	 hls_url = intent.getStringExtra("url");
//        	 hls_url = "rtsp://218.24.21.4:554/vod/00000050280004310958.mpg?userid=sctest09&stbip=106.39.200.46&clienttype=1&mediaid=0000000030010003430069&ifcharge=1&time=20161017105233+08&life=172800&usersessionid=8441088&vcdnid=vcdn002&boid=002&srcboid=002&columnid=0500030000&backupagent=218.24.21.4:554&ctype=1&playtype=0&Drm=0&EpgId=epg_nj_003&programid=00000050280004310958&contname=&fathercont=&bp=0&authid=1247629168&tscnt=0&tstm=0&tsflow=0&ifpricereqsnd=1&stbid=00000000001911000003A089E42CA11D&nodelevel=3&terminalflag=1&bitrate=0&ottuserid=&usercharge=33C19A14DFC980268F76FF9E35D40C19";
        	 Log.e(TAG, "hls_url:" + hls_url);
        }
    }
        
    IjkMediaPlayer player = null;
    private void playStart() {
    	player = null;
		try {
			if (player == null) {
				player = new IjkMediaPlayer();
			}

			player.setDataSource(hls_url);
			player.setDisplay(mHolder);
			player.setOnPreparedListener(listener);
			player.prepareAsync();
			Log.e(TAG, "prepareAsync wait");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "playStart erro:"+e);
		}
	}
    
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
    	switch (keyCode) {
		case KeyEvent.KEYCODE_0:
			playStart();
			break;
		case KeyEvent.KEYCODE_BACK:
			player.stop();
			finish();
		default:
			break;
		}
    	return super.onKeyDown(keyCode, event);
    };
    
    OnPreparedListener listener = new OnPreparedListener() {

		@Override
		public void onPrepared(IMediaPlayer arg0) {
			// TODO Auto-generated method stub
			Log.e(TAG, "onPrepared");
			player.start();
		}
	};

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		mHolder = arg0;
		playStart();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		player.pause();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		player.stop();
	}
}
