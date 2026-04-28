package com.cardes.domain.usecase

import com.cardes.domain.entity.Spending
import com.cardes.domain.repository.SpendingRepository

interface GetSpendingsByCategoriesByDateRangeUseCase {
    suspend operator fun invoke(
        categoryIds: List<Long>,
        from: Long,
        to: Long,
    ): Result<List<Spending>>
}

class GetSpendingsByCategoriesByDateRangeUseCaseImpl constructor(
    private val spendingRepository: SpendingRepository,
) : GetSpendingsByCategoriesByDateRangeUseCase {
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
