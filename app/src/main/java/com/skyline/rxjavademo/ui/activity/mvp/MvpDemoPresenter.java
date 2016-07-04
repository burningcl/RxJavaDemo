package com.skyline.rxjavademo.ui.activity.mvp;


import android.util.Log;

import com.skyline.rxjavademo.common.DemoResult;
import com.skyline.rxjavademo.util.EventBusHolder;

/**
 * 负责页面的操作，是Model与View之间的桥梁
 * Created by jairus on 16/6/28.
 */
public class MvpDemoPresenter {

	final static String LOG_TAG = MvpDemoPresenter.class.getSimpleName();

	private MvpDemoView demoView;

	private MvpDemoModel demoModel;

	private String location;

	public MvpDemoPresenter(MvpDemoView demoView) {
		Log.d(LOG_TAG, "constructor, demoView: " + demoView);
		this.demoView = demoView;
		this.demoModel = new MvpDemoModel();
		EventBusHolder.EVENT_BUS.register(this);
		Log.d(LOG_TAG, "constructor");
	}

	public void fetchData(final String location) {
		Log.d(LOG_TAG, "fetchData, location: " + location);
		this.location = location;
		demoModel.fetchData(location);
	}

	public void onEventMainThread(DemoResult result) {
		if (result == null || result.query == null || location == null || !result.query.equals(location)) {
			return;
		}
		Log.d(LOG_TAG, "onEventMainThread, result: " + result);
		if (result != null && result.status.equals(DemoResult.Status.SUCCESS)) {
			demoView.renderWeather(result.data);
			demoView.setContentVisible(true);
		} else {
			demoView.setContentVisible(false);
		}
	}

	public void onViewDestroy() {
		Log.d(LOG_TAG, "onViewDestroy");
		EventBusHolder.EVENT_BUS.unregister(this);
	}


}
