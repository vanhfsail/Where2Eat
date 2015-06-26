package com.where2eat.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.example.where2eat.R;
import com.where2eat.util.HttpUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener{
	TextView regist;
	Button loginButton;
	EditText et_username, et_password;
	//SharedPreferences sp;  
	//@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v == regist){
			Intent intent = new Intent(LoginActivity.this,
					SNSRegistActivity.class);
			startActivity(intent);
		}else if(v == loginButton){
			//Intent intent = new Intent(LoginActivity.this,
				//	MainActivity.class);
			//startActivity(intent);
			
			login();
		}
	}

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_login);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		Animation anim=AnimationUtils.loadAnimation(getBaseContext(), R.anim.login_anim);
		anim.setFillAfter(true);
		LinearLayout inputLayout = (LinearLayout) findViewById(R.id.input_box);
		inputLayout.startAnimation(anim);
		regist = (TextView) findViewById(R.id.btn_register);
		loginButton = (Button) findViewById(R.id.btn_login);
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		regist.setOnClickListener(this);
		loginButton.setOnClickListener(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}
	
	private void login(){
		String name = et_username.getText().toString();
		String password = et_password.getText().toString();

		if (TextUtils.isEmpty(name)) {
			ShowToast(R.string.toast_error_username_null);
			return;
		}

		if (TextUtils.isEmpty(password)) {
			ShowToast(R.string.toast_error_password_null);
			return;
		}

		final ProgressDialog progress = new ProgressDialog(
				LoginActivity.this);
		progress.setMessage("正在登陆...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		/*
		String queryString = "mobel="+name+"&password="+password;
		String url = HttpUtil.BASE_URL+"com/Ngbussiness/server/SelectServlet?"+queryString;
		List list =  HttpUtil. jsonSelect(url);
		if(list.size()==0){
			Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_LONG).show();
		}
		else{
			Bundle b=new Bundle();
			b.putStringArray("user", list);
			
			Intent intent=new Intent();
			intent.setClass(SNSRegistActivity.this,
					RegistActivity.class);
			intent.putExtras(b);
			startActivity(intent);
		}
		*/
		String result = query(name,password);
		Log.i("tag", result);
		if(result.equals("error")){
			Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_LONG).show();
			progress.dismiss();
			return;
		}
		else{
			SaveData(result);
			
			Intent intent=new Intent();
			intent.setClass(LoginActivity.this,
					MainActivity.class);
			startActivity(intent);
		}
		
		
	}
	
	 private String query(String username,String password){
			String queryString;
			
			try {
				queryString = "mobel="+URLEncoder.encode(username, "UTF-8")+"&password="+URLEncoder.encode(password, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				queryString="";
			}
			String url = HttpUtil.BASE_URL+"loginServlet?"+queryString;
		
			return HttpUtil.queryStringForPost(url);
	    }
	 
	 public void SaveData(String id) {  
		    //指定操作的文件名称  
		    SharedPreferences share = getSharedPreferences("mydata", MODE_PRIVATE);   
		    SharedPreferences.Editor edit = share.edit(); //编辑文件  
		         //根据键值对添加数据  
		    edit.putString("id", id);  
		    edit.commit();  //保存数据信息  
		}  
}

	

