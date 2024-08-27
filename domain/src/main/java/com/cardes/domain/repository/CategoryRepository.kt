package com.cardes.domain.repository

import com.cardes.domain.entity.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun observeCategories(): Flow<List<Category>>
}
