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

    override fun getSpendings(): Flow<List<Spending>> =
        spendingDao.getAllSpendings()
            .map { spendingEntities -> spendingEntities.map { spendingEntity -> spendingEntity.toSpending() } }

    override fun observeSpending(spendingId: Long): Flow<Spending> =
        spendingDao.observeSpending(spendingId)
            .filterNotNull()
            .map { spendingEntity -> spendingEntity.toSpending() }

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

private fun Category.toCategoryEntity(id: Long = this.id) =
    CategoryEntity(
        id = id,
        name = name,
    )

private fun CategoryEntity.toCategory() =
    Category(
        id = id,
        name = name,
    )

private fun Spending.toSpendingEntity(id: Long = this.id) =
    SpendingEntity(
        id = id,
        amount = amount,
        content = content,
        time = time,
    )
