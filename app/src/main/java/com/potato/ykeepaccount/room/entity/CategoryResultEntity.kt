package com.potato.ykeepaccount.room.entity

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.TypeConverters
import com.potato.ykeepaccount.room.converters.CategoryConverters

@TypeConverters(CategoryConverters::class)
data class CategoryResultEntity(
    @Embedded
    var categoryEntity: CategoryEntity,
    @Relation(
        parentColumn = "primary_id",
        entityColumn = "father_id"
    )
    var categoryList: MutableList<CategoryEntity>
)