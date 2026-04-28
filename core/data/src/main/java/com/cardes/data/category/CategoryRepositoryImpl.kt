package com.cardes.data.category

import com.cardes.data.spending.resultWrap
import com.cardes.domain.entity.Category
import com.cardes.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CategoryRepositoryImpl(
    private val categoryLocalDataSource: CategoryLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : CategoryRepository {
    override fun observeCategories(): Flow<List<Category>> = categoryLocalDataSource.observeCategories()

    override suspend fun createCategory(name: String): Result<Unit> =
        withContext(ioDispatcher) {
            resultWrap {
                categoryLocalDataSource.createCategory(name)
            }
        }

    override fun observeCategory(categoryId: Long): Flow<Category?> = categoryLocalDataSource.observeCategory(categoryId = categoryId)

    override suspend fun updateCategoryName(
        categoryId: Long,
        categoryName: String,
    ): Result<Unit> =
        withContext(ioDispatcher) {
            resultWrap {
                categoryLocalDataSource.updateCategoryName(
                    categoryId = categoryId,
                    categoryName = categoryName,
                )
            }
        }

    override suspend fun removeCategory(categoryId: Long): Result<Unit> =
        withContext(ioDispatcher) {
            resultWrap {
                categoryLocalDataSource.removeCategory(categoryId = categoryId)
            }
        }

    override suspend fun updateCategoryEmoji(
        categoryId: Long,
        emoji: String,
    ): Result<Unit> =
        withContext(ioDispatcher) {
            resultWrap {
                categoryLocalDataSource.updateCategoryEmoji(
                    categoryId = categoryId,
                    emoji = emoji,
                )
            }
        }

    override suspend fun removeCategoryEmoji(categoryId: Long): Result<Unit> =
        withContext(ioDispatcher) {
            resultWrap {
                categoryLocalDataSource.removeCategoryEmoji(categoryId = categoryId)
            }
        }
}
