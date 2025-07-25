package com.cardes.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.TypeConverters
import androidx.room.Update
import com.cardes.data.db.entity.SpendingCategoryCrossRef
import com.cardes.data.db.entity.SpendingEntity
import com.cardes.data.db.entity.SpendingWithCategories
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

@Dao
@TypeConverters(BigDecimalTypeConverter::class)
abstract class SpendingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addSpending(spending: SpendingEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addSpendings(spendings: List<SpendingEntity>)

    @Transaction
    open suspend fun addSpendingWithCategories(
        spending: SpendingEntity,
        categoryIds: List<Long>,
    ) {
        val spendingId = addSpending(spending)
        addSpendingCategoriesCrossRef(
            categoryIds.map { categoryId -> SpendingCategoryCrossRef(spendingId, categoryId) },
        )
    }

    @Transaction
    open suspend fun addSpendingsWithCategories(spendings: List<Pair<SpendingEntity, List<Long>>>) {
        spendings.forEach { (spending, categoryIds) ->
            addSpendingWithCategories(spending, categoryIds)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addSpendingCategoriesCrossRef(crossRef: List<SpendingCategoryCrossRef>)

    @Query(value = "DELETE FROM SpendingCategoryCrossRef where spendingId=:spendingId")
    abstract suspend fun removeSpendingCategoryCrossRef(spendingId: Long)

    @Transaction
    @Query(value = "SELECT * FROM spendings")
    abstract fun observeAllSpendings(): Flow<List<SpendingWithCategories>>

    @Transaction
    @Query(value = "SELECT * FROM spendings where id=:spendingId")
    abstract suspend fun getSpending(spendingId: Long): SpendingWithCategories

    @Query(value = "DELETE FROM spendings where id=:spendingId")
    abstract suspend fun deleteSpending(spendingId: Long)

    @Query(value = "DELETE FROM spendings")
    abstract suspend fun deleteAll()

    @Update
    abstract suspend fun updateSpending(spending: SpendingEntity)

    @Transaction
    open suspend fun updateSpending(
        spending: SpendingEntity,
        categoryIds: List<Long>,
    ) {
        updateSpending(spending)
        removeSpendingCategoryCrossRef(spending.id)
        addSpendingCategoriesCrossRef(
            categoryIds.map { categoryId -> SpendingCategoryCrossRef(spending.id, categoryId) },
        )
    }

    @Transaction
    @Query(value = "SELECT * FROM spendings where id=:spendingId")
    abstract fun observeSpending(spendingId: Long): Flow<SpendingWithCategories>

    @Transaction
    @Query(
        value = "SELECT * FROM spendings " +
            "INNER JOIN SpendingCategoryCrossRef ON spendings.id = SpendingCategoryCrossRef.spendingId " +
            "INNER JOIN  categories ON SpendingCategoryCrossRef.categoryId = categories.categoryId " +
            "WHERE categories.categoryId IN (:categoryIds)",
    )
    abstract suspend fun getSpendingsByCategoryIds(categoryIds: List<Long>): List<SpendingWithCategories>

    @Transaction
    @Query(
        value = "SELECT * FROM spendings " +
            "INNER JOIN SpendingCategoryCrossRef ON spendings.id = SpendingCategoryCrossRef.spendingId " +
            "INNER JOIN categories ON SpendingCategoryCrossRef.categoryId = categories.categoryId " +
            "WHERE categories.categoryId IN (:categoryIds) AND spendings.time BETWEEN :from AND :to",
    )
    abstract suspend fun getSpendingsByCategoryIdsByDateRange(
        categoryIds: List<Long>,
        from: Long,
        to: Long,
    ): List<SpendingWithCategories>

    @Transaction
    @Query(value = "SELECT * FROM spendings")
    abstract suspend fun getAllSpendings(): List<SpendingWithCategories>

    @Query(
        value = "SELECT categories.name, sum(spendings.amount) AS total FROM spendings " +
            "INNER JOIN SpendingCategoryCrossRef ON spendings.id = SpendingCategoryCrossRef.spendingId " +
            "INNER JOIN categories ON SpendingCategoryCrossRef.categoryId = categories.categoryId " +
            "WHERE spendings.time BETWEEN :from AND :to " +
            "GROUP BY categories.name ",
    )
    abstract suspend fun getTotalSpentOfCategoriesByDateRange(
        from: Long,
        to: Long,
    ): Map<
        @MapColumn(columnName = "name", tableName = "categories") String,
        @MapColumn(columnName = "total") BigDecimal,
    >

    @Transaction
    @Query(value = "SELECT * FROM spendings WHERE time BETWEEN :from AND :to")
    abstract suspend fun getSpendingsByTimeRange(
        from: Long,
        to: Long,
    ): List<SpendingWithCategories>
}
