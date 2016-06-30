package com.skyline.rxjavademo.db;

import com.skyline.db.jerrymouse.core.Dao;
import com.skyline.db.jerrymouse.core.annotation.Param;
import com.skyline.db.jerrymouse.core.annotation.Sql;
import com.skyline.db.jerrymouse.core.type.SqlType;
import com.skyline.rxjavademo.meta.CacheMeta;

/**
 * Created by jairus on 16/6/28.
 */
public interface CacheDao extends Dao<CacheMeta> {

	@Sql(type = SqlType.DELETE, value = "delete from " + CacheMeta.TABLE_NAME + " where key = ?")
	void delete(@Param String key);

	@Sql(type = SqlType.UPDATE, value = "update " + CacheMeta.TABLE_NAME + " set value = ? where key = ?")
	void update(@Param String value, @Param String key);

	@Sql(type = SqlType.SELECT, value = "select * from " + CacheMeta.TABLE_NAME + " where key = ? limit 0,1")
	CacheMeta select( @Param String key);

}
