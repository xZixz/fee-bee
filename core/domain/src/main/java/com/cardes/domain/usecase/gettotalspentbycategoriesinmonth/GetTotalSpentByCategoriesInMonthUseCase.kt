package com.cardes.domain.usecase.gettotalspentbycategoriesinmonth

import com.cardes.domain.repository.SpendingRepository
import java.math.BigDecimal

interface GetTotalSpentByCategoriesInMonthUseCase {
    suspend operator fun invoke(
        month: Int,
        year: Int,
    ): Result<Map<String, BigDecimal>>
}

class GetTotalSpentByCategoriesInMonthUseCaseImpl constructor(
    val spendingRepository: SpendingRepository,
) : GetTotalSpentByCategoriesInMonthUseCase {
    override suspend fun invoke(
        month: Int,
        year: Int,
    ): Result<Map<String, BigDecimal>> =
        spendingRepository.getTotalSpentByCategoriesInMonth(
            month = month,
            year = year,
        )
}
