package com.where2eat.activity;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.where2eat.R;
import com.where2eat.entiy.User;
import com.where2eat.util.HttpUtil;

public class SelfActivity extends BaseActivity implements OnClickListener {
	Button btn_back,btn_logout;
	TextView tv_title,name,phone;
	LinearLayout btn_changename,btn_changepsw,btn_myrefund,btn_myorder,btn_feedback,btn_about;
	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_self);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_logout = (Button) findViewById(R.id.btn_logout);
		tv_title = (TextView) findViewById(R.id.tv_bar_title);
		btn_changename = (LinearLayout) findViewById(R.id.btn_changename);
		btn_changepsw = (LinearLayout) findViewById(R.id.btn_changepsw);
		btn_myrefund = (LinearLayout) findViewById(R.id.btn_myrefund);
		btn_feedback = (LinearLayout) findViewById(R.id.btn_feedback);
		btn_myorder = (LinearLayout) findViewById(R.id.btn_myorder);
		btn_about = (LinearLayout) findViewById(R.id.btn_about);
		name = (TextView) findViewById(R.id.name);
		phone = (TextView) findViewById(R.id.phone);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		btn_back.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
		btn_changename.setOnClickListener(this);
		btn_changepsw.setOnClickListener(this);
		btn_myrefund.setOnClickListener(this);
		btn_myorder.setOnClickListener(this);
		btn_feedback.setOnClickListener(this);
		btn_about.setOnClickListener(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		String id = LoadData();
		//Toast.makeText(SelfActivity.this, "id:"+id, Toast.LENGTH_LONG).show();
		//String id="1";
		List<User> list = query(id);
		tv_title.setText("个人信息");
		name.setText(list.get(0).getUserName());
		phone.setText(list.get(0).getuPhone());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btn_back){
			//finish();
			Intent intent=new Intent();
			intent.setClass(SelfActivity.this,
					MainActivity.class);
			startActivity(intent);
		}else if(v == btn_logout){
			//logout();
			onDestroy();
			Intent intent = new Intent(SelfActivity.this,
					LoginActivity.class);
			startActivity(intent);
		}else if(v == btn_changename){
			Intent intent = new Intent(SelfActivity.this,
					ChangeNameActivity.class);
			startActivity(intent);
		}else if(v == btn_changepsw){
			Intent intent = new Intent(SelfActivity.this,
					ChangePswActivity.class);
			startActivity(intent);
		}
		else if(v == btn_myrefund){
			Intent intent = new Intent(SelfActivity.this,
					MyRefundActivity.class);
			startActivity(intent);
		}
		else if(v == btn_myorder){
			Intent intent = new Intent(SelfActivity.this,
					OrderListActivity.class);
			startActivity(intent);
		}
		else if(v == btn_feedback){
			Intent intent = new Intent(SelfActivity.this,
					FeedBackActivity.class);
			startActivity(intent);
		}
		else if(v == btn_about){
			Intent intent = new Intent(SelfActivity.this,
					AboutActivity.class);
			startActivity(intent);
		}
	}
	
	 
	 
	private List<User> query(String id){
		String queryString;
		
		
			queryString = "id="+id;
		
		String url = HttpUtil.BASE_URL+"selfinfoServlet?"+queryString;
		
		return HttpUtil.jsonSelect(url);
    }
	
	public String LoadData() {  
	    //指定操作的文件名称  
	    SharedPreferences share = getSharedPreferences("mydata", MODE_PRIVATE);   
	    String id = share.getString("id", "1");  
	   return id;
	      
	}  
	
	public void onDestroy() {  
        // TODO Auto-generated method stub  
  
		    SharedPreferences share = getSharedPreferences("mydata", MODE_PRIVATE);   
		    SharedPreferences.Editor edit = share.edit(); //编辑文件  
            edit.clear();  
            edit.commit();  
          
    }  

}
