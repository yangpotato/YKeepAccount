package com.potato.ykeepaccount.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.potato.ykeepaccount.room.dao.CategoryDao
import com.potato.ykeepaccount.room.entity.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1)
abstract class AccountDatabase : RoomDatabase() {
    abstract fun categoryDao() : CategoryDao

    companion object{
        private var instance : AccountDatabase? = null

        fun getInstance(context: Context) : AccountDatabase{
            return instance?: synchronized(this){
                Room.databaseBuilder(context.applicationContext, AccountDatabase::class.java, "keep_account")
                   .allowMainThreadQueries().build().also { instance = it }
            }
        }
    }
}