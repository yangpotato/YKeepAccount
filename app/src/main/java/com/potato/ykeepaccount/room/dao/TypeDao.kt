package com.potato.ykeepaccount.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.potato.ykeepaccount.room.entity.CategoryEntity
import com.potato.ykeepaccount.room.entity.TypeEntity
import io.reactivex.Single

@Dao
interface TypeDao {
    /**
     * 添加新类别
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addType(types: List<TypeEntity>) : Single<List<Long>>

    /**
     * 获取类别
     */
    @Query("SELECT * FROM type WHERE father_id='0'")
    fun queryPrimaryTypeList() : Single<List<TypeEntity>>

    /**
     * 获取类别
     */
    @Query("SELECT * FROM type WHERE father_id=:id")
    fun queryTypeListByPrimaryId(id : Long) : Single<List<TypeEntity>>
}