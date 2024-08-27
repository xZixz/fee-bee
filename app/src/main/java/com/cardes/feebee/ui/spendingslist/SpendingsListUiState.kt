package com.cardes.feebee.ui.spendingslist

import com.cardes.domain.entity.Spending

sealed interface SpendingsListUiState {
    data object Loading : SpendingsListUiState

    data class Success(
        val spendings: List<Spending>,
    ) : SpendingsListUiState
}

