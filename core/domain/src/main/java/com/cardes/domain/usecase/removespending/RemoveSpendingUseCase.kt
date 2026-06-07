package com.cardes.domain.usecase.removespending

fun interface RemoveSpendingUseCase {
    suspend operator fun invoke(spendingId: Long): Result<Unit>
}
