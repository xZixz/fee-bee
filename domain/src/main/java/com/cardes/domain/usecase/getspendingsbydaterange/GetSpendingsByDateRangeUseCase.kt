package com.cardes.domain.usecase.getspendingsbydaterange

import com.cardes.domain.entity.Spending
import com.cardes.domain.repository.SpendingRepository
import javax.inject.Inject

interface GetSpendingsByDateRangeUseCase {
    suspend operator fun invoke(
        from: Long,
        to: Long,
    ): Result<List<Spending>>
}

class GetSpendingsByDateRangeUseCaseImpl @Inject constructor(
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
