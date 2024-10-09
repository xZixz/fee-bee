package com.cardes.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.cardes.data.db.entity.SpendingCategoryCrossRef
import com.cardes.data.db.entity.SpendingEntity
import com.cardes.data.db.entity.SpendingWithCategories
import kotlinx.coroutines.flow.Flow

@Dao
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
    abstract fun removeSpendingCategoryCrossRef(spendingId: Long)

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

    @Query(
        value = "SELECT * FROM spendings " +
            "inner join SpendingCategoryCrossRef on spendings.id = SpendingCategoryCrossRef.spendingId " +
            "inner join  categories on SpendingCategoryCrossRef.categoryId = categories.categoryId " +
            "where categories.categoryId IN (:categoryIds)",
    )
    abstract fun getSpendingsByCategoryIds(categoryIds: List<Long>): List<SpendingWithCategories>

    @Query(value = "SELECT * FROM spendings")
    abstract fun getAllSpendings(): List<SpendingWithCategories>
}
