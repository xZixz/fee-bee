package com.cardes.domain.usecase.addsamples

import com.cardes.domain.repository.SpendingRepository
import javax.inject.Inject

interface AddSamplesUseCase {
    suspend operator fun invoke()
}

class AddSamplesUseCaseImpl @Inject constructor(
    private val spendingRepository: SpendingRepository,
) : AddSamplesUseCase {
    override suspend fun invoke() {
        spendingRepository.createSamples()
    }
}
