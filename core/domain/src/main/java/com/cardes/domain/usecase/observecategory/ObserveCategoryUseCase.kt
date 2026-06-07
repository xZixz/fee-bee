package com.cardes.domain.usecase.observecategory

import com.cardes.domain.entity.Category
import kotlinx.coroutines.flow.Flow

fun interface ObserveCategoryUseCase {
    operator fun invoke(categoryId: Long): Flow<Category?>
}
