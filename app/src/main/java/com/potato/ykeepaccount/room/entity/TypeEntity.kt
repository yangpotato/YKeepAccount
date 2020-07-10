package com.potato.ykeepaccount.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "type")
data class TypeEntity(
    var name : String, //类型名称
    @ColumnInfo(name = "type_id") var typeId : Long, //类型名称
    @ColumnInfo(name = "father_id") var fatherId : Long //父级ID
) {
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
}