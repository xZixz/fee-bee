package com.cardes.data.spending

import com.cardes.data.db.CategoryDao
import com.cardes.data.db.SpendingDao
import com.cardes.data.db.entity.CategoryEntity
import com.cardes.data.db.entity.SpendingEntity
import com.cardes.data.db.entity.SpendingWithCategories
import com.cardes.data.fake.Fake
import com.cardes.domain.entity.Category
import com.cardes.domain.entity.Spending
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import java.util.Calendar
import javax.inject.Inject

interface SpendingLocalDataSource {
    fun getSpendings(): Flow<List<Spending>>

    suspend fun getSpending(spendingId: Long): Spending

    suspend fun removeSpending(spendingId: Long)

    suspend fun createSamples()

    suspend fun deleteAll()

    suspend fun createSpending(
        time: Long,
        content: String,
        amount: BigDecimal,
        categoryIds: List<Long>,
    )

    suspend fun updateSpending(
        id: Long,
        content: String,
        time: Long,
        amount: BigDecimal,
        categoryIds: List<Long>,
    )

    fun observeSpending(spendingId: Long): Flow<Spending>

    suspend fun getSpendingsByCategoryIds(categoryIds: List<Long>): List<Spending>

    suspend fun getAllSpendings(): List<Spending>

    suspend fun getSpendingsByDateRange(
        from: Long,
        to: Long,
    ): List<Spending>

    suspend fun getSpendingsByCategoriesByDateRange(
        categoryIds: List<Long>,
        from: Long,
        to: Long,
    ): List<Spending>
}

class SpendingLocalDataSourceImpl @Inject constructor(
    private val spendingDao: SpendingDao,
    private val categoryDao: CategoryDao,
) : SpendingLocalDataSource {
    override suspend fun createSpending(
        time: Long,
        content: String,
        amount: BigDecimal,
        categoryIds: List<Long>,
    ) {
        spendingDao.addSpendingWithCategories(
            SpendingEntity(
                id = 0,
                amount = amount,
                content = content,
                time = time,
            ),
            categoryIds,
        )
    }

    override suspend fun updateSpending(
        id: Long,
        content: String,
        time: Long,
        amount: BigDecimal,
        categoryIds: List<Long>,
    ) {
        spendingDao.updateSpending(
            spending = SpendingEntity(
                id = id,
                content = content,
                time = time,
                amount = amount,
            ),
            categoryIds = categoryIds,
        )
    }

    override suspend fun getSpendingsByCategoriesByDateRange(
        categoryIds: List<Long>,
        from: Long,
        to: Long,
    ): List<Spending> =
        spendingDao
            .getSpendingsByCategoryIdsByDateRange(
                categoryIds = categoryIds,
                from = from,
                to = to,
            ).map(SpendingWithCategories::toSpending)

    override fun getSpendings(): Flow<List<Spending>> =
        spendingDao
            .observeAllSpendings()
            .map { spendingEntities -> spendingEntities.map { spendingEntity -> spendingEntity.toSpending() } }

    override fun observeSpending(spendingId: Long): Flow<Spending> =
        spendingDao
            .observeSpending(spendingId)
            .filterNotNull()
            .map { spendingEntity -> spendingEntity.toSpending() }

    override suspend fun getSpendingsByCategoryIds(categoryIds: List<Long>): List<Spending> =
        spendingDao
            .getSpendingsByCategoryIds(categoryIds)
            .map { spendingEntity -> spendingEntity.toSpending() }

    override suspend fun getAllSpendings(): List<Spending> =
        spendingDao
            .getAllSpendings()
            .map { spendingWithCategories -> spendingWithCategories.toSpending() }

    override suspend fun getSpendingsByDateRange(
        from: Long,
        to: Long,
    ): List<Spending> {
        val startOfTheFromDate = Calendar
            .getInstance()
            .apply {
                timeInMillis = from
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
        val endOfTheToDate = Calendar
            .getInstance()
            .apply {
                timeInMillis = to
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
                set(Calendar.MILLISECOND, 999)
            }.timeInMillis
        return spendingDao
            .getSpendingsByTimeRange(
                from = startOfTheFromDate,
                to = endOfTheToDate,
            ).map { spendingEntity -> spendingEntity.toSpending() }
    }

    override suspend fun getSpending(spendingId: Long): Spending = spendingDao.getSpending(spendingId).toSpending()

    override suspend fun removeSpending(spendingId: Long) = spendingDao.deleteSpending(spendingId)

    override suspend fun createSamples() {
        categoryDao.createCategories(Fake.categories.map { it.toCategoryEntity() })
        spendingDao.addSpendingsWithCategories(
            Fake.spendings.map { spending ->
                Pair(spending.toSpendingEntity(id = 0), spending.categories.map { it.id })
            },
        )
    }

    override suspend fun deleteAll() {
        spendingDao.deleteAll()
    }
}

// Mapper functions
private fun SpendingWithCategories.toSpending() =
    Spending(
        id = spending.id,
        content = spending.content,
        amount = spending.amount,
        time = spending.time,
        categories = categories.map { it.toCategory() },
    )

private fun SpendingEntity.toSpending() =
    Spending(
        id = id,
        content = content,
        amount = amount,
        time = time,
        categories = listOf(),
    )

private fun Category.toCategoryEntity(id: Long = this.id) =
    CategoryEntity(
        categoryId = id,
        name = name,
    )

private fun CategoryEntity.toCategory() =
    Category(
        id = categoryId,
        name = name,
    )

private fun Spending.toSpendingEntity(id: Long = this.id) =
    SpendingEntity(
        id = id,
        amount = amount,
        content = content,
        time = time,
    )
