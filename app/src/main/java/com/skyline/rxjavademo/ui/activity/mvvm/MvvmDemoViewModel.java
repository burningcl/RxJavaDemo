package com.skyline.rxjavademo.ui.activity.mvvm;


import android.databinding.Observable;

import com.skyline.rxjavademo.common.DemoResult;
import com.skyline.rxjavademo.meta.WeatherData;

/**
 * Created by jairus on 16/6/28.
 */
public interface MvvmDemoViewModel extends Observable{

	WeatherData weatherData();

	int contentVisibility();

	void setResult(DemoResult result);
}
