package com.skyline.rxjavademo.mvp;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.skyline.rxjavademo.meta.WeatherData;
import com.skyline.rxjavademo.ui.activity.mvp.MvpDemoPresenter;
import com.skyline.rxjavademo.ui.activity.mvp.MvpDemoView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by jairus on 16/7/4.
 */
@RunWith(AndroidJUnit4.class)
public class MvpDemoPresenterTest {

	public static final String LOG_TAG = MvpDemoPresenterTest.class.getSimpleName();


	@Before
	public void before() {

	}

	@Test
	public void testHangzhou() {
		MvpDemoView mockView = new MvpDemoView() {

			int time = 0;

			@Override
			public void setContentVisible(boolean visible) {
				Log.d(LOG_TAG, "setContentVisible, visible: " + visible);
				if (time == 0) {
					Assert.assertEquals(visible, false);
				} else {
					Assert.assertEquals(visible, true);
				}
				time++;
			}

			@Override
			public void renderWeather(WeatherData weather) {
				Log.d(LOG_TAG, "renderWeather, weather: " + weather);
				if (time == 0) {
					Assert.assertNull(weather);
				} else {
					Assert.assertNotNull(weather);
					Assert.assertEquals(weather.city.areaCnName, "杭州");
				}
			}
		};
		MvpDemoPresenter presenter = new MvpDemoPresenter(mockView);
		presenter.fetchData("杭州");
	}

	@Test
	public void testNoWhere() {
		MvpDemoView mockView = new MvpDemoView() {
			@Override
			public void setContentVisible(boolean visible) {
				Log.d(LOG_TAG, "setContentVisible, visible: " + visible);
				Assert.assertEquals(visible, false);
			}

			@Override
			public void renderWeather(WeatherData weather) {
				Log.d(LOG_TAG, "renderWeather, weather: " + weather);
				Assert.assertNull(weather);
			}
		};
		MvpDemoPresenter presenter = new MvpDemoPresenter(mockView);
		presenter.fetchData("NoWhere");
	}

}
