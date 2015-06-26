package com.where2eat.activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

import android.app.Application;
import android.util.Log;
import android.widget.TextView;

/**
 * 主Application
 */
public class LocationApplication extends Application {
	
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	
	public TextView mLocationResult;
	public StringBuffer mLocationStr;
	
	
	@Override
	public void onCreate() {
		
		super.onCreate();
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
	}

	
	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			StringBuffer sb = new StringBuffer(256);
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append(location.getAddrStr());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append(location.getAddrStr());
			}
			logMsg(sb.toString());
			mLocationStr = sb;
			Log.i("BaiduLocationApiDem", sb.toString());
		}
	}
	
	
	/**
	 * 显示请求字符串
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			if (mLocationResult != null)
				mLocationResult.setText(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

