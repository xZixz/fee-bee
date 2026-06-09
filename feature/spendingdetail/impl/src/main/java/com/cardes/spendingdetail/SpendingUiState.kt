package com.cardes.spendingdetail

import com.cardes.domain.entity.Category

sealed interface SpendingUiState {
    data object Loading : SpendingUiState

    data class Success(
        val spendingId: Long,
        val description: String,
        val date: String,
        val cost: String,
        val categories: List<Category>,
    ) : SpendingUiState
}
