package com.skyline.rxjavademo.ui.activity.mvvm;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;

import com.skyline.rxjavademo.R;
import com.skyline.rxjavademo.common.DemoResult;
import com.skyline.rxjavademo.databinding.MvvmDemoActivityBinding;
import com.skyline.rxjavademo.util.EventBusHolder;

/**
 * Created by jairus on 16/6/28.
 */
public class MvvmDemoActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

	private MvvmDemoActivityBinding dataBinding;

	private MvvmDemoViewModel demoViewModel;

	private MvvmDemoModel demoModel;

	@Override
	protected void onCreate(@ColorInt Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataBinding = DataBindingUtil.setContentView(this, R.layout.mvvm_demo_activity);
		demoViewModel = new MvvmDemoViewModelImpl();
		demoModel = new MvvmDemoModel();
		dataBinding.setDemoViewModel(demoViewModel);
		dataBinding.searchView.setOnQueryTextListener(this);
		EventBusHolder.EVENT_BUS.register(this);
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		demoModel.fetchData(query);
		dataBinding.searchView.clearFocus();
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}

	public static void start(Context context) {
		Intent intent = new Intent(context, MvvmDemoActivity.class);
		context.startActivity(intent);
	}

	public void onEventMainThread(DemoResult result) {
		demoViewModel.setResult(result);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBusHolder.EVENT_BUS.unregister(this);
	}
}