package com.cardes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cardes.data.db.entity.CategoryEntity
import com.cardes.data.db.entity.SpendingCategoryCrossRef
import com.cardes.data.db.entity.SpendingEntity

const val FEE_BEE_DATABASE_NAME = "fee_bee_database"
const val VERSION = 1

@Database(
    entities = [
        SpendingEntity::class,
        CategoryEntity::class,
        SpendingCategoryCrossRef::class,
    ],
    exportSchema = true,
    version = VERSION,
)
abstract class FeeBeeDatabase : RoomDatabase() {
    abstract fun spendingDao(): SpendingDao

    abstract fun categoryDao(): CategoryDao
}
