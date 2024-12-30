package com.cardes.data.db

import androidx.room.TypeConverter
import java.math.BigDecimal

class BigDecimalTypeConverter {
    @TypeConverter
    fun fromBigDecimal(bigDecimal: BigDecimal): String = bigDecimal.toPlainString()

    @TypeConverter
    fun fromDouble(string: String): BigDecimal = string.toBigDecimal()
}
