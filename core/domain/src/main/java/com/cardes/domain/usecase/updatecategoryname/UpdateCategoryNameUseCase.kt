package com.cardes.domain.usecase.updatecategoryname

fun interface UpdateCategoryNameUseCase {
    suspend operator fun invoke(
        categoryId: Long,
        categoryName: String,
    ): Result<Unit>
}
