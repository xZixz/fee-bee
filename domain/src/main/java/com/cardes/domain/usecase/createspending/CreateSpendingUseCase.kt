package com.cardes.domain.usecase.createspending

import com.cardes.domain.repository.SpendingRepository
import java.math.BigDecimal
import javax.inject.Inject

interface CreateSpendingUseCase {
    suspend operator fun invoke(
        time: Long,
        content: String,
        amount: BigDecimal,
        categoryIds: List<Long>,
    ): Result<Unit>
}

class CreateSpendingUseCaseImpl @Inject constructor(
    private val spendingRepository: SpendingRepository,
) : CreateSpendingUseCase {
    override suspend fun invoke(
        time: Long,
        content: String,
        amount: BigDecimal,
        categoryIds: List<Long>,
    ): Result<Unit> {
        return spendingRepository.createSpending(
            time,
            content,
            amount,
            categoryIds,
        )
    }
}
