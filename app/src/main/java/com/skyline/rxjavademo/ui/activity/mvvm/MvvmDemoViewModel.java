package com.skyline.rxjavademo.ui.activity.mvvm;


import android.databinding.ObservableField;

import com.skyline.rxjavademo.common.DemoResult;
import com.skyline.rxjavademo.util.EventBusHolder;

/**
 * Created by jairus on 16/6/28.
 */
public class MvvmDemoViewModel {

	final static String LOG_TAG = MvvmDemoViewModel.class.getSimpleName();

	protected MvvmDemoModel demoModel;

	/**
	 * 双向绑定的关键
	 */
	public ObservableField<DemoResult> demoResult;

	public MvvmDemoViewModel() {
		this.demoModel= new MvvmDemoModel();
		this.demoResult = new ObservableField();
		EventBusHolder.EVENT_BUS.register(this);
	}

	public void fetchData(String query){
		demoModel.fetchData(query);
	}

	public void onEventMainThread(DemoResult result) {
		this.demoResult.set(result);
	}

	public void onViewDestroy(){
		EventBusHolder.EVENT_BUS.unregister(this);
	}


}
