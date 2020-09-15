package com.potato.ykeepaccount.room.converters

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.potato.ykeepaccount.room.entity.CategoryEntity

class CategoryConverters {

    @TypeConverter
    fun getCategoryFromString(value: String) : List<CategoryEntity>{
        return Gson().fromJson<List<CategoryEntity>>(value, Array<CategoryEntity>::class.java).toMutableList()
    }

    @TypeConverter
    fun storeCategoryToString(categoryEntity: MutableList<CategoryEntity>): String {
        return Gson().toJson(categoryEntity)
    }
}