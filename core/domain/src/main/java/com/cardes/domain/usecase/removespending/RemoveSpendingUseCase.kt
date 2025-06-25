package com.cardes.domain.usecase.removespending

import com.cardes.domain.repository.SpendingRepository
import javax.inject.Inject

interface RemoveSpendingUseCase {
    suspend operator fun invoke(spendingId: Long): Result<Unit>
}

class RemoveSpendingUseCaseImpl @Inject constructor(
    private val spendingRepository: SpendingRepository,
) : RemoveSpendingUseCase {
    override suspend fun invoke(spendingId: Long): Result<Unit> = spendingRepository.removeSpending(spendingId = spendingId)
}
