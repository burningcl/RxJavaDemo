package com.skyline.rxjavademo.ui.activity;

import android.view.View;

/**
 * Created by jairus on 16/6/29.
 */
public interface MainEventHandler {

	/**
	 *
	 * @param view
	 */
	void mvcOnClick(View view);

	/**
	 *
	 * @param view
	 */
	void mvpOnClick(View view);

	/**
	 *
	 * @param view
	 */
	void mvvmOnClick(View view);

	/**
	 *
	 * @param view
	 */
	void rxOnClick(View view);
}
