package com.skyline.rxjavademo.meta;

/**
 * Created by jairus on 16/6/18.
 */
public class WeatherItem {

	public int index;

	public WeatherInfoItem weatherDay;

	public WeatherInfoItem weatherNight;

	public String tempDay;

	public String tempNight;

	public WeatherInfoItem windDireDay;

	public WeatherInfoItem windDireNight;

	public WeatherInfoItem windForceDay;

	public WeatherInfoItem windForceNight;

	public String sunRiseSet;

	public class WeatherInfoItem {

		public String code;

		public String value;

		public String imgUrl() {
			return "http://mimg.127.net/p/js6/lib/img/weather/64x64/" + code + ".png";
		}
	}

	public String temp() {
		return tempNight + "~" + tempDay;
	}

}
