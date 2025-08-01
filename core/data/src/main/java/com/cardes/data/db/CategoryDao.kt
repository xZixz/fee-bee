package com.cardes.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cardes.data.db.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert
    suspend fun createCategory(categoryEntity: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createCategories(categoryEntities: List<CategoryEntity>)

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Query("SELECT * FROM categories")
    fun observeCategories(): Flow<List<CategoryEntity>>

    @Query("SElECT * FROM categories WHERE categoryId = :categoryId")
    fun observeCategory(categoryId: Long): Flow<CategoryEntity?>

    @Query("UPDATE categories SET name=:categoryName WHERE categoryId = :categoryId")
    suspend fun updateCategoryName(
        categoryId: Long,
        categoryName: String,
    )

    @Query("UPDATE categories SET emoji=:emoji WHERE categoryId = :categoryId")
    suspend fun updateCategoryEmoji(
        categoryId: Long,
        emoji: String,
    )

    @Query("UPDATE categories SET emoji='' WHERE categoryId = :categoryId")
    suspend fun removeCategoryEmoji(categoryId: Long)

    @Query("DELETE FROM categories WHERE categoryId=:categoryId")
    suspend fun removeCategory(categoryId: Long)
}
