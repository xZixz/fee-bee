package com.cardes.domain.usecase.observespendings

import com.cardes.domain.entity.Spending
import kotlinx.coroutines.flow.Flow

fun interface ObserveSpendingsUseCase {
    operator fun invoke(): Flow<List<Spending>>
}
