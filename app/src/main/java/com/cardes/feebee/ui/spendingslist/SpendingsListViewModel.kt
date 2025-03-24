package com.cardes.feebee.ui.spendingslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardes.core.common.di.Dispatcher
import com.cardes.core.common.di.FeeBeeDispatcher
import com.cardes.domain.entity.Spending
import com.cardes.domain.usecase.addsamples.AddSamplesUseCase
import com.cardes.domain.usecase.deleteallspendings.DeleteAllSpendingsUseCase
import com.cardes.domain.usecase.observespendings.ObserveSpendingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.SortedMap
import javax.inject.Inject

@HiltViewModel
class SpendingsListViewModel @Inject constructor(
    observeSpendingsUseCase: ObserveSpendingsUseCase,
    private val addSamplesUseCase: AddSamplesUseCase,
    private val deleteAllSpendingsUseCase: DeleteAllSpendingsUseCase,
    @Dispatcher(FeeBeeDispatcher.Default) private val defaultDispatcher: CoroutineDispatcher,
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
