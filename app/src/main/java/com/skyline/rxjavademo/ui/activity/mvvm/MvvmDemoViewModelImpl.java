package com.skyline.rxjavademo.ui.activity.mvvm;


import android.databinding.BaseObservable;
import android.util.Log;
import android.view.View;

import com.skyline.rxjavademo.common.DemoResult;
import com.skyline.rxjavademo.meta.WeatherData;
import com.skyline.rxjavademo.util.EventBusHolder;

/**
 * Created by jairus on 16/6/28.
 */
public class MvvmDemoViewModelImpl extends BaseObservable implements MvvmDemoViewModel {

	final static String LOG_TAG = MvvmDemoViewModelImpl.class.getSimpleName();

	private DemoResult result;

	private String location;

	private MvvmDemoModel demoModel;

	public MvvmDemoViewModelImpl(){
		this.demoModel = new MvvmDemoModel();
		EventBusHolder.EVENT_BUS.register(this);
	}

	@Override
	public WeatherData weatherData() {
		WeatherData data = result != null ? result.data : null;
		Log.d(LOG_TAG, "weatherData: " + data);
		return data;
	}

	@Override
	public int contentVisibility() {
		int v = result != null && result.status.equals(DemoResult.Status.SUCCESS) && result.data != null ? View.VISIBLE : View.GONE;
		Log.d(LOG_TAG, "contentVisibility: " + v);
		return v;
	}

	@Override
	public void fetchData(String location) {
		Log.d(LOG_TAG, "fetchData, location: " + location);
		this.location = location;
		demoModel.fetchData(location);
	}

	@Override
	public void onViewDestroy() {
		EventBusHolder.EVENT_BUS.unregister(this);
	}

	public void onEventMainThread(DemoResult result) {
		if (result == null || result.query == null || location == null || !result.query.equals(location)) {
			return;
		}
		Log.d(LOG_TAG, "onEventMainThread, result: " + result);
		notifyChange();
		this.result = result;
	}


}
