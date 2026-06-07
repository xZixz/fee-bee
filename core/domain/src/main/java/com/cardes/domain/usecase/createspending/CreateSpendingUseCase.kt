package com.cardes.domain.usecase.createspending

import java.math.BigDecimal

fun interface CreateSpendingUseCase {
    suspend operator fun invoke(
        time: Long,
        content: String,
        amount: BigDecimal,
        categoryIds: List<Long>,
    ): Result<Unit>
}
