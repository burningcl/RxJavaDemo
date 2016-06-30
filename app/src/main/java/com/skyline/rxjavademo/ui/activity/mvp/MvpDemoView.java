package com.skyline.rxjavademo.ui.activity.mvp;

import com.skyline.rxjavademo.meta.WeatherData;

/**
 * Created by jairus on 16/6/28.
 */
public interface MvpDemoView {

	void setContentVisible(boolean visible);

	void renderWeather(WeatherData weather);

}
