package com.potato.ykeepaccount.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @ColumnInfo(name = "category_name") var categoryName : String,
    @ColumnInfo(name = "father_id") var fatherId : Long
) {
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
}