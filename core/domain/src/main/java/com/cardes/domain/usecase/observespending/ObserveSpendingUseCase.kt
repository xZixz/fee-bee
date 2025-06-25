package com.cardes.domain.usecase.observespending

import com.cardes.domain.entity.Spending
import com.cardes.domain.repository.SpendingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveSpendingUseCase {
    operator fun invoke(spendingId: Long): Flow<Spending>
}

class ObserveSpendingUseCaseImpl @Inject constructor(
    private val spendingRepository: SpendingRepository,
) : ObserveSpendingUseCase {
    override fun invoke(spendingId: Long): Flow<Spending> = spendingRepository.observeSpending(spendingId)
}
