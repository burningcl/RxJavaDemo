package com.skyline.rxjavademo.meta;

import com.skyline.db.jerrymouse.core.annotation.DbField;
import com.skyline.db.jerrymouse.core.annotation.DbTable;
import com.skyline.db.jerrymouse.core.annotation.PrimaryKey;
import com.skyline.db.jerrymouse.core.type.SortType;

/**
 * Created by jairus on 16/6/28.
 */

@DbTable(name = CacheMeta.TABLE_NAME)
public class CacheMeta {

	public static final String TABLE_NAME = "TB_CACHE";

	@DbField(primaryKey = @PrimaryKey(primaryKey = true, autoIncrement = true))
	public long id;

	@DbField(index = SortType.ASC)
	public String key;

	@DbField
	public String value;

	@DbField
	public long  createTime;

	@DbField
	public long  expiration;

}
