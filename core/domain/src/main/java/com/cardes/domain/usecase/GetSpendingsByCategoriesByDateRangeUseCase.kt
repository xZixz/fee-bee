package com.cardes.domain.usecase

import com.cardes.domain.entity.Spending

fun interface GetSpendingsByCategoriesByDateRangeUseCase {
    suspend operator fun invoke(
        categoryIds: List<Long>,
        from: Long,
        to: Long,
    ): Result<List<Spending>>
}
