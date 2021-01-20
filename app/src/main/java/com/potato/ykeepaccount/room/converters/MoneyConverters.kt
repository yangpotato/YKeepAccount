package com.potato.ykeepaccount.room.converters

import androidx.room.TypeConverter
import java.math.BigDecimal

class MoneyConverters {
	@TypeConverter
	fun doubleToBigDecimal(value: Double) : BigDecimal{
		return BigDecimal(value.toString())
	}

	@TypeConverter
	fun bigDecimalToDouble(value: BigDecimal): Double{
		return value.toDouble()
	}
}