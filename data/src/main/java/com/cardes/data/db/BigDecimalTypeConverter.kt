package com.cardes.data.db

import androidx.room.TypeConverter
import java.math.BigDecimal

class BigDecimalTypeConverter {
    @TypeConverter
    fun fromBigDecimal(bigDecimal: BigDecimal): Double = bigDecimal.toDouble()

    @TypeConverter
    fun fromDouble(double: Double): BigDecimal = double.toBigDecimal()
}
