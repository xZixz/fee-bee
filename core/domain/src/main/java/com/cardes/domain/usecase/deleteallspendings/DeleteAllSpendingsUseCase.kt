package com.cardes.domain.usecase.deleteallspendings

import com.cardes.domain.repository.SpendingRepository

interface DeleteAllSpendingsUseCase {
    suspend operator fun invoke()
}

class DeleteAllSpendingsUseCaseImpl constructor(
    private val spendingRepository: SpendingRepository,
) : DeleteAllSpendingsUseCase {
    override suspend fun invoke() {
        spendingRepository.deleteAll()
    }
}
