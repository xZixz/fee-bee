package com.cardes.feebee.ui.spendingslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardes.domain.usecase.addsamples.AddSamplesUseCase
import com.cardes.domain.usecase.deleteallspendings.DeleteAllSpendingsUseCase
import com.cardes.domain.usecase.observegroupedbymonthspendings.ObserveGroupedByMonthsSpendingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpendingsListViewModel @Inject constructor(
    observeGroupedByMonthsSpendingsUseCase: ObserveGroupedByMonthsSpendingsUseCase,
    private val addSamplesUseCase: AddSamplesUseCase,
    private val deleteAllSpendingsUseCase: DeleteAllSpendingsUseCase,
) : ViewModel() {
    fun addSamples() {
        viewModelScope.launch {
            addSamplesUseCase.invoke()
        }
    }

    fun deleteAllSpendings() {
        viewModelScope.launch {
            deleteAllSpendingsUseCase.invoke()
        }
    }

    val spendingsListUiState =
        observeGroupedByMonthsSpendingsUseCase()
            .map { groupedSpendings ->
                SpendingsListUiState.Success(data = groupedSpendings)
            }.stateIn(
                scope = viewModelScope,
                initialValue = SpendingsListUiState.Loading,
                started = SharingStarted.WhileSubscribed(5_000),
            )
}
