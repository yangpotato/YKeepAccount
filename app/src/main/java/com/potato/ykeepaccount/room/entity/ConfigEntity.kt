package com.potato.ykeepaccount.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "config")
open class ConfigEntity(
    @ColumnInfo(name = "month_money")
    var mouthMoney : Double
){
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
}