package com.cardes.domain.usecase.addcategory

import com.cardes.domain.repository.CategoryRepository
import javax.inject.Inject

interface AddCategoryUseCase {
    suspend operator fun invoke(name: String): Result<Unit>
}

class AddCategoryUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : AddCategoryUseCase {
    override suspend fun invoke(name: String): Result<Unit> = categoryRepository.createCategory(name)
}
