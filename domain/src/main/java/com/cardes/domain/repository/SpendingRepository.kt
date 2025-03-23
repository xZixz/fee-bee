package com.cardes.domain.repository

import com.cardes.domain.base.MonthYear
import com.cardes.domain.entity.Spending
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.util.SortedMap

interface SpendingRepository {
    suspend fun getSpending(spendingId: Long): Result<Spending>

    fun observeSpendings(): Flow<List<Spending>>

    fun observeGroupedByMonthsSpending(): Flow<SortedMap<MonthYear, List<Spending>>>

    suspend fun removeSpending(spendingId: Long): Result<Unit>

    suspend fun createSamples()

    suspend fun deleteAll()

    suspend fun createSpending(
        time: Long,
        content: String,
        amount: BigDecimal,
        categoryIds: List<Long>,
    ): Result<Unit>

    suspend fun updateSpending(
        id: Long,
        content: String,
        time: Long,
        amount: BigDecimal,
        categoryIds: List<Long>,
    ): Result<Unit>

    fun observeSpending(spendingId: Long): Flow<Spending>

    suspend fun getSpendingsByCategoryIds(categoryIds: List<Long>): Result<List<Spending>>

    suspend fun getAllSpendings(): Result<List<Spending>>

    suspend fun getSpendingsByDateRage(
        from: Long,
        to: Long,
    ): Result<List<Spending>>

    suspend fun getSpendingsByCategoriesByDateRange(
        categoryIds: List<Long>,
        from: Long,
        to: Long,
    ): Result<List<Spending>>

    suspend fun getTotalSpentByCategoriesInMonth(
        month: Int,
        year: Int,
    ): Result<Map<String, BigDecimal>>
}
