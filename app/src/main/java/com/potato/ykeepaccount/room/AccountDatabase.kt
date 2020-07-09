package com.potato.ykeepaccount.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.base.commom.utils.LogUtil
import com.potato.ykeepaccount.room.dao.AccountDao
import com.potato.ykeepaccount.room.dao.CategoryDao
import com.potato.ykeepaccount.room.dao.TypeDao
import com.potato.ykeepaccount.room.entity.AccountEntity
import com.potato.ykeepaccount.room.entity.CategoryEntity
import com.potato.ykeepaccount.room.entity.TypeEntity
import io.reactivex.Scheduler
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.simpleframework.xml.convert.Convert

@Database(entities = [CategoryEntity::class, AccountEntity::class, TypeEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AccountDatabase : RoomDatabase() {
    abstract fun categoryDao() : CategoryDao
    abstract fun accountDao() : AccountDao
    abstract fun typeDao() : TypeDao

    companion object{
        private var instance : AccountDatabase? = null

        fun getInstance(context: Context) : AccountDatabase{
            return instance?: synchronized(this){
                Room.databaseBuilder(context.applicationContext, AccountDatabase::class.java, "keep_account")
                   .addCallback(object :Callback(){
                       override fun onCreate(db: SupportSQLiteDatabase) {
                           super.onCreate(db)
                           LogUtil.i("创建数据库")
                           getInstance(context.applicationContext).categoryDao().addCategory(getInitialCategoryList()).subscribeOn(
                               Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object :SingleObserver<List<Long>>{
                               override fun onSuccess(t: List<Long>) {
                                   LogUtil.i("初始化数据成功")
                               }

                               override fun onSubscribe(d: Disposable) {

                               }

                               override fun onError(e: Throwable) {
                               }

                           })
                       }
                   }).allowMainThreadQueries().build().also { instance = it }
            }
        }

        /**
         * 初始化一些数据
         */
        fun getInitialCategoryList() : List<CategoryEntity>{
            val categoryList : MutableList<CategoryEntity> = ArrayList()
            categoryList.add(CategoryEntity("常用", 1 , 0))
            categoryList.add(CategoryEntity("餐饮", 2, 0))
            categoryList.add(CategoryEntity("交通", 3, 0))
            categoryList.add(CategoryEntity("购物", 4, 0))
            categoryList.add(CategoryEntity("娱乐", 5, 0))
            categoryList.add(CategoryEntity("医药", 6, 0))
            categoryList.add(CategoryEntity("学习", 7, 0))
            categoryList.add(CategoryEntity("早餐", 0, 2))
            categoryList.add(CategoryEntity("午餐", 0, 2))
            categoryList.add(CategoryEntity("晚餐", 0, 2))
            categoryList.add(CategoryEntity("零食", 0, 2))
            categoryList.add(CategoryEntity("水果", 0, 2))
            categoryList.add(CategoryEntity("公交", 0, 3))
            categoryList.add(CategoryEntity("地铁", 0, 3))
            categoryList.add(CategoryEntity("打车", 0, 3))
            categoryList.add(CategoryEntity("火车", 0, 3))
            categoryList.add(CategoryEntity("汽车", 0, 3))
            categoryList.add(CategoryEntity("淘宝", 0, 4))
            categoryList.add(CategoryEntity("京东", 0, 4))
            return categoryList
        }
    }

}