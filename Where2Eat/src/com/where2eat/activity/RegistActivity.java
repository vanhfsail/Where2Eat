package com.where2eat.activity;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.where2eat.util.*;
import com.example.where2eat.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistActivity extends BaseActivity implements OnClickListener {
	Button btn_back,registButton;
	EditText et_username, et_password, et_repassword,et_email;
	 String amobel;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btn_back){
			Intent intent = new Intent(RegistActivity.this,
					LoginActivity.class);
			startActivity(intent);
		}else if(v == registButton) {
			regist();
		}
		
	}

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_regist);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		btn_back = (Button) findViewById(R.id.btn_back);
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		et_repassword = (EditText) findViewById(R.id.et_repassword);
		et_email = (EditText) findViewById(R.id.et_email);
		registButton = (Button) findViewById(R.id.btn_register);
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("bundle");
        amobel = bundle.getString("mobel");
        
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		btn_back.setOnClickListener(this);
		registButton.setOnClickListener(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}
	/*
	 * 判断输入信息
	 */
	private void regist(){
		String name = et_username.getText().toString();
		String password = et_password.getText().toString();
		String repassword = et_repassword.getText().toString();
		String email = et_email.getText().toString();
		if (TextUtils.isEmpty(name)) {
			ShowToast(R.string.toast_error_username_null);
			return;
		}

		if (TextUtils.isEmpty(password)) {
			ShowToast(R.string.toast_error_password_null);
			return;
		}
		if (!repassword.equals(password)) {
			ShowToast(R.string.toast_error_comfirm_password);
			return;
		}
		/*
		if (TextUtils.isEmpty(email)) {
			ShowToast(R.string.toast_error_email_null);
			return;
		}
		*/
		
		final ProgressDialog progress = new ProgressDialog(RegistActivity.this);
		progress.setMessage("正在注册...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
       
        String result = insert(name,password,email,amobel);
        if(result.equals("user_exist")){
        	Toast.makeText(RegistActivity.this, "该手机号已存在，请重新输入", Toast.LENGTH_LONG).show();
        	Intent intent = new Intent(RegistActivity.this,
					SNSRegistActivity.class);
			startActivity(intent);
        }
        else if(result.equals("success")){
        	Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_LONG).show();
        	Intent intent = new Intent(RegistActivity.this,
					LoginActivity.class);
			startActivity(intent);
        }
        else{
        	Toast.makeText(RegistActivity.this, "注册失败", Toast.LENGTH_LONG).show();
        	progress.dismiss();
        	return;
        }
        
		
	}
	
	 private String insert(String username,String password,String mail,String mobel){
			String queryString;
			
			try {
				queryString = "name="+URLEncoder.encode(URLEncoder.encode(username, "UTF-8"), "UTF-8")
+"&password="+password+"&mail="+mail+"&mobel="+mobel;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				queryString="";
			}
			String url = HttpUtil.BASE_URL+"registerServlet?"+queryString;
			return HttpUtil.queryStringForPost(url);
	    }

}
