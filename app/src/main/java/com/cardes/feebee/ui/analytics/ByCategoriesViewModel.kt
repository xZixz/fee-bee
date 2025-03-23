package com.cardes.feebee.ui.analytics

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardes.domain.base.MonthYear
import com.cardes.domain.usecase.gettotalspentbycategoriesinmonth.GetTotalSpentByCategoriesInMonthUseCase
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ByCategoriesViewModel @Inject constructor(
    private val getTotalSpentByCategoriesInMonthUseCase: GetTotalSpentByCategoriesInMonthUseCase,
) : ViewModel() {
    private val _selectedMonth = MutableStateFlow(
        MonthYear(
            month = Calendar.getInstance().get(Calendar.MONTH) + 1,
            year = Calendar.getInstance().get(Calendar.YEAR),
        ),
    )
    val selectedMonth = _selectedMonth.asStateFlow()
    val totalSpentByCategoryInMonthModelProducer = CartesianChartModelProducer()
    private val _chartViewState = MutableStateFlow<ByCategoryChartViewState>(ByCategoryChartViewState.Loading)
    val chartViewState = _chartViewState.asStateFlow()

    init {
        _selectedMonth
            .onEach { (month, year) ->
                _chartViewState.update { ByCategoryChartViewState.Loading }
                // Fake loading
                delay(500L)
                getTotalSpentByCategoriesInMonthUseCase
                    .invoke(
                        month = month,
                        year = year,
                    ).onSuccess { result ->
                        when {
                            result.isEmpty() -> _chartViewState.update { ByCategoryChartViewState.NoData }
                            else -> {
                                _chartViewState.update { ByCategoryChartViewState.Data(categoryNames = result.keys.toList()) }
                                totalSpentByCategoryInMonthModelProducer.runTransaction {
                                    columnSeries {
                                        series(result.values)
                                    }
                                }
                            }
                        }
                    }.onFailure { error ->
                        _chartViewState.update { ByCategoryChartViewState.Error(message = error.message.orEmpty()) }
                    }
            }.launchIn(viewModelScope)
    }

    fun onMonthPicked(
        month: Int,
        year: Int,
    ) {
        _selectedMonth.value = MonthYear(month, year)
    }
}

sealed class ByCategoryChartViewState {
    object Loading : ByCategoryChartViewState()

    object NoData : ByCategoryChartViewState()

    data class Error(
        val message: String,
    ) : ByCategoryChartViewState()

    data class Data(
        val categoryNames: List<String>,
    ) : ByCategoryChartViewState()
}
