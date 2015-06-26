package com.where2eat.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.example.where2eat.R;
import com.where2eat.util.HttpUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeNameActivity extends BaseActivity implements OnClickListener{
	Button btn_back,btn_forsure;
	TextView tv_title;
	EditText username;
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btn_back){
			finish();
		}else if(v == btn_forsure){
			changename();
			
		}
	}

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_change_name);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_forsure = (Button) findViewById(R.id.btn_forsure);
		tv_title = (TextView) findViewById(R.id.tv_bar_title);
		username = (EditText)findViewById(R.id.et_username);
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
		tv_title.setText("修改用户名");
	}
	
	public void changename(){
		String name = username.getText().toString();
		String id=LoadData();
		String result = query(id,name);
		if(result.equals("success")){
			Toast.makeText(ChangeNameActivity.this, "修改成功", Toast.LENGTH_LONG).show();
			Intent intent=new Intent();
			intent.setClass(ChangeNameActivity.this,
					SelfActivity.class);
			startActivity(intent);
		}else{
			Toast.makeText(ChangeNameActivity.this, "修改失败", Toast.LENGTH_LONG).show();
		}
		
	}
	
	 private String query(String userid,String newName){
			String queryString;
			
			try {
				queryString = "userId="+userid+"&newUserName="+URLEncoder.encode(URLEncoder.encode(newName,"UTF-8"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				queryString="";
			}
			String url = HttpUtil.BASE_URL+"changeUserNamesServlet?"+queryString;
			return HttpUtil.queryStringForPost(url);
	    }
	 
	 public String LoadData() {  
		    //指定操作的文件名称  
		    SharedPreferences share = getSharedPreferences("mydata", MODE_PRIVATE);   
		    String id = share.getString("id", "1");  
		   return id;
		      
		}  


}
