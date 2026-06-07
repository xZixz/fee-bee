package com.cardes.domain.usecase.observecategories

import com.cardes.domain.entity.Category
import kotlinx.coroutines.flow.Flow

fun interface ObserveCategoriesUseCase {
    operator fun invoke(): Flow<List<Category>>
}
