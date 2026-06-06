package com.cardes.spendingdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardes.designsystem.common.spendingDateDisplayFormat
import com.cardes.domain.usecase.observespending.ObserveSpendingUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.annotation.InjectedParam

class SpendingDetailViewModel(
    @InjectedParam val spendingId: Long,
    observeSpendingUseCase: ObserveSpendingUseCase,
) : ViewModel() {
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
