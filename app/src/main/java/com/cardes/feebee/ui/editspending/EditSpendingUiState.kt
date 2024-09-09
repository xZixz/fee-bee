package com.cardes.feebee.ui.editspending

import com.cardes.domain.entity.Category

data class EditSpendingUiState(
    val description: String,
    val amount: String,
    val date: String,
    val categories: List<Category>,
    val selectedCategoryIds: List<Long>,
)
