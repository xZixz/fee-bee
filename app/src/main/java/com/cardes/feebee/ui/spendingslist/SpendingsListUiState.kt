package com.cardes.feebee.ui.spendingslist

import com.cardes.domain.base.MonthYear
import com.cardes.domain.entity.Spending
import java.util.SortedMap

sealed interface SpendingsListUiState {
    data object Loading : SpendingsListUiState

    data class Success(
        val data: SortedMap<MonthYear, List<Spending>>,
    ) : SpendingsListUiState
}
