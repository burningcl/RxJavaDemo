package com.skyline.rxjavademo.bl;

import android.util.Log;

import com.google.gson.Gson;
import com.skyline.db.jerrymouse.core.DaoProxy;
import com.skyline.db.jerrymouse.core.util.StringUtils;
import com.skyline.rxjavademo.common.ActionListener;
import com.skyline.rxjavademo.db.CacheDao;
import com.skyline.rxjavademo.meta.CacheMeta;
import com.skyline.rxjavademo.util.AsyncTaskUtil;

/**
 * Created by jairus on 16/6/29.
 */
public class AsyncDbCache {

	final static String LOG_TAG = AsyncDbCache.class.getSimpleName();

	final static long DEFAULT_EXPIRATION = 60 * 60 * 1000;

	final static Gson gson = new Gson();

	static CacheDao cacheDao = null;

	protected static CacheDao getDao() throws Exception {
		if (cacheDao != null) {
			return cacheDao;
		}
		synchronized (AsyncDbCache.class) {
			if (cacheDao != null) {
				return cacheDao;
			}
			cacheDao = DaoProxy.getDao(CacheDao.class);
		}
		return cacheDao;
	}

	public static void put(final String key, final Object value) {
		put(key, value, DEFAULT_EXPIRATION);
	}

	public static void put(final String key, final Object value, final long expiration) {
		if (value == null) {
			return;
		}
		AsyncTaskUtil.execute(new Runnable() {
			@Override
			public void run() {
				try {
					CacheDao cacheDao = getDao();
					String valueJson = gson.toJson(value);
					CacheMeta cacheMeta = cacheDao.select(key);
					if (cacheMeta == null) {
						cacheMeta = new CacheMeta();
						cacheMeta.key = key;
						cacheMeta.value = valueJson;
						cacheMeta.createTime = System.currentTimeMillis();
						cacheMeta.expiration = expiration;
						cacheDao.insert(cacheMeta);
					} else {
						cacheDao.update(key, valueJson);
					}
				} catch (Exception e) {
					Log.w(LOG_TAG, "fail to put", e);
				}
			}
		});
	}

	public static <T> void get(final String key, final ActionListener<T> callback, final Class<T> clazz) {
		if (callback == null || clazz == null) {
			return;
		}
		if (StringUtils.isEmpty(key)) {
			callback.onAction(null);
		}
		AsyncTaskUtil.execute(new Runnable() {
			@Override
			public void run() {
				try {
					CacheDao cacheDao = getDao();
					CacheMeta cacheMeta = cacheDao.select(key);
					if (cacheMeta == null || StringUtils.isEmpty(cacheMeta.value)) {
						callback.onAction(null);
						return;
					}
					long expiration = cacheMeta.expiration;
					if (expiration > 0 && System.currentTimeMillis() - cacheMeta.createTime > expiration) {
						remove(key);
						callback.onAction(null);
						return;
					}
					String valueJson = cacheMeta.value;
					T data = gson.fromJson(valueJson, clazz);
					callback.onAction(data);
				} catch (Exception e) {
					Log.w(LOG_TAG, "fail to get", e);
				}
			}
		});
	}

	public static void remove(final String key) {
		AsyncTaskUtil.execute(new Runnable() {
			@Override
			public void run() {
				try {
					CacheDao cacheDao = getDao();
					cacheDao.delete(key);
				} catch (Exception e) {
					Log.w(LOG_TAG, "fail to remove", e);
				}
			}
		});
	}

}
