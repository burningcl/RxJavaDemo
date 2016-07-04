package com.skyline.rxjavademo.mvvm;

import android.databinding.Observable;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;

import com.skyline.rxjavademo.ui.activity.mvvm.MvvmDemoViewModel;
import com.skyline.rxjavademo.ui.activity.mvvm.MvvmDemoViewModelImpl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by apple on 16/7/4.
 */
@RunWith(AndroidJUnit4.class)
public class MvvmDemoViewModelTest {
	public static final String LOG_TAG = MvvmDemoViewModelTest.class.getSimpleName();

	@Test
	public void testHangzhou() {
		final MvvmDemoViewModel vm = new MvvmDemoViewModelImpl();
		vm.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {

			int time = 0;

			@Override
			public void onPropertyChanged(Observable sender, int propertyId) {
				Log.i(LOG_TAG, "onPropertyChanged, sender: " + sender + ", propertyId: " + propertyId);

				if (time == 0) {
					Assert.assertEquals(vm.contentVisibility(), View.GONE);
					Assert.assertNull(vm.weatherData());
				} else {
					Assert.assertEquals(vm.contentVisibility(), View.VISIBLE);
					Assert.assertNotNull(vm.weatherData());
					Assert.assertEquals(vm.weatherData().city.areaCnName, "杭州");
				}
				time++;
			}
		});
		vm.fetchData("杭州");
	}

	@Test
	public void testNoWhere() {
		final MvvmDemoViewModel vm = new MvvmDemoViewModelImpl();
		vm.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {

			@Override
			public void onPropertyChanged(Observable sender, int propertyId) {
				Log.i(LOG_TAG, "onPropertyChanged, sender: " + sender + ", propertyId: " + propertyId);
				Assert.assertEquals(vm.contentVisibility(), View.GONE);
				Assert.assertNull(vm.weatherData());
			}
		});
		vm.fetchData("tNoWhere");
	}
}
