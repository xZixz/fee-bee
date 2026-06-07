package com.cardes.domain.usecase.updatecategoryemoji

fun interface UpdateCategoryEmojiUseCase {
    suspend operator fun invoke(
        categoryId: Long,
        emoji: String,
    ): Result<Unit>
}
