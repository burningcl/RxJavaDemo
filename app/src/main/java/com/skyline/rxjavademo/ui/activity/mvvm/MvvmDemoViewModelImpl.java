package com.skyline.rxjavademo.ui.activity.mvvm;


import android.databinding.BaseObservable;
import android.util.Log;
import android.view.View;

import com.skyline.rxjavademo.common.DemoResult;
import com.skyline.rxjavademo.meta.WeatherData;

/**
 * Created by jairus on 16/6/28.
 */
public class MvvmDemoViewModelImpl extends BaseObservable implements MvvmDemoViewModel {

	final static String LOG_TAG = MvvmDemoViewModelImpl.class.getSimpleName();

	private DemoResult result;

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
	public void setResult(DemoResult result) {
		Log.d(LOG_TAG, "setResult: " + result);
		notifyChange();
		this.result = result;
	}
}
