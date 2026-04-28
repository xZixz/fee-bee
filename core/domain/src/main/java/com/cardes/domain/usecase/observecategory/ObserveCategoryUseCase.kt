package com.cardes.domain.usecase.observecategory

import com.cardes.domain.entity.Category
import com.cardes.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

interface ObserveCategoryUseCase {
    operator fun invoke(categoryId: Long): Flow<Category?>
}

class ObserveCategoryUseCaseImpl constructor(
    private val categoryRepository: CategoryRepository,
) : ObserveCategoryUseCase {
    override fun invoke(categoryId: Long): Flow<Category?> = categoryRepository.observeCategory(categoryId)
}
