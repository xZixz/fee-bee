package com.cardes.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.cardes.data.db.BigDecimalTypeConverter
import java.math.BigDecimal

private const val SPENDING_TABLE_NAME = "spendings"

@Entity(tableName = SPENDING_TABLE_NAME)
@TypeConverters(BigDecimalTypeConverter::class)
data class SpendingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: BigDecimal,
    val content: String,
    val time: Long,
)
