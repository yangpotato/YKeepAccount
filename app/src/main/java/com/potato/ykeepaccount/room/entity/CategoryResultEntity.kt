package com.potato.ykeepaccount.room.entity

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.TypeConverters
import com.potato.ykeepaccount.room.converters.CategoryConverters

@TypeConverters(CategoryConverters::class)
class CategoryResultEntity(
    @Embedded
    var categoryEntity: CategoryEntity,
    @Relation(
        parentColumn = "primary_id",
        entityColumn = "father_id"
    )
    var categoryList: MutableList<CategoryEntity>,

    @Relation(
        parentColumn = "primary_id",
        entityColumn = "is_common"
    )
    var commonList: MutableList<CategoryEntity>
){
    fun getList(): MutableList<CategoryEntity>{
        return if (categoryEntity.PrimaryId == 1L) commonList else categoryList
    }
}