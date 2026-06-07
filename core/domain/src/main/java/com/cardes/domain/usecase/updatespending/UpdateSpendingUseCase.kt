package com.cardes.domain.usecase.updatespending

import java.math.BigDecimal

fun interface UpdateSpendingUseCase {
    suspend operator fun invoke(
        id: Long,
        content: String,
        time: Long,
        amount: BigDecimal,
        categoryIds: List<Long>,
    ): Result<Unit>
}
