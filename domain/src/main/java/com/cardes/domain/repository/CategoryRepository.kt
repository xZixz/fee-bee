package com.cardes.domain.repository

import com.cardes.domain.entity.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun observeCategories(): Flow<List<Category>>

    suspend fun createCategory(name: String): Result<Unit>

    fun observeCategory(categoryId: Long): Flow<Category>

    suspend fun updateCategoryName(
        categoryId: Long,
        categoryName: String,
    ): Result<Unit>
}
