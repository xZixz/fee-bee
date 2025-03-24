package com.cardes.data.fake

import com.cardes.domain.base.MonthYear
import com.cardes.domain.entity.Category
import com.cardes.domain.entity.Spending
import java.util.Calendar

object Fake {
    val categories =
        listOf(
            Category(
                id = 1,
                name = "Food",
                emoji = "🍔",
            ),
            Category(
                id = 2,
                name = "Cleaning",
                emoji = "\uD83E\uDEA3",
            ),
            Category(
                id = 3,
                name = "Daily",
                emoji = "💡",
            ),
            Category(
                id = 4,
                name = "Sky",
                emoji = "",
            ),
        )
    val spendings =
        listOf(
            Spending(
                id = 1,
                time = Thu_May_02_2004,
                amount = 70.toBigDecimal(),
                content = "Lunch",
                categories = listOf(
                    categories[0],
                    categories[2],
                ),
            ),
            Spending(
                id = 2,
                time = Wed_May_02_2004,
                amount = 300.toBigDecimal(),
                content = "bTask",
                categories = listOf(categories[1]),
            ),
            Spending(
                id = 3,
                time = Wed_May_02_2004,
                amount = 100.toBigDecimal(),
                content = "Book",
                categories = listOf(),
            ),
            Spending(
                id = 1,
                time = Sat_June_22_2004,
                amount = 70.toBigDecimal(),
                content = "Lunch",
                categories = listOf(
                    categories[0],
                    categories[2],
                ),
            ),
            Spending(
                id = 2,
                time = Sat_June_22_2004,
                amount = 300.toBigDecimal(),
                content = "bTask",
                categories = listOf(categories[1]),
            ),
            Spending(
                id = 3,
                time = Wed_Jul_10_2004,
                amount = 100.toBigDecimal(),
                content = "Book",
                categories = listOf(),
            ),
        )
}

@Suppress("ktlint:standard:property-naming")
private const val Thu_May_02_2004 = 1714642661906L

@Suppress("ktlint:standard:property-naming")
private const val Wed_May_02_2004 = 1714556426610L

@Suppress("ktlint:standard:property-naming")
private const val Sat_June_22_2004 = 1719068901523L

@Suppress("ktlint:standard:property-naming")
private const val Wed_Jul_10_2004 = 1720624543532L
