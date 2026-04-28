package com.cardes.domain.usecase.updatecategoryname

import com.cardes.domain.repository.CategoryRepository

interface UpdateCategoryNameUseCase {
    suspend operator fun invoke(
        categoryId: Long,
        categoryName: String,
    ): Result<Unit>
}

class UpdateCategoryNameUseCaseImpl constructor(
    private val categoryRepository: CategoryRepository,
) : UpdateCategoryNameUseCase {
    override suspend fun invoke(
        categoryId: Long,
        categoryName: String,
    ): Result<Unit> =
        categoryRepository.updateCategoryName(
            categoryId = categoryId,
            categoryName = categoryName,
        )
}
