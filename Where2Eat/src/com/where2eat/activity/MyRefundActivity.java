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
		
		mData = getData();// Ϊ�ղŵı�����ֵ
		listView = (ListView) findViewById(R.id.list_refund);// ʵ����ListView
		adapter = new RefundAdapter(this);// ����һ��������
		listView.setAdapter(adapter);// ΪListView�ؼ���������
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
		tv_title.setText("�Ż�ȯ�б�");
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btn_back) {
			finish();
		}
	}
	// ��ʼ��һ��List
		public List<HashMap<String, Object>> getData() {
			// �½�һ�������࣬���ڴ�Ŷ�������
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> map = null;
			for (int i = 1; i <= 1; i++) {
				map = new HashMap<String, Object>();
				map.put("restaurant_name", "ľͰ����   ��ʹ��");
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
				return mData.size();// ListView����Ŀ��
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
						.inflate(R.layout.refund_item, null);// ���ݲ����ļ�ʵ����view
				TextView restaurant_name = (TextView) convertView
						.findViewById(R.id.restaurant_name);// ��ĳ���ؼ�
				restaurant_name.setText(mData.get(position).get("restaurant_name")
						.toString());// ���ÿؼ���������(���ݴӼ���������)
				TextView refund_money = (TextView) convertView
						.findViewById(R.id.refund_money);// ��ĳ���ؼ�
				refund_money.setText(mData.get(position).get("refund_money")
						.toString());// ���ÿؼ���������(���ݴӼ���������)
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
