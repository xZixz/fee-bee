package com.cardes.domain.usecase.observespendings

import com.cardes.domain.entity.Spending
import com.cardes.domain.repository.SpendingRepository
import kotlinx.coroutines.flow.Flow

interface ObserveSpendingsUseCase {
    operator fun invoke(): Flow<List<Spending>>
}

class ObserveSpendingsUseCaseImpl constructor(
    private val spendingRepository: SpendingRepository,
) : ObserveSpendingsUseCase {
    override operator fun invoke() = spendingRepository.observeSpendings()
}
