package com.where2eat.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.example.where2eat.R;
import com.where2eat.entiy.Restaurant;
import com.where2eat.util.HttpUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class RestaurantActivity extends BaseActivity implements OnClickListener{
	Button btn_back,btn_booking;
	LinearLayout ll_refund;
	TextView tv_title,tv_restaurant_name,type,price,time,address,phone,description;
	ImageView img;
	RatingBar score;
	String id;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btn_back){
			finish();
		}else if(v == btn_booking){
			//changename();
			//finish();
			
			
			Intent intent = new Intent(RestaurantActivity.this,
					BookingActivity.class);
			Bundle b = new Bundle();
			b.putString("id", id);
			intent.putExtra("bundle", b);
			
			startActivity(intent);
		}else if(v == ll_refund){
			//changename();
			//finish();点击了代金券，为用户添加代金券
			ShowToast("成功预订即可获得代金券");
			//餐厅数据表中添加代金券数据，代金券也是从数据中取出的ll_refund.setVisibility(View.GONE);
			//向数据库插入代金券数据
		}
	}

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_restaurant);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_booking = (Button) findViewById(R.id.btn_booking);
		tv_title = (TextView) findViewById(R.id.tv_bar_title);
		tv_restaurant_name = (TextView) findViewById(R.id.restaurant_name);
		type =(TextView) findViewById(R.id.restaurant_type);
		price =(TextView) findViewById(R.id.price);
		time =(TextView) findViewById(R.id.restaurant_time);
		address =(TextView) findViewById(R.id.restaurant_address);
		phone =(TextView) findViewById(R.id.restaurant_phone);
		description =(TextView) findViewById(R.id.restaurant_description);
		img=(ImageView)findViewById(R.id.restaurant_icon);//to do...
		score = (RatingBar)findViewById(R.id.app_ratingbar);
		ll_refund = (LinearLayout) findViewById(R.id.ll_refund);//to do...
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		btn_back.setOnClickListener(this);
		btn_booking.setOnClickListener(this);
		ll_refund.setOnClickListener(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		String imageUrl = "http://172.31.34.188/wte/upload/";  
		 
		Bitmap bmImg;
		
		tv_title.setText("餐厅信息");
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("bundle");
        id = bundle.getString("id");
        List<Restaurant> list = select(id);
        tv_restaurant_name.setText(list.get(0).getName());
        type.setText(list.get(0).getType());
        price.setText(list.get(0).getAvgConcume());
        time.setText(list.get(0).getTime());
        address.setText(list.get(0).getAddress());
        phone.setText(list.get(0).getPhone());
        description.setText(list.get(0).getDescription());
        img.setImageBitmap(returnBitMap(imageUrl+list.get(0).getImage()));  
        if(list.get(0).getScore()==null||list.get(0).getScore().toString().equals("")||list.get(0).getScore().toString().equals("null"))
	    {
	    	score.setRating(0);
	    }
	    else{
	    	score.setRating(Float.parseFloat(list.get(0).getScore().toString()));
	    	
	    }
        
	}
	
	public Bitmap returnBitMap(String url){  
	    URL myFileUrl = null;    
	    Bitmap bitmap = null;   
	    try {    
	        myFileUrl = new URL(url);    
	    } catch (MalformedURLException e) {    
	        e.printStackTrace();    
	    }    
	    try {    
	        HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();    
	        conn.setDoInput(true);    
	        conn.connect();    
	        InputStream is = conn.getInputStream();    
	        bitmap = BitmapFactory.decodeStream(is);    
	        is.close();    
	    } catch (IOException e) {    
	        e.printStackTrace();    
	    }    
	    return bitmap;    
	}
	
	 public List<Restaurant> select(String id){
		 try {
			
			String url = HttpUtil.BASE_URL+"RestaurantInfoServlet?restId="+id;
			return HttpUtil. jsonSelectRest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return null;
	    }

	

}
