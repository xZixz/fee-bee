package com.cardes.feebee.ui.spendingslist

import com.cardes.domain.entity.Spending
import java.util.SortedMap

sealed interface SpendingsListUiState {
    data object Loading : SpendingsListUiState

    data class Success(
        val data: SortedMap<DayYear, List<Spending>>,
    ) : SpendingsListUiState
}
