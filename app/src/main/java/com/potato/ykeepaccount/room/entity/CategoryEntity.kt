package com.potato.ykeepaccount.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @ColumnInfo(name = "category_name") var categoryName : String,
    @ColumnInfo(name = "category_id") var categoryId : Long,  //大类id
    @ColumnInfo(name = "father_id") var fatherId : Long,
    @ColumnInfo(name = "last_time") var lastTime : Long = 0 //上次使用改标签的时间
) {
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
}