package com.skyline.rxjavademo.ui.activity.mvc;


import android.util.Log;

import com.skyline.db.jerrymouse.core.util.StringUtils;
import com.skyline.rxjavademo.bl.AsyncDbCache;
import com.skyline.rxjavademo.common.ActionListener;
import com.skyline.rxjavademo.meta.RequestResponse;
import com.skyline.rxjavademo.meta.WeatherData;
import com.skyline.rxjavademo.network.ApiClient;
import com.skyline.rxjavademo.common.DemoResult;
import com.skyline.rxjavademo.util.EventBusHolder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Model负责数据操作的逻辑
 * Created by jairus on 16/6/28.
 */
public class MvcDemoModel {

	final static String LOG_TAG = MvcDemoModel.class.getSimpleName();

	protected static String getCacheKey(String location) {
		return "weather_" + location;
	}

	/**
	 * 获取数据
	 *
	 * @param location
	 */
	public void fetchData(final String location) {
		if (StringUtils.isEmpty(location)) {
			return;
		}

		EventBusHolder.EVENT_BUS.post(new DemoResult(location, null, DemoResult.Status.LOADING));

		final String cacheKey = getCacheKey(location);

		AsyncDbCache.get(cacheKey, new ActionListener<WeatherData>() {
			@Override
			public void onAction(WeatherData data) {
				if (data != null) {
					Log.d(LOG_TAG, "fetchData, success, from db");
					EventBusHolder.EVENT_BUS.post(new DemoResult(location, data, DemoResult.Status.SUCCESS));
					return;
				}
				fetchDataFromInternet(cacheKey, location);

			}
		}, WeatherData.class);
	}

	/**
	 * 从网络上获取数据
	 *
	 * @param cacheKey
	 * @param location
	 */
	protected void fetchDataFromInternet(final String cacheKey, final String location) {
		Call<RequestResponse<WeatherData>> call = ApiClient.getWeatherData(location);
		call.enqueue(new Callback<RequestResponse<WeatherData>>() {
			@Override
			public void onResponse(Call<RequestResponse<WeatherData>> call, Response<RequestResponse<WeatherData>> response) {
				if (response.body() != null && response.body().data != null) {
					Log.d(LOG_TAG, "fetchData, success, from internet");
					EventBusHolder.EVENT_BUS.post(new DemoResult(location, response.body().data, DemoResult.Status.SUCCESS));
					AsyncDbCache.put(cacheKey, response.body().data);
				} else {
					Log.w(LOG_TAG, "fetchData, fail, response: " + response.body() + ", " + response.code() + ", " + response.errorBody());
					EventBusHolder.EVENT_BUS.post(new DemoResult(location, null, DemoResult.Status.FAIL));
				}
			}

			@Override
			public void onFailure(Call<RequestResponse<WeatherData>> call, Throwable t) {
				Log.w(LOG_TAG, "fetchData, fail", t);
				EventBusHolder.EVENT_BUS.post(new DemoResult(location, null, DemoResult.Status.FAIL));
			}
		});
	}

}
