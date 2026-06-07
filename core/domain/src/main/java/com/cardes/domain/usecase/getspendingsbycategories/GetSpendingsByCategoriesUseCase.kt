package com.cardes.domain.usecase.getspendingsbycategories

import com.cardes.domain.entity.Spending

fun interface GetSpendingsByCategoriesUseCase {
    suspend operator fun invoke(categoryIds: List<Long>): Result<List<Spending>>
}
