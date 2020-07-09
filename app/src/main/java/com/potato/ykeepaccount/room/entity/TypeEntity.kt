package com.potato.ykeepaccount.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "type")
data class TypeEntity(
    var name : String, //类型名称
    var fatherId : Long //父级ID
) {
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
}