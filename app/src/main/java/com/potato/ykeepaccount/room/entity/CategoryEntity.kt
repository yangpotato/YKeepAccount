package com.potato.ykeepaccount.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @ColumnInfo(name = "category_name") var categoryName : String,
    @ColumnInfo(name = "primary_id") var PrimaryId : Long,  //大类id
    @ColumnInfo(name = "father_id") var fatherId : Long,
    @ColumnInfo(name = "last_time") var lastTime : Long = 0, //上次使用改标签的时间
    @ColumnInfo(name = "category_url") var categoryUrl : String = "",
    @ColumnInfo(name = "is_common") var isCommon : Int = 0 //是否是常用 0：不是  1：是
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")var categoryId : Long = 0
}