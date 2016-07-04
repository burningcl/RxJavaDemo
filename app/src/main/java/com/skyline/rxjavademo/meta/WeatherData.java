package com.skyline.rxjavademo.meta;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by jairus on 16/6/18.
 */
public class WeatherData {

	/**
	 *
	 */
	public City city;
	/**
	 * 农历
	 */
	public String lunar;

	public List<WeatherItem> info;

	public String publishTime;

	public String extUrl;

	public Aqi aqi;

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
