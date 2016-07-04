package com.skyline.rxjavademo.ui.activity.mvvm;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;

import com.skyline.rxjavademo.R;
import com.skyline.rxjavademo.databinding.MvvmDemoActivityBinding;

/**
 * Created by jairus on 16/6/28.
 */
public class MvvmDemoActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

	private static final String LOG_TAG = MvvmDemoActivity.class.getSimpleName();

	private MvvmDemoActivityBinding dataBinding;

	private MvvmDemoViewModel demoViewModel;

	@Override
	protected void onCreate(@ColorInt Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataBinding = DataBindingUtil.setContentView(this, R.layout.mvvm_demo_activity);
		demoViewModel = new MvvmDemoViewModelImpl();
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
		Intent intent = new Intent(context, MvvmDemoActivity.class);
		context.startActivity(intent);
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		demoViewModel.onViewDestroy();
	}
}