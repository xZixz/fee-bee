package com.cardes.domain.usecase.getspendingsbycategories

import com.cardes.domain.entity.Spending
import com.cardes.domain.repository.SpendingRepository
import javax.inject.Inject

interface GetSpendingsByCategoriesUseCase {
    suspend operator fun invoke(categoryIds: List<Long>): Result<List<Spending>>
}

class GetSpendingsByCategoriesUseCaseImpl @Inject constructor(
    private val spendingRepository: SpendingRepository,
) : GetSpendingsByCategoriesUseCase {
    override suspend fun invoke(categoryIds: List<Long>): Result<List<Spending>> = spendingRepository.getSpendingsByCategoryIds(categoryIds)
}
