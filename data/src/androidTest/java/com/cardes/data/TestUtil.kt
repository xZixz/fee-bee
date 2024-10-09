package com.cardes.data

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
}
