package com.skyline.rxjavademo.common;

import com.google.gson.Gson;
import com.skyline.rxjavademo.meta.WeatherData;

/**
 * Created by jairus on 16/6/28.
 */
public class DemoResult {

	public String query;

	public WeatherData data;

	public Status status;

	public DemoResult(String query, WeatherData data, Status status) {
		this.query = query;
		this.data = data;
		this.status = status;
	}

	@Deprecated
	public DemoResult(WeatherData data, Status status) {
		this.data = data;
		this.status = status;
	}

	public enum Status {
		LOADING,
		SUCCESS,
		FAIL
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
