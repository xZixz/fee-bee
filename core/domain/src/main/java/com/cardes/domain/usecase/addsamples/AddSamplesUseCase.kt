package com.cardes.domain.usecase.addsamples

import com.cardes.domain.repository.SpendingRepository

interface AddSamplesUseCase {
    suspend operator fun invoke()
}

class AddSamplesUseCaseImpl(
    private val spendingRepository: SpendingRepository,
) : AddSamplesUseCase {
    override suspend fun invoke() {
        spendingRepository.createSamples()
    }
}
