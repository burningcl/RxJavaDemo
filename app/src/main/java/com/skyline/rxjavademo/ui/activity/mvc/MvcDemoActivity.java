package com.skyline.rxjavademo.ui.activity.mvc;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SearchView;

import com.skyline.rxjavademo.R;
import com.skyline.rxjavademo.common.DemoResult;
import com.skyline.rxjavademo.databinding.MvcDemoActivityBinding;
import com.skyline.rxjavademo.util.EventBusHolder;

/**
 * Activity即Controller
 * 负责对页面操作的逻辑
 * Created by jairus on 16/6/28.
 */
public class MvcDemoActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

	private MvcDemoActivityBinding dataBinding;

	private MvcDemoModel demoModel;

	@Override
	protected void onCreate(@ColorInt Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataBinding = DataBindingUtil.setContentView(this, R.layout.mvc_demo_activity);
		EventBusHolder.EVENT_BUS.register(this);
		demoModel = new MvcDemoModel();
		dataBinding.searchView.setOnQueryTextListener(this);
	}

	public void onEventMainThread(DemoResult result) {
		if (result != null && result.status.equals(DemoResult.Status.SUCCESS)) {
			dataBinding.setWeatherData(result.data);
			dataBinding.llContent.setVisibility(View.VISIBLE);
		} else {
			dataBinding.llContent.setVisibility(View.GONE);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBusHolder.EVENT_BUS.unregister(this);
	}

	public static void start(Context context) {
		Intent intent = new Intent(context, MvcDemoActivity.class);
		context.startActivity(intent);
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		dataBinding.searchView.clearFocus();
		demoModel.fetchData(query);
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}
}
