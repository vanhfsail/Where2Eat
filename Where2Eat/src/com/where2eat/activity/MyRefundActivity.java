package com.where2eat.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.where2eat.R;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MyRefundActivity extends BaseActivity implements OnClickListener {

	private ListView listView;
	private List<HashMap<String, Object>> mData;
	Button btn_back;
	RefundAdapter adapter;
	private TextView tv_title;
	
	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_my_refund);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		btn_back = (Button) findViewById(R.id.btn_back);
		tv_title = (TextView) findViewById(R.id.tv_bar_title);
		
		mData = getData();// 为刚才的变量赋值
		listView = (ListView) findViewById(R.id.list_refund);// 实例化ListView
		adapter = new RefundAdapter(this);// 创建一个适配器
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
				
				
			}
		});
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		tv_title.setText("优惠券列表");
		
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
			for (int i = 1; i <= 1; i++) {
				map = new HashMap<String, Object>();
				map.put("restaurant_name", "木桶传奇   可使用");
				map.put("refund_money", "100");
				map.put("refund_condition", "300");
				map.put("refund_time", "2015/05/27");
				
				list.add(map);
			}

			return list;
		}

		public class RefundAdapter extends BaseAdapter {
			private LayoutInflater mInflater;

			public RefundAdapter(Context context) {
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
				convertView = mInflater
						.inflate(R.layout.refund_item, null);// 根据布局文件实例化view
				TextView restaurant_name = (TextView) convertView
						.findViewById(R.id.restaurant_name);// 找某个控件
				restaurant_name.setText(mData.get(position).get("restaurant_name")
						.toString());// 给该控件设置数据(数据从集合类中来)
				TextView refund_money = (TextView) convertView
						.findViewById(R.id.refund_money);// 找某个控件
				refund_money.setText(mData.get(position).get("refund_money")
						.toString());// 给该控件设置数据(数据从集合类中来)
				TextView refund_condition = (TextView) convertView
						.findViewById(R.id.refund_condition);
				refund_condition.setText(mData.get(position).get("refund_condition")
						.toString());
				TextView refund_time = (TextView) convertView
						.findViewById(R.id.refund_time);
				refund_time.setText(mData.get(position).get("refund_time")
						.toString());
				return convertView;
			}

		}
	
}
