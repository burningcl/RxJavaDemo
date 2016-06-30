package com.skyline.rxjavademo.ui.activity.rx;


import android.util.Log;

import com.skyline.rxjavademo.bl.DbCache;
import com.skyline.rxjavademo.meta.RequestResponse;
import com.skyline.rxjavademo.meta.WeatherData;
import com.skyline.rxjavademo.network.ApiClient;

import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jairus on 16/6/28.
 */
public class RxDemoModel {

	final static String LOG_TAG = RxDemoModel.class.getSimpleName();

	protected static String getCacheKey(String location) {
		return "weather_" + location;
	}

	public Observable<WeatherData> fetchData(final String location) {
		final String cacheKey = getCacheKey(location);
		return Observable.create(
				new Observable.OnSubscribe<WeatherData>() {
					@Override
					public void call(Subscriber<? super WeatherData> sub) {
						try {
							Log.d(LOG_TAG, "subscribeOn " + Thread.currentThread());
							WeatherData weatherData = DbCache.get(cacheKey, WeatherData.class);
							if (weatherData == null) {
								Call<RequestResponse<WeatherData>> call = ApiClient.getWeatherData(location);
								Response<RequestResponse<WeatherData>> resp = call.execute();
								if (resp.body() != null && resp.body().data != null) {
									weatherData = resp.body().data;
									DbCache.put(cacheKey, weatherData);
								}

							}
							sub.onNext(weatherData);
							sub.onCompleted();
						} catch (Throwable e) {
							Log.w(LOG_TAG, "fetchData, fail", e);
							sub.onError(null);
						}
					}
				}
		)
//				.map(new Func1<WeatherData, DemoResult>() {
//			@Override
//			public DemoResult call(WeatherData weatherData) {
//				if (weatherData != null) {
//					return new DemoResult(weatherData, DemoResult.Status.SUCCESS);
//				}
//				Call<RequestResponse<WeatherData>> call = ApiClient.getWeatherData(location);
//				try {
//					Response<RequestResponse<WeatherData>> resp = call.execute();
//					if (resp.body() != null && resp.body().data != null) {
//						return new DemoResult(resp.body().data, DemoResult.Status.SUCCESS);
//					}
//				} catch (IOException e) {
//					Log.w(LOG_TAG, "fetchData, fail", e);
//				}
//				return new DemoResult(null, DemoResult.Status.FAIL);
//			}
//		})
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io());
	}

}
