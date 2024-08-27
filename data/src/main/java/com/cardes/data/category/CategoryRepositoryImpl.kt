package com.cardes.data.category

import com.cardes.domain.entity.Category
import com.cardes.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryLocalDataSource: CategoryLocalDataSource,
) : CategoryRepository {
    override fun observeCategories(): Flow<List<Category>> = categoryLocalDataSource.observeCategories()
}
