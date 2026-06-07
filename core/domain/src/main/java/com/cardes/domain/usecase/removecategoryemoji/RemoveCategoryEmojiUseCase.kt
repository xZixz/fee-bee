package com.cardes.domain.usecase.removecategoryemoji

fun interface RemoveCategoryEmojiUseCase {
    suspend operator fun invoke(categoryId: Long): Result<Unit>
}
