package com.skyline.rxjavademo.ui.activity.mvp;


import com.skyline.rxjavademo.common.DemoResult;
import com.skyline.rxjavademo.util.EventBusHolder;

/**
 * Created by jairus on 16/6/28.
 */
public class MvpDemoPresenter {

	final static String LOG_TAG = MvpDemoPresenter.class.getSimpleName();

	private MvpDemoView demoView;

	MvpDemoModel demoModel;

	public MvpDemoPresenter(MvpDemoView demoView) {
		this.demoView = demoView;
		this.demoModel= new MvpDemoModel();
		EventBusHolder.EVENT_BUS.register(this);
	}

	public void fetchData(final String location) {
		demoModel.fetchData(location);
	}

	public void onEventMainThread(DemoResult result) {
		if (result != null && result.status.equals(DemoResult.Status.SUCCESS)) {
			demoView.renderWeather(result.data);
			demoView.setContentVisible(true);
		}else{
			demoView.setContentVisible(false);
		}
	}

	public void onViewDestroy(){
		EventBusHolder.EVENT_BUS.unregister(this);
	}


}
