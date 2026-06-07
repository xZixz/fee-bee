package com.cardes.domain.usecase.getspendingsbydaterange

import com.cardes.domain.entity.Spending
import com.cardes.domain.repository.SpendingRepository

interface GetSpendingsByDateRangeUseCase {
    suspend operator fun invoke(
        from: Long,
        to: Long,
    ): Result<List<Spending>>
}

class GetSpendingsByDateRangeUseCaseImpl(
    private val spendingRepository: SpendingRepository,
) : GetSpendingsByDateRangeUseCase {
    override suspend fun invoke(
        from: Long,
        to: Long,
    ): Result<List<Spending>> =
        spendingRepository.getSpendingsByDateRage(
            from = from,
            to = to,
        )
}
