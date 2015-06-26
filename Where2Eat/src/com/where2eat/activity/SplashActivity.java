package com.where2eat.activity;


import com.example.where2eat.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

public class SplashActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		  StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()  
	        .detectDiskReads().detectDiskWrites().detectNetwork()  
	        .penaltyLog().build());  
	         StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()  
	        .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()  
	        .penaltyLog().penaltyDeath().build());  
	         
		init();
	}
	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_splash);

	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub

	}


	
	private void init(){

        new Handler().postDelayed(new Runnable() {
            public void run() {
            	Intent intent = new Intent(SplashActivity.this,
        				MainActivity.class);
        		startActivity(intent);
        		SplashActivity.this.finish();
            }
        }, 1000); //2900 for release

    }
		
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}
	
}



