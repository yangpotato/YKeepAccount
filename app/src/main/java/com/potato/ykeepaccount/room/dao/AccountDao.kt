package com.potato.ykeepaccount.room.dao

import androidx.room.*
import com.potato.ykeepaccount.room.entity.AccountEntity
import com.potato.ykeepaccount.room.entity.AccountResultEntity
import com.potato.ykeepaccount.room.entity.AccountTotalEntity
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
    @Transaction
    @Query("SELECT a.*, a.type_id as typeId, a.category_id as categoryId, x.type_name as typeName, s.primary_id as typePrimaryId, s.type_name as typePrimaryName, b.category_name as categoryName, c.primary_id as categoryPrimaryId, c.category_name as categoryPrimaryName, c.category_url as categoryUrl, x.coefficient as coefficient FROM account a LEFT JOIN category b on a.category_id=b.category_id LEFT JOIN category c on b.father_id=c.primary_id LEFT JOIN type x on a.type_id=x.type_id LEFT JOIN type s on x.father_id=s.primary_id ORDER BY a.cost_time DESC")
    fun getAccount() : Single<MutableList<AccountResultEntity>>


    @Query("SELECT " +
            "SUM(account.money * type.coefficient) as total, " +
            "SUM(CASE type.coefficient WHEN -1 THEN account.money * type.coefficient ELSE 0 END) as expenses, " +
            "SUM(CASE type.coefficient WHEN 1 THEN account.money * type.coefficient ELSE 0 END) as income " +
            "FROM account LEFT JOIN type on account.type_id = type.type_id")
    fun getAccountTotal(): Single<AccountTotalEntity>
//
//    @Query("select sum(money * b.coefficient),b.coefficient from account a left join type b on a.type_id=b.type_id")
//    fun getTotalMoney(startTime: Long, endTime: Long)
//
//    @Query("select sum(money * b.coefficient),b.coefficient from account a left join type b on a.type_id=b.type_id where b.coefficient=-1")
//    fun getTotalSpendMoney(startTime: Long, endTime: Long)
}