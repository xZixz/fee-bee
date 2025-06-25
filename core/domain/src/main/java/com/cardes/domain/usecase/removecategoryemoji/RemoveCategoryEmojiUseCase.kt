package com.cardes.domain.usecase.removecategoryemoji

import com.cardes.domain.repository.CategoryRepository
import javax.inject.Inject

interface RemoveCategoryEmojiUseCase {
    suspend operator fun invoke(categoryId: Long): Result<Unit>
}

class RemoveCategoryEmojiUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : RemoveCategoryEmojiUseCase {
    override suspend fun invoke(categoryId: Long): Result<Unit> = categoryRepository.removeCategoryEmoji(categoryId = categoryId)
}
