package com.skyline.rxjavademo;

import android.app.Application;
import android.util.Log;

import com.skyline.db.jerrymouse.core.exception.DataSourceException;
import com.skyline.rxjavademo.db.DbInitor;

/**
 * Created by jairus on 16/6/28.
 */
public class DemoApplication extends Application {

	public static final String LOG_TAG = DemoApplication.class.getSimpleName();

	public static DemoApplication instance = null;

	@Override
	public void onCreate() {
		super.onCreate();
		try {
			DbInitor.init(getApplicationContext());
		} catch (DataSourceException e) {
			Log.w(LOG_TAG, "DbInitor.init, fail", e);
		}
		instance=this;
	}

	public static DemoApplication getInstance() {
		return instance;
	}
}
