package com.potato.ykeepaccount.room.entity

import androidx.room.*

@Entity(tableName = "type")
data class TypeEntity(
    @ColumnInfo(name = "type_name") var name : String, //类型名称
    @ColumnInfo(name = "coefficient") var coefficient : Int, //类型名称
    @ColumnInfo(name = "primary_id") var PrimaryId : Long, //类型名称
    @ColumnInfo(name = "father_id") var fatherId : Long //父级ID
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "type_id") var typeId : Long = 0
}