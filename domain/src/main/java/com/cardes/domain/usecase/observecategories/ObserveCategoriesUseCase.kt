package com.cardes.domain.usecase.observecategories

import com.cardes.domain.entity.Category
import com.cardes.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveCategoriesUseCase {
    operator fun invoke(): Flow<List<Category>>
}

class ObserveCategoriesUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : ObserveCategoriesUseCase {
    override fun invoke() = categoryRepository.observeCategories()
}
