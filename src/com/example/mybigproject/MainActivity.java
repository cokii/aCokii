package com.example.mybigproject;

import model.ConnectionModel;
import model.CouponsFamilyModel;
import model.MomeryModel;
import net.tsz.afinal.annotation.view.ViewInject;
import vo.CouponsFamily;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import config.UserConfig;

public class MainActivity extends NivagationActivity {

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@ViewInject(id = R.id.button1, click = "")
	Button button1;
	@ViewInject(id = R.id.button2, click = "")
	Button button2;
	@ViewInject(id = R.id.editText1, click = "")
	EditText editText1;
	@ViewInject(id = R.id.button3, click = "")
	Button button3;
	ConnectionModel connectionModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		connectionModel = ConnectionModel.getInstance(this);
	}

	public void download(View view) {
		new CouponsFamilyModel(this, mHandler).download();
	}

	public void findentity(View v) {
		CouponsFamily couponsFamily = (CouponsFamily) MomeryModel.getinstance(
				this).get(CouponsFamily.class, editText1.getText().toString());
		UserConfig.p(this, "拿到的名字：" + couponsFamily.getCouponname());
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_main);
	}

	@Override
	public void sonhandleMessage(Message msg) {
		UserConfig.p(this, "可以执行");
	}

	public void tonextactivity(View view) {
		Intent intent = new Intent();
		intent.setClass(this, Main2Activity.class);
		intent.putExtra("is_first", false);
		startActivity(intent, getClass().getName());
		finish();
	}
}
