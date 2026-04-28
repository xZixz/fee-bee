package com.cardes.domain.usecase.updatecategoryemoji

import com.cardes.domain.repository.CategoryRepository

interface UpdateCategoryEmojiUseCase {
    suspend operator fun invoke(
        categoryId: Long,
        emoji: String,
    ): Result<Unit>
}

class UpdateCategoryEmojiUseCaseImpl
    constructor(
        private val categoryRepository: CategoryRepository,
    ) : UpdateCategoryEmojiUseCase {
        override suspend fun invoke(
            categoryId: Long,
            emoji: String,
        ): Result<Unit> =
            categoryRepository.updateCategoryEmoji(
                categoryId = categoryId,
                emoji = emoji,
            )
    }
