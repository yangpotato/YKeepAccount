package com.potato.ykeepaccount.room.entity

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Junction
import androidx.room.Relation
import java.text.SimpleDateFormat

data class AccountResultEntity(
   @Embedded
   var account: AccountEntity,
   var categoryUrl: String?,
   var coefficient: Int?,
   var typeId: Long?,
   var typeName: String?,
   var typePrimaryId: Long?,
   var typePrimaryName: String?,
   var categoryId: Long?,
   var categoryName: String?,
   var categoryPrimaryId: Long?,
   var categoryPrimaryName: String?
)