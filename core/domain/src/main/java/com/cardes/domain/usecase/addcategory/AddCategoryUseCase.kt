package com.cardes.domain.usecase.addcategory

fun interface AddCategoryUseCase {
    suspend operator fun invoke(name: String): Result<Unit>
}
