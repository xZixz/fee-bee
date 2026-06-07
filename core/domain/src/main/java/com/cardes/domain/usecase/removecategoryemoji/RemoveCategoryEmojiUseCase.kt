package com.cardes.domain.usecase.removecategoryemoji

import com.cardes.domain.repository.CategoryRepository

interface RemoveCategoryEmojiUseCase {
    suspend operator fun invoke(categoryId: Long): Result<Unit>
}

class RemoveCategoryEmojiUseCaseImpl(
    private val categoryRepository: CategoryRepository,
) : RemoveCategoryEmojiUseCase {
    override suspend fun invoke(categoryId: Long): Result<Unit> = categoryRepository.removeCategoryEmoji(categoryId = categoryId)
}
