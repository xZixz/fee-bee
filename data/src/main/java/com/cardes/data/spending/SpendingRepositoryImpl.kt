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

    override suspend fun getSpending(spendingId: Long): Result<Spending> =
        withContext(ioDispatcher) {
            resultWrap {
                spendingLocalDataSource.getSpending(spendingId = spendingId)
            }
        }

    override fun observeSpendings(): Flow<List<Spending>> = spendingLocalDataSource.getSpendings()

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

suspend fun <T> resultWrap(block: suspend () -> T): Result<T> {
    return try {
        val result = block()
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}
