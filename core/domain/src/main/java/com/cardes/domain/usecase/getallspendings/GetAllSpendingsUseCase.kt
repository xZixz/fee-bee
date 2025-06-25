package com.cardes.domain.usecase.getallspendings

import com.cardes.domain.entity.Spending
import com.cardes.domain.repository.SpendingRepository
import javax.inject.Inject

interface GetAllSpendingsUseCase {
    suspend operator fun invoke(): Result<List<Spending>>
}

class GetAllSpendingsUseCaseImpl @Inject constructor(
    private val spendingRepository: SpendingRepository,
) : GetAllSpendingsUseCase {
    override suspend fun invoke(): Result<List<Spending>> = spendingRepository.getAllSpendings()
}
