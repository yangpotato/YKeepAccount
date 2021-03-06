package com.potato.ykeepaccount.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.potato.ykeepaccount.room.converters.MoneyConverters
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "account")
//, foreignKeys = [ForeignKey(
//entity = CategoryEntity::class,
//parentColumns = ["category_id"],
//childColumns = ["category_id"]
//)]
@TypeConverters(MoneyConverters::class)
data class AccountEntity(
    var money : BigDecimal, //金额
    @ColumnInfo(name = "type_id") var typeId : Long,  //类别
    @ColumnInfo(name = "category_id") var categoryId : Long,  //分类ID
    var remark : String = "",  //备注
    @ColumnInfo(name = "img_url") var imgUrl : String = "",  //图片链接
    @ColumnInfo(name = "cost_time") var costTime : Long,  //消费时间
    @ColumnInfo(name = "edit_time") var editTime : Calendar = Calendar.getInstance(), //修改时间
    @ColumnInfo(name = "create_time") var createTime : Calendar = Calendar.getInstance()  //创建时间
) {
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0

    fun getCostTimeFormat() : String{
        return SimpleDateFormat("MM月dd日").format(costTime)
    }

}