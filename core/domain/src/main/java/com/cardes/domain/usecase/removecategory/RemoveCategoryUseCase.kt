package com.cardes.domain.usecase.removecategory

fun interface RemoveCategoryUseCase {
    suspend operator fun invoke(categoryId: Long): Result<Unit>
}
