package com.cardes.domain.usecase.gettotalspentbycategoriesinmonth

import java.math.BigDecimal

fun interface GetTotalSpentByCategoriesInMonthUseCase {
    suspend operator fun invoke(
        month: Int,
        year: Int,
    ): Result<Map<String, BigDecimal>>
}
