package com.where2eat.activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.where2eat.R;
import com.where2eat.activity.MainActivity.RestaurantListAdapter;
import com.where2eat.entiy.Restaurant;
import com.where2eat.util.HttpUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends BaseActivity implements OnClickListener {
	private ListView listView;
	private List<HashMap<String, Object>> mData;
	Button btn_back, btn_search;
	RestaurantListAdapter adapter;
	private EditText et_searchtext;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btn_back) {
			finish();
		}
		if (v == btn_search) {
			
			search(et_searchtext.getText().toString());
			
		}
	}

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_search);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_search = (Button) findViewById(R.id.btn_search);
		et_searchtext = (EditText) findViewById(R.id.et_searchtext);
		listView = (ListView) findViewById(R.id.list_search_restaurant);// 实例化ListView
		
	}
	
	private void search(String et_searchtet){
		mData = getData(et_searchtet);// 为刚才的变量赋值
		//Toast.makeText(SearchActivity.this, "size:"+mData.size(), Toast.LENGTH_LONG).show();
		adapter = new RestaurantListAdapter(this);// 创建一个适配器
		listView.setAdapter(adapter);// 为ListView控件绑定适配器
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		btn_back.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				 String restid = (String) mData.get(position).get("id");
					
					Intent intent = new Intent(SearchActivity.this,
							RestaurantActivity.class);
					Bundle bundle = new Bundle();//　　Bundle的底层是一个HashMap<String, Object
		            bundle.putString("id", restid);
		            intent.putExtra("bundle", bundle);
					startActivity(intent);
			}
		});
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	// 初始化一个List
	public List<HashMap<String, Object>> getData(String restaurantName) {
		// 新建一个集合类，用于存放多条数据
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = null;

		 List<Restaurant> list1=select(restaurantName);
		    
		    for (int i = 0; i < list1.size(); i++) {
		      map = new HashMap<String, Object>();
		      map.put("restaurant_name",list1.get(i).getName() );
		      map.put("restaurant_type", list1.get(i).getType());
		      map.put("price", "￥"+list1.get(i).getAvgConcume()+"/人");
		     // map.put("restaurant_icon1", list1.get(i).getImage());
		     // map.put("restaurant_icon",R.drawable.default_restaurant_icon);
		      map.put("restaurant_icon",list1.get(i).getImage());
		      map.put("id", list1.get(i).getId());
		      map.put("avgScore", list1.get(i).getScore());
		      list.add(map);
		    }
		    
	         return list;
	}
	
public List<Restaurant> select(String name){
		
		try {
				
			
			//String url = HttpUtil.BASE_URL+"restaurantNameServlet?Name="+name;
			String url = HttpUtil.BASE_URL+"restaurantNameServlet?Name="+URLEncoder.encode(URLEncoder.encode(name,"UTF-8"),"UTF-8");;
			return HttpUtil.jsonSelectRest(url);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public class RestaurantListAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public RestaurantListAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mData.size();// ListView的条目数
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			String imageUrl = "http://172.31.34.188/wte/upload/";    
			Bitmap bmImg;
			     
		    convertView = mInflater.inflate(R.layout.restaurant_list_item, null);//根据布局文件实例化view
		    TextView restaurant_name = (TextView) convertView.findViewById(R.id.restaurant_name);//找某个控件
		    restaurant_name.setText(mData.get(position).get("restaurant_name").toString());//给该控件设置数据(数据从集合类中来)
		    TextView restaurant_type = (TextView) convertView.findViewById(R.id.restaurant_type);//找某个控件
		    restaurant_type.setText(mData.get(position).get("restaurant_type").toString());//给该控件设置数据(数据从集合类中来)
		    TextView price = (TextView) convertView.findViewById(R.id.price);
		    price.setText(mData.get(position).get("price").toString());
		    ImageView restaurant_icon = (ImageView) convertView.findViewById(R.id.restaurant_icon);
		    //restaurant_icon.(R.drawable.default_restaurant_icon);
		   // restaurant_icon.setBackgroundResource(R.drawable.default_restaurant_icon);
		    restaurant_icon.setImageBitmap(returnBitMap(imageUrl+mData.get(position).get("restaurant_icon").toString())); 
		    RatingBar score = (RatingBar) convertView.findViewById(R.id.app_ratingbar);
		    score.setRating(Float.parseFloat(mData.get(position).get("avgScore").toString()));
	
		    return convertView;
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
	

}
