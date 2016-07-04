package com.skyline.rxjavademo.ui.activity.mvvm;


import android.databinding.Observable;

import com.skyline.rxjavademo.meta.WeatherData;

/**
 * Created by jairus on 16/6/28.
 */
public interface MvvmDemoViewModel extends Observable {

	/**
	 * 当前ViewModel的天气数据
	 *
	 * @return
	 */
	WeatherData weatherData();

	/**
	 * 当前View中的content的Visibility值
	 *
	 * @return
	 */
	int contentVisibility();

	/**
	 * @param location
	 */
	void fetchData(String location);

	/**
	 *
	 */
	void onViewDestroy();
}
