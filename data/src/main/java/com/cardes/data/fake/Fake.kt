package com.cardes.data.fake

import com.cardes.domain.entity.Category
import com.cardes.domain.entity.Spending

object Fake {
    val categories =
        listOf(
            Category(
                id = 1,
                name = "Food",
            ),
            Category(
                id = 2,
                name = "Cleaning",
            ),
            Category(
                id = 3,
                name = "Daily",
            ),
        )
    val spendings =
        listOf(
            Spending(
                id = 1,
                time = 1714642661906,
                amount = 70.toBigDecimal(),
                content = "Lunch",
                categories = listOf(
                    categories[0],
                    categories[2],
                ),
            ),
            Spending(
                id = 2,
                time = 1714556426610,
                amount = 300.toBigDecimal(),
                content = "bTask",
                categories = listOf(categories[1]),
            ),
            Spending(
                id = 3,
                time = 1714556426610,
                amount = 100.toBigDecimal(),
                content = "Book",
                categories = listOf(),
            ),
        )
}
