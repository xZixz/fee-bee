package com.cardes.domain.repository

import com.cardes.domain.entity.Spending
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface SpendingRepository {
    suspend fun getSpending(spendingId: Long): Result<Spending>

    fun observeSpendings(): Flow<List<Spending>>

    suspend fun removeSpending(spendingId: Long): Result<Unit>

    suspend fun createSamples()

    suspend fun deleteAll()

    suspend fun createSpending(
        time: Long,
        content: String,
        amount: BigDecimal,
        categoryIds: List<Long>,
    ): Result<Unit>
}
