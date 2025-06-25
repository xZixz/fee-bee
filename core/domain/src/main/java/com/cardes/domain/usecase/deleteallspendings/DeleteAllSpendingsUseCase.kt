package com.cardes.domain.usecase.deleteallspendings

import com.cardes.domain.repository.SpendingRepository
import javax.inject.Inject

interface DeleteAllSpendingsUseCase {
    suspend operator fun invoke()
}

class DeleteAllSpendingsUseCaseImpl @Inject constructor(
    private val spendingRepository: SpendingRepository,
) : DeleteAllSpendingsUseCase {
    override suspend fun invoke() {
        spendingRepository.deleteAll()
    }
}
