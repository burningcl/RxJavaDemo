package com.skyline.rxjavademo.bl;

import android.util.Log;

import com.google.gson.Gson;
import com.skyline.db.jerrymouse.core.DaoProxy;
import com.skyline.db.jerrymouse.core.util.StringUtils;
import com.skyline.rxjavademo.db.CacheDao;
import com.skyline.rxjavademo.meta.CacheMeta;
import com.skyline.rxjavademo.util.AsyncTaskUtil;

/**
 * 同步的数据库cache
 * Created by jairus on 16/6/29.
 */
public class DbCache {

	final static String LOG_TAG = DbCache.class.getSimpleName();

	final static long DEFAULT_EXPIRATION = 60 * 60 * 1000;

	final static Gson gson = new Gson();

	static CacheDao cacheDao = null;

	protected static CacheDao getDao() throws Exception {
		if (cacheDao != null) {
			return cacheDao;
		}
		synchronized (DbCache.class) {
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

	public static <T> T get(final String key, final Class<T> clazz) {
		if (clazz == null || StringUtils.isEmpty(key)) {
			return null;
		}
		try {
			CacheDao cacheDao = getDao();
			CacheMeta cacheMeta = cacheDao.select(key);
			if (cacheMeta == null || StringUtils.isEmpty(cacheMeta.value)) {
				return null;
			}
			long expiration = cacheMeta.expiration;
			if (expiration > 0 && System.currentTimeMillis() - cacheMeta.createTime > expiration) {
				remove(key);
				return null;
			}
			String valueJson = cacheMeta.value;
			return gson.fromJson(valueJson, clazz);
		} catch (Exception e) {
			Log.w(LOG_TAG, "fail to get", e);
		}
		return null;
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
