package com.cardes.spendingdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.cardes.designsystem.common.spendingDateDisplayFormat
import com.cardes.domain.usecase.observespending.ObserveSpendingUseCase
import com.cardes.spendingdetail.navigation.SpendingDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SpendingDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observeSpendingUseCase: ObserveSpendingUseCase,
) : ViewModel() {
    private val spendingId: Long = savedStateHandle.toRoute<SpendingDetailRoute>().spendingId
    val spendingUiState = observeSpendingUseCase
        .invoke(spendingId)
        .map { spending ->
            SpendingUiState.Success(
                spendingId = spending.id,
                description = spending.content,
                date = spendingDateDisplayFormat.format(spending.time),
                categories = spending.categories,
                cost = spending.amount.toString(),
            )
        }.stateIn(
            initialValue = SpendingUiState.Loading,
            started = SharingStarted.WhileSubscribed(500L),
            scope = viewModelScope,
        )
}
