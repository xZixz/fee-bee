package com.cardes.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    primaryKeys = ["spendingId", "categoryId"],
    indices = [Index("spendingId"), Index("categoryId")],
    foreignKeys = [
        ForeignKey(
            entity = SpendingEntity::class,
            parentColumns = ["id"],
            childColumns = ["spendingId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class SpendingCategoryCrossRef(
    val spendingId: Long,
    val categoryId: Long,
)
