package com.cardes.domain.usecase.observespending

import com.cardes.domain.entity.Spending
import com.cardes.domain.repository.SpendingRepository
import kotlinx.coroutines.flow.Flow

interface ObserveSpendingUseCase {
    operator fun invoke(spendingId: Long): Flow<Spending>
}

class ObserveSpendingUseCaseImpl(
    private val spendingRepository: SpendingRepository,
) : ObserveSpendingUseCase {
    override fun invoke(spendingId: Long): Flow<Spending> = spendingRepository.observeSpending(spendingId)
}
