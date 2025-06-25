package com.cardes.domain.usecase.removecategory

import com.cardes.domain.repository.CategoryRepository
import javax.inject.Inject

interface RemoveCategoryUseCase {
    suspend operator fun invoke(categoryId: Long): Result<Unit>
}

class RemoveCategoryUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : RemoveCategoryUseCase {
    override suspend fun invoke(categoryId: Long): Result<Unit> = categoryRepository.removeCategory(categoryId = categoryId)
}
