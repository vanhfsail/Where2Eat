package com.where2eat.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.where2eat.R;
import com.where2eat.entiy.Reserve;
import com.where2eat.util.HttpUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends BaseActivity implements OnClickListener {
	Button btn_back,btn_cancel_order,btn_evaluate_order;
	private ListView timeLineListView;
	private TextView tv_title,tv_order_status, tv_order_detail;
	private TextView restaurant_name,restaurant_phone,restaurant_address,booking_time,booking_person,booking_phone;
	LinearLayout ll_order_status, ll_order_detail;
	private OrderTimeLineAdapter timeLineAdapter;
	private List<HashMap<String, Object>> mData;
	String order_status_flag;
	boolean evaluate_flag;
	String id;
	String state;
	String isEvaluated;
	@Override
	public void onClick(View v) {
		// TODO Auto-geneevaluated method stub
		if (v == btn_back){
			finish();
		}else if(v == tv_order_status){
			tv_order_status.setTextColor(tv_order_status.getResources().getColor(
					R.color.theme_color));
			tv_order_detail.setTextColor(Color.BLACK);
			ll_order_detail.setVisibility(View.GONE);
			ll_order_status.setVisibility(View.VISIBLE);
		}else if(v == tv_order_detail){
			tv_order_status.setTextColor(Color.BLACK);
			tv_order_detail.setTextColor(tv_order_detail.getResources().getColor(
					R.color.theme_color));
			
			List<Reserve> list=selectDetail();
			restaurant_name.setText(list.get(0).getrName());
			restaurant_phone.setText(list.get(0).getrPhone());
			restaurant_address.setText(list.get(0).getrAddress());
			booking_time.setText(list.get(0).getReserveTime());
			booking_person.setText(list.get(0).getUserName());
			booking_phone.setText(list.get(0).getReservePhone());
			
			
			ll_order_detail.setVisibility(View.VISIBLE);
			ll_order_status.setVisibility(View.GONE);
		}else if(v == btn_cancel_order){
			//取消订单
			dialog();
		}
		else if(v == btn_evaluate_order){
			Intent intent = new Intent(OrderActivity.this,
					EvaluateActivity.class);
			Bundle b = new Bundle();
			b.putString("id", id);
			intent.putExtra("bundle", b);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void setContentView() {
		// TODO Auto-geneevaluated method stub
		setContentView(R.layout.activity_order);
	}

	@Override
	public void initViews() {
		// TODO Auto-geneevaluated method stub
		btn_back = (Button) findViewById(R.id.btn_back);
		tv_title = (TextView) findViewById(R.id.tv_bar_title);
		
		btn_cancel_order = (Button) findViewById(R.id.btn_cancel_order);
		btn_evaluate_order = (Button) findViewById(R.id.btn_evaluate_order);
		
		ll_order_status = (LinearLayout) findViewById(R.id.ll_order_status);
		ll_order_detail = (LinearLayout) findViewById(R.id.ll_order_detail);
		tv_order_status = (TextView) findViewById(R.id.tv_order_status);
		tv_order_detail = (TextView) findViewById(R.id.tv_order_detail);
		
		restaurant_name=(TextView)findViewById(R.id.restaurant_name);
		restaurant_phone=(TextView)findViewById(R.id.restaurant_phone);
		restaurant_address=(TextView)findViewById(R.id.restaurant_address);
		booking_time=(TextView)findViewById(R.id.booking_time);
		booking_person=(TextView)findViewById(R.id.booking_person);
		booking_phone=(TextView)findViewById(R.id.booking_phone);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("bundle");
        id = bundle.getString("id");
        isEvaluated=bundle.getString("isEvaluated");
		
		mData = getData();// 为刚才的变量赋值
		timeLineListView = (ListView)findViewById(R.id.timeline_list);
		timeLineAdapter = new OrderTimeLineAdapter(this);
		timeLineListView.setAdapter(timeLineAdapter);
		
		//evaluate_flag = false;//false未评价,true已经评价
		//order_status_flag = new String("已完成");
		//Toast.makeText(OrderActivity.this, "state:"+state+",isEvaluated"+isEvaluated, Toast.LENGTH_LONG).show();
		if((state.equals("5"))&&(isEvaluated.equals("0"))){
			btn_cancel_order.setVisibility(View.GONE);
			btn_evaluate_order.setVisibility(View.VISIBLE);
		}else if((state.equals("5"))&&(isEvaluated.equals("1"))){
			btn_cancel_order.setVisibility(View.GONE);
			btn_evaluate_order.setVisibility(View.GONE);
		}else{
			btn_cancel_order.setVisibility(View.VISIBLE);
			btn_evaluate_order.setVisibility(View.GONE);
		}
	}

	@Override
	public void initListeners() {
		// TODO Auto-geneevaluated method stub
		tv_order_status.setOnClickListener(this);
		tv_order_detail.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		
		btn_evaluate_order.setOnClickListener(this);
		btn_cancel_order.setOnClickListener(this);
	}

	@Override
	public void initData() {
		// TODO Auto-geneevaluated method stub
		tv_title.setText("订单信息");
	}
	
	// 初始化一个List
			public List<HashMap<String, Object>> getData() {
				// 新建一个集合类，用于存放多条数据
				ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
				HashMap<String, Object> map_status1,map_status2,map_status3,map_status4 = null;
				
				state = query();
				//Toast.makeText(OrderActivity.this, "isvaluated:"+isEvaluated+",state:"+state, Toast.LENGTH_LONG).show();
	
				map_status1 = new HashMap<String, Object>();
				map_status1.put("msg", "下单成功");
				map_status1.put("time", "");
				list.add(map_status1);
				
				if(!state.equals("1")&!state.equals("5"))
				{
				map_status2 = new HashMap<String, Object>();
				if(state.equals("2"))
				map_status2.put("msg", "商家已经接单");
				else if(state.equals("3"))
					map_status2.put("msg", "订单被拒绝");
				else if(state.equals("4"))
					map_status2.put("msg", "订单已作废");
				map_status2.put("time", "");
				list.add(map_status2);
				}
				
				if(state.equals("5")){
					map_status4 = new HashMap<String, Object>();
					map_status4.put("msg", "商家已接单");
					map_status3 = new HashMap<String, Object>();
					map_status3.put("msg", "订单已完成");
					map_status3.put("time", "");
					map_status4.put("time", "");
					list.add(map_status4);
					list.add(map_status3);
				}

				return list;
			}
			
			 private String query(){
					
					String url = HttpUtil.BASE_URL+"reserveStateServlet?reserveId="+id;
				
					//Toast.makeText(OrderActivity.this, "state:"+HttpUtil.queryStringForPost(url), Toast.LENGTH_LONG).show();
					
					return HttpUtil.queryStringForPost(url);
			    }
			 
	public class OrderTimeLineAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public OrderTimeLineAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-geneevaluated method stub
			return mData.size();// ListView的条目数
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-geneevaluated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-geneevaluated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = mInflater
					.inflate(R.layout.order_time_point_item, null);// 根据布局文件实例化view
			TextView msg = (TextView) convertView
					.findViewById(R.id.time_line_item_msg);// 找某个控件
			msg.setText(mData.get(position).get("msg")
					.toString());// 给该控件设置数据(数据从集合类中来)
			TextView time = (TextView) convertView
					.findViewById(R.id.time_line_item_time);// 找某个控件
			time.setText(mData.get(position).get("time")
					.toString());// 给该控件设置数据(数据从集合类中来)
			return convertView;
		}
		
	}
	
	 public List<Reserve> selectDetail()
		{
			if(!id.equals("")&id!=null)
			{
				String url="";
				try {
					
					url = HttpUtil.BASE_URL+"reserveDeInfoServlet?reserveId="+id;
					return HttpUtil.jsonSelectDetailOrder(url);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			return null;
		}
	 
	// 取消订单弹出对话框
		protected void dialog() {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("请联系餐厅取消订单！");
			builder.setTitle("提示");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String phone_number = "8008208820";
					// TODO Auto-generated method stub
					// 调用系统的拨号服务实现电话拨打功能
					// 封装一个拨打电话的intent，并且将电话号码包装成一个Uri对象传入
					Intent share_intent = new Intent();
					share_intent.setAction(Intent.ACTION_DIAL);
					share_intent.setData(Uri.parse("tel:"+phone_number));
					share_intent = Intent.createChooser(share_intent, "选择应用打开");
					startActivity(share_intent);
				}

			});
			
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
				
			});
			
			builder.create().show();
		}

	
}
