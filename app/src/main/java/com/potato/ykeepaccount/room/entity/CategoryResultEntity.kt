package com.potato.ykeepaccount.room.entity

import androidx.room.Embedded
import androidx.room.TypeConverters
import com.potato.ykeepaccount.room.converters.CategoryConverters

data class CategoryResultEntity(

    @TypeConverters(CategoryConverters::class)
    var categoryList: MutableList<CategoryEntity>
)