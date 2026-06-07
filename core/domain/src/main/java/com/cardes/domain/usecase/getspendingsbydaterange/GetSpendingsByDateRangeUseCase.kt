package com.cardes.domain.usecase.getspendingsbydaterange

import com.cardes.domain.entity.Spending

fun interface GetSpendingsByDateRangeUseCase {
    suspend operator fun invoke(
        from: Long,
        to: Long,
    ): Result<List<Spending>>
}
