package com.where2eat.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.where2eat.R;
import com.where2eat.activity.MainActivity.RestaurantListAdapter;
import com.where2eat.entiy.Reserve;
import com.where2eat.entiy.Restaurant;
import com.where2eat.util.HttpUtil;
import com.where2eat.util.JSONTools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class OrderListActivity extends BaseActivity implements OnClickListener {

	private ListView listView;
	private List<HashMap<String, Object>> mData;
	Button btn_back;
	OrderListAdapter adapter;
	private TextView tv_title;
	String state="0";
	
	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_order_list);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		btn_back = (Button) findViewById(R.id.btn_back);
		tv_title = (TextView) findViewById(R.id.tv_bar_title);
		
		mData = getData();// 为刚才的变量赋值
		listView = (ListView) findViewById(R.id.list_order);// 实例化ListView
		adapter = new OrderListAdapter(this);// 创建一个适配器
		listView.setAdapter(adapter);// 为ListView控件绑定适配器
	}

	@Override
	public void initListeners() {
		btn_back.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				
                String orderid =  (String) mData.get(position).get("id");
                
                if( mData.get(position).get("note")==null||mData.get(position).get("note").toString().equals("")||mData.get(position).get("note").toString().equals("null")){
				  
				    state="0";
				    //Toast.makeText(OrderListActivity.this,"00000", Toast.LENGTH_LONG).show();
				}
				else{
					
					state="1";
				}
              
                //Toast.makeText(OrderListActivity.this,"isvalu:"+state, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(OrderListActivity.this,
						OrderActivity.class);
				Bundle bundle = new Bundle();//　　Bundle的底层是一个HashMap<String, Object
	            bundle.putString("id", orderid);
	            bundle.putString("isEvaluated",state);
	           
	            intent.putExtra("bundle", bundle);
				startActivity(intent);
			}
		});
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		tv_title.setText("订单列表");
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btn_back) {
			finish();
		}
	}
	// 初始化一个List
		public List<HashMap<String, Object>> getData() {
			// 新建一个集合类，用于存放多条数据
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> map = null;
			
			List<Reserve> list1=select();
			
			for (int i = 0; i < list1.size(); i++) {
				map = new HashMap<String, Object>();
				map.put("restaurant_name", list1.get(i).getrName());
				map.put("restaurant_type", list1.get(i).getrType());
				//Object note = mData.get(i).get("note");
				//map.put("order_status", "已评价");
				map.put("note", list1.get(i).getNote());
				
				if(list1.get(i).getNote()==null||list1.get(i).getNote().toString().equals("")||list1.get(i).getNote().toString().equals("null")){
				    map.put("order_status", "待评价");
    
				}
				else{
					//Toast.makeText(OrderListActivity.this,"note:"+list1.get(i).getNote(), Toast.LENGTH_LONG).show();
					map.put("order_status", "已评价");
					
				}
				map.put("order_time", list1.get(i).getReserveTime());
				//map.put("restaurant_icon", R.drawable.default_restaurant_icon);
				map.put("id", list1.get(i).getId());
				//Toast.makeText(OrderListActivity.this,"note:"+list1.get(i).getNote() , Toast.LENGTH_LONG).show();
				map.put("restaurant_icon", list1.get(i).getrImg());
				list.add(map);
			}

			return list;
		}
		
		public List<Reserve> select(){
			String url="";
			try {
				
				url = HttpUtil.BASE_URL+"reserveInfoServlet?userId="+LoadData();
				
				return HttpUtil.jsonSelectOrder(url);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public String LoadData() {  
		    //指定操作的文件名称  
		    SharedPreferences share = getSharedPreferences("mydata", MODE_PRIVATE);   
		    String id = share.getString("id", "1");  
		   return id;
		      
		}  

		public class OrderListAdapter extends BaseAdapter {
			private LayoutInflater mInflater;

			public OrderListAdapter(Context context) {
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
				//String imageUrl = "";  
				Bitmap bmImg; 
				
				convertView = mInflater
						.inflate(R.layout.order_list_item, null);// 根据布局文件实例化view
				TextView restaurant_name = (TextView) convertView
						.findViewById(R.id.restaurant_name);// 找某个控件
				restaurant_name.setText(mData.get(position).get("restaurant_name")
						.toString());// 给该控件设置数据(数据从集合类中来)
				TextView restaurant_type = (TextView) convertView
						.findViewById(R.id.restaurant_type);// 找某个控件
				restaurant_type.setText(mData.get(position).get("restaurant_type")
						.toString());// 给该控件设置数据(数据从集合类中来)
				TextView order_time = (TextView) convertView
						.findViewById(R.id.order_time);
				order_time.setText(mData.get(position).get("order_time")
						.toString());
				TextView order_status = (TextView) convertView
						.findViewById(R.id.order_status);
				order_status.setText(mData.get(position).get("order_status")
						.toString());
				
				ImageView restaurant_icon = (ImageView) convertView.findViewById(R.id.restaurant_icon);
			    restaurant_icon.setImageBitmap(returnBitMap(imageUrl+mData.get(position).get("restaurant_icon").toString()));  
				return convertView;
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
	
}
