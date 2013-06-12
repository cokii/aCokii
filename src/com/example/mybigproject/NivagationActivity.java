package com.example.mybigproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalActivity;

import config.UserConfig;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class NivagationActivity extends FinalActivity implements
		OnClickListener {

	public Button leftButton, rightButton;
	protected TextView textView;
	String classname, rightbuttontext;
	RelativeLayout titleMainView, titleDefoutView;
	LinearLayout mainLayout;
	View titleView;
	String titleString;
	protected MyHandler mHandler;

	public RelativeLayout getTitleMainView() {
		return titleMainView;
	}

	public void setTitleMainView(RelativeLayout titleMainView) {
		this.titleMainView = titleMainView;
	}

	LayoutParams lParams = new LayoutParams(LayoutParams.FILL_PARENT,
			LayoutParams.WRAP_CONTENT);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView();
		// titleView = LinearLayout.inflate(this, R.layout.titlelayout, null);
		titleView = findViewById(R.id.titlelayout);
		// mainLayout.addView(titleView, lParams);
		leftButton = (Button) titleView.findViewById(R.id.titleleftbutton);
		leftButton.setOnClickListener(this);
		rightButton = (Button) titleView.findViewById(R.id.titlerightbutton);
		rightButton.setOnClickListener(this);
		rightButton.setVisibility(View.GONE);
		textView = (TextView) titleView.findViewById(R.id.titletextView1);
		mHandler = new MyHandler();
		UserConfig.p(this, mHandler.toString());
	}

	/**
	 * 每次重新展现都刷新一下标题和左按钮 因为有可能页面存在task 没销毁 而又别的activity再次启动它
	 * 这样需要得到最后点的那个activity的信息，这样返回按键点击 标题也不会错
	 */
	@Override
	protected void onResume() {

		textView.setText(getTitle());

		classname = getIntent().getStringExtra("classname");
		rightbuttontext = getIntent().getStringExtra("rightbuttontext");
		if (getIntent().getBooleanExtra("is_first", true)) {
			leftButton.setVisibility(View.GONE);
		} else {
			// 重点日后肯定用
			// leftButton.setText("< 返回");
			// leftButton.setText(getIntent().getStringExtra("title"));
		}
		if (rightbuttontext != null) {
			rightButton.setVisibility(View.VISIBLE);
			rightButton.setText(rightbuttontext);
		}

		super.onResume();
	}

	/**
	 * 要用此函数做跳转，因为被跳转的leftbutton需要当前activity的标题做名称和类名做返回
	 * 
	 * @param intent
	 * @param classname
	 *            返回跳转的类名 默认当前当前类名
	 */
	public void startActivity(Intent intent, String classname) {
		intent.putExtra("title", getTitle());
		intent.putExtra("classname", classname);
		super.startActivity(intent);
	}

	@Override
	protected void onPause() {

		super.onPause();
	}

	/**
	 * 被所继承类调用设置主xml 因为标题栏的初始化有顺序要求
	 */
	public abstract void setContentView();

	public void onClick(View v) {
		if (v == leftButton) {
			Intent intent = new Intent();
			if (classname == null) {
				classname = "com.qunyin.cc.activity.MainActivity";
			}
			intent.setClassName(this, classname);
			startActivity(intent);

		} else if (v == rightButton) {
		}

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}


	@Override
	public void setTitle(CharSequence title) {

		textView.setText(title);
		super.setTitle(title);
	}

	class MyHandler extends Handler {

		public void handleMessage(Message msg) {
			sonhandleMessage(msg);
		}
	};

	public abstract void sonhandleMessage(Message msg);

}
