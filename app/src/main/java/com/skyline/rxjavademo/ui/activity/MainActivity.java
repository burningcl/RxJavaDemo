package com.skyline.rxjavademo.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.skyline.rxjavademo.R;
import com.skyline.rxjavademo.databinding.MainActivityBinding;
import com.skyline.rxjavademo.ui.activity.mvc.MvcDemoActivity;
import com.skyline.rxjavademo.ui.activity.mvp.MvpDemoActivity;
import com.skyline.rxjavademo.ui.activity.mvvm.MvvmDemoActivity;
import com.skyline.rxjavademo.ui.activity.rx.RxDemoActivity;

/**
 * Created by jairus on 16/6/28.
 */
public class MainActivity extends AppCompatActivity implements MainEventHandler {

	MainActivityBinding binding;

	@Override
	protected void onCreate(@ColorInt Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
		binding.setMainEventHandler(this);
	}

	@Override
	public void mvcOnClick(View v) {
		MvcDemoActivity.start(v.getContext());
	}

	@Override
	public void mvpOnClick(View v) {
		MvpDemoActivity.start(v.getContext());
	}

	@Override
	public void mvvmOnClick(View v) {
		MvvmDemoActivity.start(v.getContext());
	}

	@Override
	public void rxOnClick(View v) {
		RxDemoActivity.start(v.getContext());
	}
}
