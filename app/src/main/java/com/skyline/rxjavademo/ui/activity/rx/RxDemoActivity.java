package com.skyline.rxjavademo.ui.activity.rx;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;

import com.skyline.rxjavademo.R;
import com.skyline.rxjavademo.databinding.RxDemoActivityBinding;

/**
 * Created by jairus on 16/6/28.
 */
public class RxDemoActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

	private RxDemoActivityBinding dataBinding;

	private RxDemoViewModel demoViewModel;

	@Override
	protected void onCreate(@ColorInt Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataBinding = DataBindingUtil.setContentView(this, R.layout.rx_demo_activity);
		demoViewModel = new RxDemoViewModel();
		dataBinding.setDemoViewModel(demoViewModel);
		dataBinding.searchView.setOnQueryTextListener(this);
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		demoViewModel.fetchData(query);
		dataBinding.searchView.clearFocus();
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}

	public static void start(Context context) {
		Intent intent = new Intent(context, RxDemoActivity.class);
		context.startActivity(intent);
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		demoViewModel.onViewDestroy();
	}
}