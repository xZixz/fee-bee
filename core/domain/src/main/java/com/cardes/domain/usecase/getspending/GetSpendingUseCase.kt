package com.cardes.domain.usecase.getspending

import com.cardes.domain.entity.Spending

fun interface GetSpendingUseCase {
    suspend operator fun invoke(spendingId: Long): Result<Spending>
}
