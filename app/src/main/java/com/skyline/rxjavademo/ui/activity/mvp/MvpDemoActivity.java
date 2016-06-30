package com.skyline.rxjavademo.ui.activity.mvp;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SearchView;

import com.skyline.rxjavademo.R;
import com.skyline.rxjavademo.databinding.MvcDemoActivityBinding;
import com.skyline.rxjavademo.meta.WeatherData;

/**
 * Created by jairus on 16/6/28.
 */
public class MvpDemoActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, MvpDemoView {

	private MvcDemoActivityBinding dataBinding;

	private MvpDemoPresenter demoPresenter;

	@Override
	protected void onCreate(@ColorInt Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataBinding = DataBindingUtil.setContentView(this, R.layout.mvc_demo_activity);
		dataBinding.searchView.setOnQueryTextListener(this);
		demoPresenter = new MvpDemoPresenter(this);
	}

	public static void start(Context context) {
		Intent intent = new Intent(context, MvpDemoActivity.class);
		context.startActivity(intent);
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		dataBinding.searchView.clearFocus();
		demoPresenter.fetchData(query);
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}


	@Override
	public void setContentVisible(boolean visible) {
		dataBinding.llContent.setVisibility(visible ? View.VISIBLE : View.GONE);
	}

	@Override
	public void renderWeather(WeatherData weather) {
		dataBinding.setWeatherData(weather);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		demoPresenter.onViewDestroy();
	}
}