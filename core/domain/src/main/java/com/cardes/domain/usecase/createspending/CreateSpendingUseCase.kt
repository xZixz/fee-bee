package com.cardes.domain.usecase.createspending

import com.cardes.domain.repository.SpendingRepository
import java.math.BigDecimal

interface CreateSpendingUseCase {
    suspend operator fun invoke(
        time: Long,
        content: String,
        amount: BigDecimal,
        categoryIds: List<Long>,
    ): Result<Unit>
}

class CreateSpendingUseCaseImpl constructor(
    private val spendingRepository: SpendingRepository,
) : CreateSpendingUseCase {
    override suspend fun invoke(
        time: Long,
        content: String,
        amount: BigDecimal,
        categoryIds: List<Long>,
    ): Result<Unit> =
        spendingRepository.createSpending(
            time,
            content,
            amount,
            categoryIds,
        )
}
