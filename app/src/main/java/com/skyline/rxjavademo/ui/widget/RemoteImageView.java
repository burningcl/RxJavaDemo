package com.skyline.rxjavademo.ui.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.skyline.rxjavademo.DemoApplication;
import com.skyline.rxjavademo.R;
import com.skyline.rxjavademo.util.AsyncTaskUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * RemoteImageView
 * Created by hzchenliang on 14-12-26.
 */
public class RemoteImageView extends ImageView {

	private static final String LOG_TAG = "RemoteImageView";
	private static Picasso mPicasso = null;

	private Picasso getPicasso() {
		if (mPicasso == null) {
			Context context = DemoApplication.getInstance();
			mPicasso = new Picasso.Builder(context)
					.indicatorsEnabled(false)
					.executor(AsyncTaskUtil.getLowPriorityThreadPool())
					.build();
		}
		return mPicasso;
	}

	public RemoteImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(null);
	}

	public RemoteImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public RemoteImageView(Context context) {
		super(context);
		init(null);
	}

	private void init(AttributeSet attrs) {
		if (attrs == null) {
			return;
		}
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RemoteImageView);
		if (typedArray == null) {
			return;
		}
		String url = typedArray.getString(R.styleable.RemoteImageView_url);
		load(url);
	}

	public void setUrl(String url) {
		load(url);
	}

	public void load(String url) {
		if (url == null) {
			return;
		}

		// 使用picasso获取网络或缓存图片
		RequestCreator creator = getPicasso().load(url);
		creator.into(this);
	}

}