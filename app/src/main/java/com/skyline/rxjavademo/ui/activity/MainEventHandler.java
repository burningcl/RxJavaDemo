package com.skyline.rxjavademo.ui.activity;

import android.view.View;

/**
 * Created by jairus on 16/6/29.
 */
public interface MainEventHandler {

	void mvcOnClick(View view);

	void mvpOnClick(View view);

	void mvvmOnClick(View view);

	void rxOnClick(View view);
}
