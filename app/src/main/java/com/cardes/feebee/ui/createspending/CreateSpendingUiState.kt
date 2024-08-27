package com.cardes.feebee.ui.createspending

import com.cardes.domain.entity.Category

data class CreateSpendingUiState(
    val description: String,
    val amount: String,
    val date: String,
    val categories: List<Category>,
    val selectedCategoryIds: List<Long>,
)
