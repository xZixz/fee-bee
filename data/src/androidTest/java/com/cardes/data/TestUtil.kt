package com.cardes.data

import com.cardes.data.db.entity.CategoryEntity

object TestUtil {
    fun createCategories(number: Int): List<CategoryEntity> {
        return (1..number).map { index ->
            CategoryEntity(
                id = index.toLong(),
                name = "Category #$index",
            )
        }
    }
}
