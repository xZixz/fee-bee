package com.cardes.data.spending

import com.cardes.core.common.di.Dispatcher
import com.cardes.core.common.di.FeeBeeDispatcher
import com.cardes.domain.entity.Spending
import com.cardes.domain.repository.SpendingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import javax.inject.Inject

class SpendingRepositoryImpl @Inject constructor(
    private val spendingLocalDataSource: SpendingLocalDataSource,
    @Dispatcher(FeeBeeDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) : SpendingRepository {
    override suspend fun createSpending(
        time: Long,
        content: String,
        amount: BigDecimal,
        categoryIds: List<Long>,
    ): Result<Unit> =
        withContext(ioDispatcher) {
            resultWrap {
                spendingLocalDataSource.createSpending(
                    time,
                    content,
                    amount,
                    categoryIds,
                )
            }
        }

    override suspend fun updateSpending(
        id: Long,
        content: String,
        time: Long,
        amount: BigDecimal,
        categoryIds: List<Long>,
    ): Result<Unit> =
        withContext(ioDispatcher) {
            resultWrap {
                spendingLocalDataSource.updateSpending(
                    id = id,
                    content = content,
                    time = time,
                    amount = amount,
                    categoryIds = categoryIds,
                )
            }
        }

    override suspend fun getSpending(spendingId: Long): Result<Spending> =
        withContext(ioDispatcher) {
            resultWrap {
                spendingLocalDataSource.getSpending(spendingId = spendingId)
            }
        }

    override fun observeSpendings(): Flow<List<Spending>> = spendingLocalDataSource.getSpendings()

    override suspend fun getSpendingsByCategoryIds(categoryIds: List<Long>): Result<List<Spending>> =
        withContext(ioDispatcher) {
            resultWrap {
                spendingLocalDataSource.getSpendingsByCategoryIds(categoryIds)
            }
        }

    override suspend fun getAllSpendings(): Result<List<Spending>> =
        withContext(ioDispatcher) {
            resultWrap {
                spendingLocalDataSource.getAllSpendings()
            }
        }

    override suspend fun getSpendingsByDateRage(
        from: Long,
        to: Long,
    ): Result<List<Spending>> =
        withContext(ioDispatcher) {
            resultWrap {
                spendingLocalDataSource.getSpendingsByDateRange(
                    from = from,
                    to = to,
                )
            }
        }

    override suspend fun getSpendingsByCategoriesByDateRange(
        categoryIds: List<Long>,
        from: Long,
        to: Long,
    ): Result<List<Spending>> =
        withContext(ioDispatcher) {
            resultWrap {
                spendingLocalDataSource.getSpendingsByCategoriesByDateRange(
                    categoryIds = categoryIds,
                    from = from,
                    to = to,
                )
            }
        }

    override fun observeSpending(spendingId: Long): Flow<Spending> = spendingLocalDataSource.observeSpending(spendingId)

    override suspend fun removeSpending(spendingId: Long): Result<Unit> =
        withContext(ioDispatcher) {
            resultWrap {
                spendingLocalDataSource.removeSpending(spendingId)
            }
        }

    override suspend fun createSamples() {
        withContext(ioDispatcher) {
            spendingLocalDataSource.createSamples()
        }
    }

    override suspend fun deleteAll() {
        withContext(ioDispatcher) {
            spendingLocalDataSource.deleteAll()
        }
    }
}

suspend fun <T> resultWrap(block: suspend () -> T): Result<T> =
    try {
        val result = block()
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }
