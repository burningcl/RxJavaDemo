package com.skyline.rxjavademo.rx;

import android.util.Log;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by jairus on 16/6/15.
 */
public class BasicTest{

	public static final String LOG_TAG = "BasicTest";

	@Test
	public void testBasic() {
		Log.d(LOG_TAG, "testBasic");

		Observable<String> myObservable = Observable.create(
				new Observable.OnSubscribe<String>() {
					@Override
					public void call(Subscriber<? super String> sub) {
						sub.onNext("Hello, world!");
						sub.onCompleted();
						throw new NullPointerException();
					}
				}
		);

		Subscriber<String> mySubscriber = new Subscriber<String>() {
			@Override
			public void onNext(String s) {
				Log.d(LOG_TAG, "onNext, s: " + s);
			}

			@Override
			public void onCompleted() {
				Log.d(LOG_TAG, "onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				Log.e(LOG_TAG, "onError", e);
			}
		};

		myObservable.subscribe(mySubscriber);
	}

	@Test
	public void testJust() {
		Log.d(LOG_TAG, "testJust");

		Observable<String> myObservable = Observable.just("Hello, world!");

		Action1<String> onNextAction = new Action1<String>() {
			@Override
			public void call(String s) {
				Log.d(LOG_TAG, "call, s: " + s);
			}
		};

		myObservable.subscribe(onNextAction);
	}

	@Test
	public void testOperators() {
		Log.d(LOG_TAG, "testOperators");

		Observable<String> myObservable = Observable.just("Hello, world!")
				.map(new Func1<String, Integer>() {
					@Override
					public Integer call(String s) {
						return s.hashCode();
					}
				})
				.map(new Func1<Integer, String>() {
					@Override
					public String call(Integer integer) {
						return "integer: " + integer;
					}
				});

		Action1<String> onNextAction = new Action1<String>() {
			@Override
			public void call(String s) {
				Log.d(LOG_TAG, "call, s: " + s);
			}
		};

		myObservable.subscribe(onNextAction);
	}

	@Test
	public void testFilter() {
		Log.d(LOG_TAG, "testFilter");
		String[] strs = {"Hello, world!", "Hello, world! 1", "World", "Hello"};

		Observable<List<String>> myObservable = Observable.from(strs)
//				.take(2)
//				.filter(new Func1<String, Boolean>() {
//					@Override
//					public Boolean call(String s) {
//						return s.startsWith("H");
//					}
//				})
				.toSortedList(new Func2<String, String, Integer>() {
					@Override
					public Integer call(String s, String s2) {
						int val = s.length() - s2.length();
						if (val == 0) {
							val = s.charAt(0) - s2.charAt(0);
						}
						return val;
					}
				})
				.map(new Func1<List<String>, List<String>>() {
					@Override
					public List<String> call(List<String> strings) {
						Log.d(LOG_TAG,"start sleep");
						try {
							Thread.sleep(1000 * 6);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Log.d(LOG_TAG,"wake up");
						return strings;
					}
				}).timeout(5, TimeUnit.SECONDS)
//				.map(new Func1<String, Integer>() {
//					@Override
//					public Integer call(String s) {
//						return s.hashCode();
//					}
//				})
//				.map(new Func1<Integer, String>() {
//					@Override
//					public String call(Integer integer) {
//						return "integer: " + integer;
//					}
//				})
				;


		Subscriber<List<String>> mySubscriber = new Subscriber<List<String>>() {
			@Override
			public void onNext(List<String> s) {
				for (String si : s)
					Log.d(LOG_TAG, "call, s: " + si);
			}

			@Override
			public void onCompleted() {
				Log.d(LOG_TAG, "onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				Log.e(LOG_TAG, "onError", e);
			}
		};
//

		myObservable.subscribe(mySubscriber);
	}

	@Test
	public void testSchedulers() {
		Log.d(LOG_TAG, "testSchedulers");

		Observable<String> myObservable = Observable.create(
				new Observable.OnSubscribe<String>() {
					@Override
					public void call(Subscriber<? super String> sub) {
						Log.d(LOG_TAG, "observeOn " + Thread.currentThread());
						sub.onNext("Hello, world!");
						sub.onCompleted();
					}
				}
		).filter(new Func1<String, Boolean>() {
			@Override
			public Boolean call(String s) {
				Log.d(LOG_TAG, "filter " + Thread.currentThread());
				return true;
			}
		})
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.newThread());

		Subscriber<String> mySubscriber = new Subscriber<String>() {
			@Override
			public void onNext(String s) {
				Log.d(LOG_TAG, "subscribeOn " + Thread.currentThread());
				Log.d(LOG_TAG, "onNext, s: " + s);
			}

			@Override
			public void onCompleted() {
				Log.d(LOG_TAG, "subscribeOn " + Thread.currentThread());
				Log.d(LOG_TAG, "onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				Log.e(LOG_TAG, "onError", e);
			}
		};

		 myObservable.subscribe(mySubscriber);
	}


}
