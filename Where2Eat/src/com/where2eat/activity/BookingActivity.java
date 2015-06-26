package com.where2eat.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.where2eat.R;
import com.where2eat.datetimepicker.SlideDateTimePicker.Builder;
import com.where2eat.datetimepicker.*;
import com.where2eat.util.HttpUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BookingActivity extends BaseActivity implements OnClickListener {
	
	
	private TextView tv_title,orderDateTime;
	
	private Button btn_back,btn_forsure;
	
	private EditText phone,number,comment;

	private String initOrderDateTime = "2015/5/26下午5：00"; // 初始化预定时间
	String id;
	
	
	@SuppressLint("SimpleDateFormat") 
	private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy/MM/ddaahh:mm");
	


	private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
        	//mFormatter.applyPattern("yyyy'/'M'/'d'日'aah'时'm'分'");
        	orderDateTime.setText(mFormatter.format(date));
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel()
        {
            //Toast.makeText(BookingActivity.this,
                   // "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btn_back){
			finish();
		}else if(v == btn_forsure){
			reserve();
		}
	}


	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_booking);
	}


	@Override
	public void initViews() {
		// TODO Auto-generated method stub
	
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_forsure = (Button) findViewById(R.id.btn_forsure);
		tv_title = (TextView) findViewById(R.id.tv_bar_title);
		orderDateTime = (TextView) findViewById(R.id.ordertime);
		number=(EditText)findViewById(R.id.people_num);
		phone=(EditText)findViewById(R.id.phone);
		comment=(EditText)findViewById(R.id.comment);
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("bundle");
        id = bundle.getString("id");
	}


	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		btn_back.setOnClickListener(this);
		btn_forsure.setOnClickListener(this);
		orderDateTime.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(listener)
                .setInitialDate(new Date())
                //.setMinDate(minDate)
                //.setMaxDate(maxDate)
                //.setIs24HourTime(true)
                //.setTheme(SlideDateTimePicker.HOLO_DARK)
                //.setIndicatorColor(Color.parseColor("#990000"))
                .build()
                .show();  
            }
        });
	}


	@Override
	public void initData() {
		// TODO Auto-generated method stub
		tv_title.setText("预定座位");
		orderDateTime.setText(initOrderDateTime);
	}
	
	public void reserve(){
		String uid=LoadData();
		String time=orderDateTime.getText().toString();
		String n=number.getText().toString();
		String p=phone.getText().toString();
		String c=comment.getText().toString();
		String result = insert(uid,p,n,time,c);
		
		if(result.equals("success")){
			Toast.makeText(BookingActivity.this, "订单已提交，请等待商家接收订单", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(BookingActivity.this,
					MainActivity.class);
			startActivity(intent);
		}else{
			Toast.makeText(BookingActivity.this, "预订失败", Toast.LENGTH_LONG).show();
		}
	}
	
	 private String insert(String uid,String phone,String number,String date,String comment){
			String queryString;
			
			try {
				queryString = "userId="+uid+"&customerNumber="+number+"&reserveDate="+URLEncoder.encode(URLEncoder.encode(date,"UTF-8"),"UTF-8")+"&phone="+phone+"&comment="+URLEncoder.encode(URLEncoder.encode(comment,"UTF-8"),"UTF-8")+"&restId="+id;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				queryString="";
			}
			String url = HttpUtil.BASE_URL+"addReserveServlet?"+queryString;
			
			return HttpUtil.queryStringForPost(url);
	    }
	 
	 public String LoadData() {  
		    //指定操作的文件名称  
		    SharedPreferences share = getSharedPreferences("mydata", MODE_PRIVATE);   
		    String id = share.getString("id", "1");  
		   return id;
		      
		}  

}
