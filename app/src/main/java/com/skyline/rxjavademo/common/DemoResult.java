package com.skyline.rxjavademo.common;

import com.skyline.rxjavademo.meta.WeatherData;

/**
 * Created by jairus on 16/6/28.
 */
public class DemoResult {
	public WeatherData data;
	public Status status;

	public DemoResult(WeatherData data, Status status) {
		this.data = data;
		this.status = status;
	}

	public enum Status{
		LOADING,
		SUCCESS,
		FAIL
	}
}
