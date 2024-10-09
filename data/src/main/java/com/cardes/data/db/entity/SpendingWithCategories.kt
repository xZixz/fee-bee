package com.cardes.data.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class SpendingWithCategories(
    @Embedded val spending: SpendingEntity,
    @Relation(
        parentColumn = "id",
        entity = CategoryEntity::class,
        entityColumn = "categoryId",
        associateBy = Junction(
            SpendingCategoryCrossRef::class,
            parentColumn = "spendingId",
            entityColumn = "categoryId",
        ),
    )
    val categories: List<CategoryEntity>,
)
