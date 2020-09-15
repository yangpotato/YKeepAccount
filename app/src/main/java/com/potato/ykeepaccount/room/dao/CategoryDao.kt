package com.potato.ykeepaccount.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.potato.ykeepaccount.room.entity.CategoryEntity
import com.potato.ykeepaccount.room.entity.CategoryResultEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface CategoryDao {
    /**
     * 添加新标签
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCategory(category : CategoryEntity)

    /**
     * 添加新标签
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCategory(categorys: MutableList<CategoryEntity>) : Single<MutableList<Long>>

    /**
     * 通过id查询分类
     */
    @Query("SELECT * FROM category WHERE category_id=:id")
    fun queryCategory(id : Long) : Observable<List<CategoryEntity>>

    @Query("SELECT * FROM category WHERE primary_id='0' ORDER BY last_time")
    fun queryDefaultCategory() : Single<MutableList<CategoryEntity>>

    @Query("SELECT * FROM category WHERE primary_id='0' ORDER BY last_time LIMIT 8")
    fun queryDefaultCategory1() : MutableList<CategoryEntity>

    @Query("SELECT *, (SELECT b.category_id FROM category b WHERE a.primary_id = b.father_id ) as categoryList FROM category a WHERE a.father_id = 0")
    fun queryAllCategory() : Single<MutableList<CategoryEntity>>

}