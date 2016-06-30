package com.skyline.rxjavademo.util;


import android.os.AsyncTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 方便调用AsyncTask的工具方法
 * Created by gengxin on 15/11/3.
 */
public class AsyncTaskUtil {

	private static final String LOG_TAG = "AsyncTaskUtil";
	private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
	private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
	private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
	private static ExecutorService highPriorityThreadPool = new ThreadPoolExecutor(
			CORE_POOL_SIZE,
			MAXIMUM_POOL_SIZE,
			1,
			TimeUnit.SECONDS,
			new LinkedBlockingDeque<Runnable>(128),
			new ThreadFactory() {
				private AtomicInteger indexGenerator = new AtomicInteger(1);

				@Override
				public Thread newThread(Runnable r) {
					return new Thread(
							r,
							"high_priority_" + indexGenerator.getAndIncrement()
					);
				}
			}
	);
	private static ExecutorService lowPriorityThreadPool = new ThreadPoolExecutor(
			3,
			Integer.MAX_VALUE,
			1,
			TimeUnit.SECONDS,
			new LinkedBlockingDeque<Runnable>(128),
			new ThreadFactory() {
				private AtomicInteger indexGenerator = new AtomicInteger(1);

				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(
							r,
							"low_priority_" + indexGenerator.getAndIncrement()
					);
					t.setDaemon(true);
					t.setPriority(Thread.MIN_PRIORITY);
					return t;
				}
			}
	);

	public static void executeSerial(Runnable runnable) {
		AsyncTask.SERIAL_EXECUTOR.execute(runnable);
	}

	public static void execute(Runnable runnable) {
		highPriorityThreadPool.execute(runnable);
	}

	public static void executeLowPriority(Runnable runnable) {
		lowPriorityThreadPool.execute(runnable);
	}

	public static ExecutorService getLowPriorityThreadPool() {
		return lowPriorityThreadPool;
	}
}

