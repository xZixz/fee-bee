package com.cardes.domain.usecase.getallspendings

import com.cardes.domain.entity.Spending

fun interface GetAllSpendingsUseCase {
    suspend operator fun invoke(): Result<List<Spending>>
}
