package com.skyline.rxjavademo.ui.activity.rx;


import android.databinding.ObservableField;
import android.util.Log;

import com.skyline.rxjavademo.common.DemoResult;
import com.skyline.rxjavademo.meta.WeatherData;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by jairus on 16/6/28.
 */
public class RxDemoViewModel {

	final static String LOG_TAG = RxDemoViewModel.class.getSimpleName();

	protected RxDemoModel demoModel;

	public ObservableField<DemoResult> demoResult;

	public RxDemoViewModel() {
		this.demoModel = new RxDemoModel();
		this.demoResult = new ObservableField();
	}

	public void fetchData(String query) {
		Observable observable = demoModel.fetchData(query);
		Subscriber<WeatherData> subscriber = new Subscriber<WeatherData>() {
			@Override
			public void onNext(WeatherData weatherData) {
				Log.d(LOG_TAG, "observeOn " + Thread.currentThread());
				Log.d(LOG_TAG, "onNext, weatherData: " + weatherData);
				if (weatherData != null)
					demoResult.set(new DemoResult(weatherData, DemoResult.Status.SUCCESS));
				else
					demoResult.set(new DemoResult(weatherData, DemoResult.Status.FAIL));
			}

			@Override
			public void onCompleted() {
				Log.d(LOG_TAG, "observeOn " + Thread.currentThread());
				Log.d(LOG_TAG, "onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				Log.w(LOG_TAG, "onError", e);
				demoResult.set(new DemoResult(null, DemoResult.Status.FAIL));
			}
		};
		observable.subscribe(subscriber);
	}

}
