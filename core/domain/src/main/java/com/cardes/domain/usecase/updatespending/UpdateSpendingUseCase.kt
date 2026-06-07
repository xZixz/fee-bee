package com.cardes.domain.usecase.updatespending

import com.cardes.domain.repository.SpendingRepository
import java.math.BigDecimal

interface UpdateSpendingUseCase {
    suspend operator fun invoke(
        id: Long,
        content: String,
        time: Long,
        amount: BigDecimal,
        categoryIds: List<Long>,
    ): Result<Unit>
}

class UpdateSpendingUseCaseImpl(
    private val spendingRepository: SpendingRepository,
) : UpdateSpendingUseCase {
    override suspend fun invoke(
        id: Long,
        content: String,
        time: Long,
        amount: BigDecimal,
        categoryIds: List<Long>,
    ): Result<Unit> =
        spendingRepository.updateSpending(
            id = id,
            content = content,
            time = time,
            amount = amount,
            categoryIds = categoryIds,
        )
}
