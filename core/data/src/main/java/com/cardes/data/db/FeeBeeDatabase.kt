package com.cardes.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.cardes.data.db.entity.CATEGORY_TABLE_NAME
import com.cardes.data.db.entity.CategoryEntity
import com.cardes.data.db.entity.SpendingCategoryCrossRef
import com.cardes.data.db.entity.SpendingEntity

const val FEE_BEE_DATABASE_NAME = "fee_bee_database"
const val VERSION = 3

val MIGRATION_2_3 = Migration(2, 3) { database ->
    database.execSQL("ALTER TABLE $CATEGORY_TABLE_NAME RENAME COLUMN emoticon TO emoji")
}

@Database(
    entities = [
        SpendingEntity::class,
        CategoryEntity::class,
        SpendingCategoryCrossRef::class,
    ],
    exportSchema = true,
    autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2,
        ),
    ],
    version = VERSION,
)
abstract class FeeBeeDatabase : RoomDatabase() {
    abstract fun spendingDao(): SpendingDao

    abstract fun categoryDao(): CategoryDao
}
