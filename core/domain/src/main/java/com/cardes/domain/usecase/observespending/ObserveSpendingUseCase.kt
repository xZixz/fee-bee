package com.cardes.domain.usecase.observespending

import com.cardes.domain.entity.Spending
import kotlinx.coroutines.flow.Flow

fun interface ObserveSpendingUseCase {
    operator fun invoke(spendingId: Long): Flow<Spending>
}
