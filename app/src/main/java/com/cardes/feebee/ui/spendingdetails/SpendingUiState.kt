package com.cardes.feebee.ui.spendingdetails

sealed interface SpendingUiState {
    data object Loading : SpendingUiState

    data class Success(
        val description: String,
        val date: String,
        val cost: String,
    ) : SpendingUiState
}
