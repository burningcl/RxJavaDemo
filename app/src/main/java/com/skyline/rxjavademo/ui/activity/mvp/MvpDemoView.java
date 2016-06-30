package com.skyline.rxjavademo.ui.activity.mvp;

import com.skyline.rxjavademo.meta.WeatherData;

/**
 * View Interface是View与Presenter之间的桥梁，负责这两者之间的解耦
 * Created by jairus on 16/6/28.
 */
public interface MvpDemoView {

	/**
	 * 设置内容是否可见
	 * @param visible
	 */
	void setContentVisible(boolean visible);

	/**
	 * 渲染天气数据
	 * @param weather
	 */
	void renderWeather(WeatherData weather);

}
