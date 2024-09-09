package com.cardes.domain.usecase.updatespending

import com.cardes.domain.repository.SpendingRepository
import java.math.BigDecimal
import javax.inject.Inject

interface UpdateSpendingUseCase {
    suspend operator fun invoke(
        id: Long,
        content: String,
        time: Long,
        amount: BigDecimal,
        categoryIds: List<Long>,
    ): Result<Unit>
}

class UpdateSpendingUseCaseImpl @Inject constructor(
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

