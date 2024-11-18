package com.cardes.domain.usecase

import com.cardes.domain.entity.Spending
import com.cardes.domain.repository.SpendingRepository
import javax.inject.Inject

interface GetSpendingsByCategoriesByDateRange {
    suspend operator fun invoke(
        categoryIds: List<Long>,
        from: Long,
        to: Long,
    ): Result<List<Spending>>
}

class GetSpendingsByCategoriesByDateRangeImpl @Inject constructor(
    private val spendingRepository: SpendingRepository,
) : GetSpendingsByCategoriesByDateRange {
    override suspend fun invoke(
        categoryIds: List<Long>,
        from: Long,
        to: Long,
    ): Result<List<Spending>> =
        spendingRepository.getSpendingsByCategoriesByDateRange(
            categoryIds = categoryIds,
            from = from,
            to = to,
        )
}
