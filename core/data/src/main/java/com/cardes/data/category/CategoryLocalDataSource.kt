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

    fun observeCategory(categoryId: Long): Flow<Category?>

    suspend fun updateCategoryName(
        categoryId: Long,
        categoryName: String,
    )

    suspend fun removeCategory(categoryId: Long)

    suspend fun updateCategoryEmoji(
        categoryId: Long,
        emoji: String,
    )

    suspend fun removeCategoryEmoji(categoryId: Long)
}

class CategoryLocalDataSourceImpl @Inject constructor(
    private val categoryDao: CategoryDao,
) : CategoryLocalDataSource {
    override fun observeCategories(): Flow<List<Category>> =
        categoryDao
            .observeCategories()
            .map { categories -> categories.map { category -> category.toCategory() } }

    override suspend fun createCategory(name: String) {
        categoryDao.createCategory(CategoryEntity(categoryId = 0, name = name))
    }

    override fun observeCategory(categoryId: Long): Flow<Category?> =
        categoryDao
            .observeCategory(categoryId = categoryId)
            .map { categoryEntity ->
                categoryEntity ?: return@map null

                categoryEntity.toCategory()
            }

    override suspend fun updateCategoryName(
        categoryId: Long,
        categoryName: String,
    ) {
        categoryDao.updateCategoryName(
            categoryId = categoryId,
            categoryName = categoryName,
        )
    }

    override suspend fun removeCategory(categoryId: Long) {
        categoryDao.removeCategory(categoryId = categoryId)
    }

    override suspend fun updateCategoryEmoji(
        categoryId: Long,
        emoji: String,
    ) {
        categoryDao.updateCategoryEmoji(
            categoryId = categoryId,
            emoji = emoji,
        )
    }

    override suspend fun removeCategoryEmoji(categoryId: Long) {
        categoryDao.removeCategoryEmoji(categoryId = categoryId)
    }
}

private fun CategoryEntity.toCategory() =
    Category(
        id = categoryId,
        name = name,
        emoji = emoji.orEmpty(),
    )
