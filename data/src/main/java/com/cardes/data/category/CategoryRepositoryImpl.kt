package com.cardes.data.category

import com.cardes.core.common.di.Dispatcher
import com.cardes.core.common.di.FeeBeeDispatcher
import com.cardes.data.spending.resultWrap
import com.cardes.domain.entity.Category
import com.cardes.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryLocalDataSource: CategoryLocalDataSource,
    @Dispatcher(FeeBeeDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) : CategoryRepository {
    override fun observeCategories(): Flow<List<Category>> = categoryLocalDataSource.observeCategories()

    override suspend fun createCategory(name: String): Result<Unit> =
        withContext(ioDispatcher) {
            resultWrap {
                categoryLocalDataSource.createCategory(name)
            }
        }
}
