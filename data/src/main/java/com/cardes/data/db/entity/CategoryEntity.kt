package com.cardes.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CATEGORY_TABLE_NAME = "categories"

@Entity(tableName = CATEGORY_TABLE_NAME)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val categoryId: Long = 0,
    val name: String,
    val emoji: String? = null,
)
