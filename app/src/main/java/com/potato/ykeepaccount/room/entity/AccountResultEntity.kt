package com.potato.ykeepaccount.room.entity

import androidx.room.Embedded
import androidx.room.Ignore
import java.math.BigDecimal

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
){
   @Ignore
   var totalDayMoney: BigDecimal = BigDecimal("0")
   @Ignore
   var incomeDayMoney: BigDecimal = BigDecimal("0")
   @Ignore
   var expensesMoney: BigDecimal = BigDecimal("0")
}