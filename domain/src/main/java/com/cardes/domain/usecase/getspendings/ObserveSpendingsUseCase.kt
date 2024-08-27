package com.cardes.domain.usecase.getspendings

import com.cardes.domain.entity.Spending
import com.cardes.domain.repository.SpendingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveSpendingsUseCase {
    operator fun invoke(): Flow<List<Spending>>
}

class ObserveSpendingsUseCaseImpl @Inject constructor(
    private val spendingRepository: SpendingRepository,
) : ObserveSpendingsUseCase {
    override operator fun invoke() = spendingRepository.observeSpendings()
}
