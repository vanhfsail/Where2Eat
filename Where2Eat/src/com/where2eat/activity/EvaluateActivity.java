package com.where2eat.activity;

import com.example.where2eat.R;
import com.where2eat.util.HttpUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class EvaluateActivity extends BaseActivity implements OnClickListener {

	private RatingBar ratingbar;  
	Button btn_back,btn_forsure;
	TextView tv_title;
	EditText et_evaluate;
	float score=0;
	String id;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btn_back){
			finish();
		}else if(v == btn_forsure){
			String comments = et_evaluate.getText().toString();
			String result = update(id,score,comments);
			if(result.equals("success"))
			{
				Toast.makeText(EvaluateActivity.this, "评价成功", Toast.LENGTH_LONG).show();
				finish();
			}
			else
			{
				Toast.makeText(EvaluateActivity.this, "评价失败", Toast.LENGTH_LONG).show();
			}
		}
	}
	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_evaluate);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_forsure = (Button) findViewById(R.id.btn_forsure);
		tv_title = (TextView) findViewById(R.id.tv_bar_title);
		ratingbar = (RatingBar)findViewById(R.id.evaluate_ratingbar);  
		et_evaluate = (EditText)findViewById(R.id.et_evaluate);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("bundle");
        id = bundle.getString("id");
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		btn_back.setOnClickListener(this);
		btn_forsure.setOnClickListener(this);
		ratingbar.setOnRatingBarChangeListener(new RatingBarListener());  
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		tv_title.setText("餐厅评价");
	}
	private class RatingBarListener implements RatingBar.OnRatingBarChangeListener{  
		  
        public void onRatingChanged(RatingBar ratingBar, float rating,  
                boolean fromUser) {  
            //System.out.println("评分：" + rating);  
        	score=rating;
        }  
    }
	
	public String update(String id,float score,String comments){
		String url = HttpUtil.BASE_URL+"evaluteReserveServlet?comments="+comments+"&scores="+score+"&reserveId="+id;
		
		//Toast.makeText(OrderActivity.this, "state:"+HttpUtil.queryStringForPost(url), Toast.LENGTH_LONG).show();
		
		return HttpUtil.queryStringForPost(url);
		
	}
}
