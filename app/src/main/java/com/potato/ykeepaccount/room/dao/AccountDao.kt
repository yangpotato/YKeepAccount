package com.potato.ykeepaccount.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.potato.ykeepaccount.room.entity.AccountEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface AccountDao {
    /**
     * 添加记账
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAccount(accountEntity: AccountEntity) : Single<Long>

    /**
     * 查询所有记账记录
     */
    @Query("SELECT * FROM account")
    fun getAccount() : Flowable<List<AccountEntity>>
}