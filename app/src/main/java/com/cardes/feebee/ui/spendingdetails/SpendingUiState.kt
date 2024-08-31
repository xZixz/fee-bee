package com.cardes.feebee.ui.spendingdetails

import com.cardes.domain.entity.Category

sealed interface SpendingUiState {
    data object Loading : SpendingUiState

    data class Success(
        val description: String,
        val date: String,
        val cost: String,
        val categories: List<Category>,
        val viewMode: ViewMode,
    ) : SpendingUiState

    enum class ViewMode {
        VIEW_ONLY,
        EDIT,
    }
}
