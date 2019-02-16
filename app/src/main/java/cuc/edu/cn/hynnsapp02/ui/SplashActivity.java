package cuc.edu.cn.hynnsapp02.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import cuc.edu.cn.hynnsapp02.R;

public class SplashActivity extends Activity{
	
	private final int InitReady = 1;
	private SplashHandler mHandler;

	//初始化
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash);
	}
	
	@Override
	public void onStart(){
		super.onStart();
		
		initialize();
	}
	
	private void initialize(){
		
		mHandler = new SplashHandler();
		
		startMain();
	}

	//开启主线程
	private void startMain(){
		new Thread(new Runnable() {
			public void run() {
				
				try {
					Thread.currentThread().join(2000);
					
					sendMessage(InitReady, null);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();;
	}


	//发送消息
	private void sendMessage(int what, Object obj) {
		Message msg = mHandler.obtainMessage();
		msg.what = what;
		msg.obj = obj;
		mHandler.sendMessageDelayed(msg, 1000);
	}

	//接收处理消息
	private class SplashHandler extends Handler {
		
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			
			case InitReady:
				
				Intent intent = new Intent(SplashActivity.this, TabsActivity.class);
				startActivity(intent);
				SplashActivity.this.finish();
				break;
			}
		}
	}
}
