package com.cardes.data.category

import com.cardes.data.db.CategoryDao
import com.cardes.data.db.entity.CategoryEntity
import com.cardes.domain.entity.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface CategoryLocalDataSource {
    fun observeCategories(): Flow<List<Category>>

    suspend fun createCategory(name: String)
}

class CategoryLocalDataSourceImpl @Inject constructor(
    private val categoryDao: CategoryDao,
) : CategoryLocalDataSource {
    override fun observeCategories(): Flow<List<Category>> =
        categoryDao.observeCategories()
            .map { categories -> categories.map { category -> category.toCategory() } }

    override suspend fun createCategory(name: String) {
        categoryDao.createCategory(CategoryEntity(id = 0, name = name))
    }
}

private fun CategoryEntity.toCategory() =
    Category(
        id = id,
        name = name,
    )
