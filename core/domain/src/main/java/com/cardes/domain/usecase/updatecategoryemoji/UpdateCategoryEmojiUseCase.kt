package com.cardes.domain.usecase.updatecategoryemoji

import com.cardes.domain.repository.CategoryRepository
import javax.inject.Inject

interface UpdateCategoryEmojiUseCase {
    suspend operator fun invoke(
        categoryId: Long,
        emoji: String,
    ): Result<Unit>
}

class UpdateCategoryEmojiUseCaseImpl
    @Inject constructor(
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
