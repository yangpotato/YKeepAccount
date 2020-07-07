package com.potato.ykeepaccount.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.potato.ykeepaccount.room.entity.CategoryEntity
import io.reactivex.Flowable

@Dao
interface CategoryDao {
    /**
     * 添加新标签
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCategory(category : CategoryEntity)

    /**
     * 通过id查询分类
     */
    @Query("SELECT * FROM category WHERE id=:id")
    fun queryCategory(id : Long) : Flowable<List<CategoryEntity>>
}