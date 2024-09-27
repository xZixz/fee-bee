package com.cardes.feebee.ui.spendingdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardes.domain.usecase.observespending.ObserveSpendingUseCase
import com.cardes.feebee.navigation.SPENDING_ID_ARG
import com.cardes.feebee.ui.editspending.spendingDateDisplayFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SpendingDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observeSpendingUseCase: ObserveSpendingUseCase,
) : ViewModel() {
    private val spendingId: Long = checkNotNull(savedStateHandle[SPENDING_ID_ARG])
    val spendingUiState = observeSpendingUseCase.invoke(spendingId)
        .map { spending ->
            SpendingUiState.Success(
                description = spending.content,
                date = spendingDateDisplayFormat.format(spending.time),
                categories = spending.categories,
                cost = spending.amount.toString(),
            )
        }
        .stateIn(
            initialValue = SpendingUiState.Loading,
            started = SharingStarted.WhileSubscribed(500L),
            scope = viewModelScope,
        )
}
