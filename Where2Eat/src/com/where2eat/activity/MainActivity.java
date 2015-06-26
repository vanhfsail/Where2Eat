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
import com.where2eat.entiy.Restaurant;
import com.where2eat.util.HttpUtil;
import com.where2eat.util.JSONTools;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements OnClickListener {
	
	private Context context;
	private LayoutInflater inflater;

	private ListView listView;
	private List<HashMap<String, Object>> mData;
	Button btn_self,btn_search;
	RestaurantListAdapter adapter;
	
	private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15,btn16,btn17,btn18,btn19;
	private Button pingfen,juli,jiagejiang,jiagesheng,moren;
	private Button openNetwork;
	PopupWindow PopType,PopSort;
	LinearLayout ll_nav;
	private TextView address,tv_nav_sort,tv_nav_type;
	private LocationClient mLocationClient;
	private TextView LocationResult;
	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {
		public void run() {
			setTitle(LocationResult.getText());
			handler.postDelayed(this, 1000);
			address.setText(LocationResult.getText());
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		

		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		this.context = this;
		inflater = LayoutInflater.from(this);

		openNetwork = (Button)findViewById(R.id.btn_network);
		openNetwork.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent  = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
				MainActivity.this.startActivity(intent);
			}
			
		});
		address = (TextView) findViewById(R.id.tv_address);
	
		mLocationClient = ((LocationApplication) getApplication()).mLocationClient;
		LocationResult = new TextView(this);
		((LocationApplication) getApplication()).mLocationResult = LocationResult;
		InitLocation();
		LocationResult.setText("定位中。。。");
		setTitle("定位中。。。");
		address.setText("定位中");
	
	}

	@Override
	protected void onStart() {
		mLocationClient.start();
		handler.postDelayed(runnable, 1000); // 开始Timer
		super.onStart();
	}

	@Override
	protected void onResume(){
		super.onResume();
		if(!isNetworkAvailable()){
			listView.setVisibility(8);
			openNetwork.setVisibility(0);
		}
		else{
			listView.setVisibility(0);
			openNetwork.setVisibility(8);
		}
	}
	
	@Override
	protected void onStop() {
		mLocationClient.stop();
		handler.removeCallbacks(runnable); // 停止Timer
		super.onStop();
	}

	private void InitLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("gcj02");// 定位使用的坐标系
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为1000ms
		option.setIsNeedAddress(true);// 需要显示地址
		mLocationClient.setLocOption(option);
	}

	public void onClick(View v) {
		
		if (v == btn_self) {
			//Toast.makeText(MainActivity.this, "haha:"+LoadData(), Toast.LENGTH_LONG).show();
			if(LoadData().equals("-1"))
			{
				statusDialog();
				
			}
			else{
			Intent intent = new Intent(MainActivity.this, SelfActivity.class);
			startActivity(intent);
			}
			
			
			
		}else if (v == btn_search) {
			Intent intent = new Intent(MainActivity.this, SearchActivity.class);
			startActivity(intent);
		}
		else if (v == tv_nav_type) {
			showTypePop();
		}
		else if (v == tv_nav_sort) {
			showSortPop();
		}

	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_main);
		
	}

	@Override
	public void initViews() {
		Log.i("tag","initviews");
		btn_self = (Button) findViewById(R.id.btn_self);
		btn_search = (Button) findViewById(R.id.btn_search);
		tv_nav_type = (TextView) findViewById(R.id.tv_nav_type);
		tv_nav_sort = (TextView) findViewById(R.id.tv_nav_sort);
		ll_nav = (LinearLayout) findViewById(R.id.ll_nav);
		listView = (ListView) findViewById(R.id.list_restaurant);// 实例化ListView
		mData = getData(0,0);// 为刚才的变量赋值
		adapter = new RestaurantListAdapter(this);// 创建一个适配器
		listView.setAdapter(adapter);// 为ListView控件绑定适配器
		//s(0);
	}

	@Override
	public void initListeners() {
		Log.i("tag","intilisteners");
		btn_self.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		tv_nav_type.setOnClickListener(this);
		tv_nav_sort.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
                String restid = (String) mData.get(position).get("id");
                //Toast.makeText(MainActivity.this, restid+"id", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(MainActivity.this,
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
	}

	// 初始化一个List
	public List<HashMap<String, Object>> getData(int type,int id) {
		// 新建一个集合类，用于存放多条数据
		
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
	    HashMap<String, Object> map = null; 
	  
	    List<Restaurant> list1=select(type,id);
	   // Toast.makeText(MainActivity.this, list1.size()+"size", Toast.LENGTH_LONG).show();
	    
	    for (int i = 0; i < list1.size(); i++) {
	      map = new HashMap<String, Object>();
	      map.put("restaurant_name",list1.get(i).getName() );
	      map.put("restaurant_type", list1.get(i).getType());
	      map.put("price", "￥"+list1.get(i).getAvgConcume()+"/人");
	      map.put("restaurant_icon1", list1.get(i).getImage());
	      //map.put("restaurant_icon",R.drawable.default_restaurant_icon);
	      map.put("restaurant_icon",list1.get(i).getImage());
	      map.put("id", list1.get(i).getId());
	      map.put("avgScore", list1.get(i).getScore());
	      list.add(map);
	    }
	    
         return list;
	}
	
	public List<Restaurant> select(int type,int id){
		String url="";
		try {
			if(type==0){
				url = HttpUtil.BASE_URL+"RestaurantListServlet?restTypeId="+id;
			}
			else{
				url = HttpUtil.BASE_URL+"restaurantSortServlet?type="+id;
			}
			/*
			String a =HttpUtil.jsonSelectRest(url);
			Toast.makeText(MainActivity.this, a, Toast.LENGTH_LONG).show();
			List<Restaurant> list2 = JSONTools.getRestaurant("restaurant", a) ;
			return list2;*/
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
			//String imageUrl = "";  
			Bitmap bmImg;
			
			//Toast.makeText(MainActivity.this, "size:"+mData.size(), Toast.LENGTH_LONG).show();
			
		    convertView = mInflater.inflate(R.layout.restaurant_list_item, null);//根据布局文件实例化view
		    TextView restaurant_name = (TextView) convertView.findViewById(R.id.restaurant_name);//找某个控件
		    restaurant_name.setText(mData.get(position).get("restaurant_name").toString());//给该控件设置数据(数据从集合类中来)
		    TextView restaurant_type = (TextView) convertView.findViewById(R.id.restaurant_type);//找某个控件
		    restaurant_type.setText(mData.get(position).get("restaurant_type").toString());//给该控件设置数据(数据从集合类中来)
		    TextView price = (TextView) convertView.findViewById(R.id.price);
		    price.setText(mData.get(position).get("price").toString());
		    ImageView restaurant_icon = (ImageView) convertView.findViewById(R.id.restaurant_icon);
		    restaurant_icon.setImageBitmap(returnBitMap(imageUrl+mData.get(position).get("restaurant_icon").toString()));  
		    RatingBar score = (RatingBar) convertView.findViewById(R.id.app_ratingbar);
		    if(mData.get(position).get("avgScore")==null||mData.get(position).get("avgScore").toString().equals("")||mData.get(position).get("avgScore").toString().equals("null"))
		    {
		    	score.setRating(0);
		    }
		    else{
		    	//Toast.makeText(MainActivity.this,mData.get(position).get("avgScore")+"" , Toast.LENGTH_LONG).show();
		    	//score.setRating(0);
		    	score.setRating(Float.parseFloat(mData.get(position).get("avgScore").toString()));
		    	
		    	//score.setRating((float) 3.5);
		    }
		    
		  
		    //score.setNumStars(2);
		    //Toast.makeText(MainActivity.this,Integer.parseInt(mData.get(position).get("avgScore").toString().substring(0,1))+"" , Toast.LENGTH_LONG).show();
		    //restaurant_icon.setBackgroundResource((Integer) mData.get(position).get("restaurant_icon"));
		    //Bitmap bitmap = BitmapFactory.decodeFile("D:/Program Files/wamp/www/wte/upload/"+mData.get(position).get("restaurant_icon").toString());  
		    //restaurant_icon.setImageBitmap(bitmap);
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
	
	@SuppressWarnings("deprecation")
	private void showSortPop() {
		View view = LayoutInflater.from(this).inflate(R.layout.pop_sort, null);
		// 注入
		moren = (Button) view.findViewById(R.id.btn_moren);
		pingfen = (Button) view.findViewById(R.id.btn_pingfen);
		//juli = (Button) view.findViewById(R.id.btn_juli);
		jiagejiang = (Button) view.findViewById(R.id.btn_jiagejiang);
		jiagesheng = (Button) view.findViewById(R.id.btn_jiagesheng);
		
		PopSort = new PopupWindow(view, mScreenWidth, 600);

		PopSort.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					PopSort.dismiss();
					return true;
				}
				return false;
			}
		});

		PopSort.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		PopSort.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		PopSort.setTouchable(true);
		PopSort.setFocusable(true);
		PopSort.setOutsideTouchable(true);
		PopSort.setBackgroundDrawable(new BitmapDrawable());
		// 动画效果 从顶部弹下
		PopSort.setAnimationStyle(R.style.MenuPop);
		PopSort.showAsDropDown(ll_nav, 0, -dip2px(this, 2.0F));
	}
	
	public void sortClick(View v){
		Button b=(Button)v;
		int id=b.getId();
		int s=1;
		switch(id){
		case R.id.btn_pingfen:
			s=1;
			break;
		//case R.id.btn_juli:
			//s=2;
			//break;
		case R.id.btn_jiagejiang:
			s=4;
			break;
		case R.id.btn_jiagesheng:
			s=3;
			break;
		case R.id.btn_moren:
			s=5;
			break;
		}
		if(s==5){
			mData = getData(0,0);
		}
		else{
		    mData = getData(1,s);// 为刚才的变量赋值
			//mData = getData(0,1);// 为刚才的变量赋值
		}
		//Toast.makeText(MainActivity.this, mData.size()+"", Toast.LENGTH_LONG).show();
		adapter = new RestaurantListAdapter(this);// 创建一个适配器
		listView.setAdapter(adapter);// 为ListView控件绑定适配器
		
		//adapter.notifyDataSetChanged();
		PopSort.dismiss();
		
	}

	@SuppressWarnings("deprecation")
	private void showTypePop() {
		View view = LayoutInflater.from(this).inflate(R.layout.pop_type, null);
		// 注入
		btn1 = (Button) view.findViewById(R.id.btn1);
		btn2 = (Button) view.findViewById(R.id.btn2);
		btn3 = (Button) view.findViewById(R.id.btn3);
		btn4 = (Button) view.findViewById(R.id.btn4);
		btn5 = (Button) view.findViewById(R.id.btn5);
		btn6 = (Button) view.findViewById(R.id.btn6);
		btn7 = (Button) view.findViewById(R.id.btn7);
		btn8 = (Button) view.findViewById(R.id.btn8);
		btn9 = (Button) view.findViewById(R.id.btn9);
		btn10 = (Button) view.findViewById(R.id.btn10);
		btn11 = (Button) view.findViewById(R.id.btn11);
		btn12 = (Button) view.findViewById(R.id.btn12);
		btn13 = (Button) view.findViewById(R.id.btn13);
		btn14 = (Button) view.findViewById(R.id.btn14);
		btn15 = (Button) view.findViewById(R.id.btn15);
		btn16 = (Button) view.findViewById(R.id.btn16);
		btn17 = (Button) view.findViewById(R.id.btn17);
		btn18 = (Button) view.findViewById(R.id.btn18);
		btn19 = (Button) view.findViewById(R.id.btn19);
		
		
		PopType = new PopupWindow(view, mScreenWidth, 600);

		PopType.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					PopType.dismiss();
					return true;
				}
				return false;
			}
		});

		PopType.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		PopType.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		PopType.setTouchable(true);
		PopType.setFocusable(true);
		PopType.setOutsideTouchable(true);
		PopType.setBackgroundDrawable(new BitmapDrawable());
		// 动画效果 从顶部弹下
		PopType.setAnimationStyle(R.style.MenuPop);
		PopType.showAsDropDown(ll_nav, 0, -dip2px(this, 2.0F));
	}
	
	public void clickBtn(View v){
		Button b =(Button)v;
		int a=0;
		switch(b.getId()){
		case R.id.btn0:
			a=0;
			break;
		case R.id.btn1:
			a=1;
			break;
		case R.id.btn2:
			a=2;
			break;
		case R.id.btn3:
			a=3;
			break;
		case R.id.btn4:
			a=4;
			break;
		case R.id.btn5:
			a=5;
			break;
		case R.id.btn6:
			a=6;
			break;
		case R.id.btn7:
			a=7;
			break;
		case R.id.btn8:
			a=8;
			break;
		case R.id.btn9:
			a=9;
			break;
		case R.id.btn10:
			a=10;
			break;
		case R.id.btn11:
			a=11;
			break;
		case R.id.btn12:
			a=12;
			break;
		case R.id.btn13:
			a=13;
			break;
		case R.id.btn14:
			a=14;
			break;
		case R.id.btn15:
			a=15;
			break;
		case R.id.btn16:
			a=16;
			break;
		case R.id.btn17:
			a=17;
			break;
		case R.id.btn18:
			a=18;
			break;
		case R.id.btn19:
			a=19;
			break;

		}
		//s(1);
		//mData = getData(1);
		 //Toast.makeText(MainActivity.this, a, Toast.LENGTH_LONG).show();
	    //a=1;
		mData = getData(0,a);// 为刚才的变量赋值
		adapter = new RestaurantListAdapter(this);// 创建一个适配器
		listView.setAdapter(adapter);// 为ListView控件绑定适配器
		
		//adapter.notifyDataSetChanged();
		PopType.dismiss();
		
		// new AlertDialog.Builder(this).setTitle(a)   
         //.create(); 
	}
	
	public String LoadData() {  
	    //指定操作的文件名称  
	    SharedPreferences share = getSharedPreferences("mydata", MODE_PRIVATE);   
	    String id = share.getString("id", "-1");  
	   return id;
	      
	}
	
	private void statusDialog() {
		final View view = inflater.inflate(R.layout.login_status_dialog, null);
		final AlertDialog dialog = new AlertDialog.Builder(context).setView(
				view).show();
		Button sure = (Button) dialog.findViewById(R.id.dialog_ok);
		sure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						LoginActivity.class);
				startActivity(intent);
				dialog.dismiss();

			}
		});

		Button cancel = (Button) dialog.findViewById(R.id.dialog_cancel);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				//finish();
			}
		});
	}
	
	


}