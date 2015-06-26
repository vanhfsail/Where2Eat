package com.where2eat.activity;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.example.where2eat.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;

public class SNSRegistActivity extends BaseActivity implements OnClickListener {
	// 手机号输入框
	private EditText et_phone;

	// 验证码输入框
	private EditText et_sns_code;

	// 获取验证码按钮
	private Button btn_forcode;

	// 注册按钮
	private Button btn_forsure;
	TextView tv_title;
	Button btn_back;
	//
	int i = 30;
	String phoneNums;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		phoneNums = et_phone.getText().toString();
		
		switch (v.getId()) {
		case R.id.btn_forcode:
			// 1. 通过规则判断手机号
			if (!judgePhoneNums(phoneNums)) {
				return;
			} // 2. 通过sdk发送短信验证
			SMSSDK.getVerificationCode("86", phoneNums);

			// 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
			btn_forcode.setClickable(false);
			btn_forcode.setText("重新发送(" + i + ")");
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (; i > 0; i--) {
						handler.sendEmptyMessage(-9);
						if (i <= 0) {
							break;
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					handler.sendEmptyMessage(-8);
				}
			}).start();
			break;

		case R.id.btn_forsure:
			SMSSDK.submitVerificationCode("86", phoneNums, et_sns_code
					.getText().toString());
			createProgressBar();
			break;
		case R.id.btn_back:
			Intent intent = new Intent(SNSRegistActivity.this,LoginActivity.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_snsregist);
		verify();
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_sns_code = (EditText) findViewById(R.id.et_sns_code);
		btn_forcode = (Button) findViewById(R.id.btn_forcode);
		btn_forsure = (Button) findViewById(R.id.btn_forsure);
		tv_title = (TextView) findViewById(R.id.tv_bar_title);
		btn_back = (Button) findViewById(R.id.btn_back);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		btn_forcode.setOnClickListener(this);
		btn_forsure.setOnClickListener(this);
		btn_back.setOnClickListener(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		tv_title.setText("使用手机号注册");
	}

	public void verify() {
		// 启动短信验证sdk
		SMSSDK.initSDK(this, "73f78dd354f6", "9e7a3b3ec47f9466b2bd7748eb16e2c4");
		EventHandler eventHandler = new EventHandler() {
			@Override
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		// 注册回调监听接口
		SMSSDK.registerEventHandler(eventHandler);
	}

	/**
	 * 
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == -9) {
				btn_forcode.setText("重新发送(" + i + ")");
			} else if (msg.what == -8) {
				btn_forcode.setText("获取验证码");
				btn_forcode.setClickable(true);
				i = 30;
			} else {
				int event = msg.arg1;
				int result = msg.arg2;
				Object data = msg.obj;
				Log.e("event", "event=" + event);
				if (result == SMSSDK.RESULT_COMPLETE) {
					// 短信注册成功后，返回MainActivity,然后提示
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
						Toast.makeText(getApplicationContext(), "提交验证码成功",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(SNSRegistActivity.this,
								RegistActivity.class);
						Bundle b = new Bundle();
						b.putString("mobel", phoneNums);
						intent.putExtra("bundle", b);
						startActivity(intent);
						SNSRegistActivity.this.finish();
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						Toast.makeText(getApplicationContext(), "验证码已经发送",
								Toast.LENGTH_SHORT).show();
					} else {
						((Throwable) data).printStackTrace();
					}
				}
			}
		}
	};

	/**
	 * 判断手机号码是否合理
	 * 
	 * @param phoneNums
	 */
	private boolean judgePhoneNums(String phoneNums) {
		if (isMatchLength(phoneNums, 11) && isMobileNO(phoneNums)) {
			return true;
		}
		Toast.makeText(this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
		return false;
	}

	/**
	 * 判断一个字符串的位数
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static boolean isMatchLength(String str, int length) {
		if (str.isEmpty()) {
			return false;
		} else {
			return str.length() == length ? true : false;
		}
	}

	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobileNums) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobileNums))
			return false;
		else
			return mobileNums.matches(telRegex);
	}

	/**
	 * progressbar
	 */
	private void createProgressBar() {
		FrameLayout layout = (FrameLayout) findViewById(android.R.id.content);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER;
		ProgressBar mProBar = new ProgressBar(this);
		mProBar.setLayoutParams(layoutParams);
		mProBar.setVisibility(View.VISIBLE);
		layout.addView(mProBar);
	}

	@Override
	protected void onDestroy() {
		SMSSDK.unregisterAllEventHandler();
		super.onDestroy();
	}
}
