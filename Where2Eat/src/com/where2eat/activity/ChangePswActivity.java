package com.where2eat.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.example.where2eat.R;
import com.example.where2eat.R.id;
import com.example.where2eat.R.layout;
import com.example.where2eat.R.menu;
import com.where2eat.util.HttpUtil;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class ChangePswActivity extends BaseActivity implements OnClickListener{
	Button btn_back,btn_forsure;
	TextView tv_title;
	EditText password1,password2;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btn_back){
			finish();
		}else if(v == btn_forsure){
			changePassword();
			
		}
	}

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_change_psw);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_forsure = (Button) findViewById(R.id.btn_forsure);
		tv_title = (TextView) findViewById(R.id.tv_bar_title);
		password1 = (EditText) findViewById(R.id.et_password);
		password2 = (EditText) findViewById(R.id.et_repassword);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		btn_back.setOnClickListener(this);
		btn_forsure.setOnClickListener(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		tv_title.setText("修改密码");
	}
	
	public void changePassword(){
		String p1 = password1.getText().toString();
		String p2 = password2.getText().toString();
		if(!p1.equals(p2)){
			Toast.makeText(ChangePswActivity.this, "两次密码不一致！", Toast.LENGTH_LONG).show();
			return;
		}
		else{
			String result = query(LoadData(),p1);
			if(result.equals("success")){
				Toast.makeText(ChangePswActivity.this, "修改成功", Toast.LENGTH_LONG).show();
				Intent intent=new Intent();
				intent.setClass(ChangePswActivity.this,
						SelfActivity.class);
				startActivity(intent);
			}
			else{
				Toast.makeText(ChangePswActivity.this, "修改失败", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	 private String query(String userid,String newPassword){
			String queryString;
			
			try {
				queryString = "userId="+URLEncoder.encode(userid, "UTF-8")+"&newPassword="+URLEncoder.encode(newPassword, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				queryString="";
			}
			String url = HttpUtil.BASE_URL+"changePasswordServlet?"+queryString;
			Log.i("tag", url);
			return HttpUtil.queryStringForPost(url);
	    }

	 public String LoadData() {  
		    //指定操作的文件名称  
		    SharedPreferences share = getSharedPreferences("mydata", MODE_PRIVATE);   
		    String id = share.getString("id", "1");  
		   return id;
		      
		}  

}
