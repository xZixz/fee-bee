package com.cardes.data

import android.icu.util.Calendar
import com.cardes.data.db.entity.CategoryEntity
import com.cardes.data.db.entity.SpendingEntity
import java.math.BigDecimal

object TestUtil {
    fun createCategories(number: Int): List<CategoryEntity> =
        (1..number).map { index ->
            CategoryEntity(
                categoryId = index.toLong(),
                name = "Category #$index",
            )
        }

    fun createSpending(
        id: Long = 0L,
        amount: BigDecimal = BigDecimal(100L),
        content: String = "Test Spending",
        time: Long = System.currentTimeMillis(),
    ) = SpendingEntity(
        id = id,
        amount = amount,
        content = content,
        time = time,
    )

    fun timeOfFirstDayOfMonthInMilliseconds(month: Int): Long =
        Calendar
            .getInstance()
            .apply {
                set(Calendar.MONTH, month - 1)
                set(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.HOUR, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
}
