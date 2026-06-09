package com.cardes.spendings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardes.domain.entity.Spending
import com.cardes.domain.usecase.addsamples.AddSamplesUseCase
import com.cardes.domain.usecase.deleteallspendings.DeleteAllSpendingsUseCase
import com.cardes.domain.usecase.observespendings.ObserveSpendingsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.SortedMap

class SpendingsListViewModel(
    observeSpendingsUseCase: ObserveSpendingsUseCase,
    private val addSamplesUseCase: AddSamplesUseCase,
    private val deleteAllSpendingsUseCase: DeleteAllSpendingsUseCase,
    private val defaultDispatcher: CoroutineDispatcher,
) : ViewModel() {
    fun addSamples() {
        viewModelScope.launch {
            addSamplesUseCase()
        }
    }

    fun deleteAllSpendings() {
        viewModelScope.launch {
            deleteAllSpendingsUseCase()
        }
    }

    val spendingsListUiState =
        observeSpendingsUseCase()
            .map { spendings ->
                SpendingsListUiState.Success(data = spendings.toGroupedSpendingUIStates())
            }.flowOn(defaultDispatcher)
            .stateIn(
                scope = viewModelScope,
                initialValue = SpendingsListUiState.Loading,
                started = SharingStarted.WhileSubscribed(5_000),
            )
}

internal fun List<Spending>.toGroupedSpendingUIStates(): SortedMap<DayYear, List<Spending>> =
    groupBy { spending ->
        Calendar.getInstance().run {
            timeInMillis = spending.time
            DayYear(
                dayOfYear = get(Calendar.DAY_OF_YEAR),
                year = get(Calendar.YEAR),
            )
        }
    }.toSortedMap(compareBy<DayYear> { it.year }.thenBy { it.dayOfYear }.reversed())

data class DayYear(
    val dayOfYear: Int,
    val year: Int,
)
