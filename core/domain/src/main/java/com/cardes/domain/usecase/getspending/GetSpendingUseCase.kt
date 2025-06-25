package com.cardes.domain.usecase.getspending

import com.cardes.domain.entity.Spending
import com.cardes.domain.repository.SpendingRepository
import javax.inject.Inject

interface GetSpendingUseCase {
    suspend operator fun invoke(spendingId: Long): Result<Spending>
}

class GetSpendingUseCaseImpl @Inject constructor(
    private val spendingRepository: SpendingRepository,
) : GetSpendingUseCase {
    override suspend fun invoke(spendingId: Long): Result<Spending> = spendingRepository.getSpending(spendingId = spendingId)
}
