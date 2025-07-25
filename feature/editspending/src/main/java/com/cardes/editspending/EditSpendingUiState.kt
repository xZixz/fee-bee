package com.cardes.editspending

import com.cardes.domain.entity.Category

data class EditSpendingUiState(
    val description: String,
    val amount: String,
    val date: Long,
    val categories: List<Category>,
    val selectedCategoryIds: List<Long>,
)
