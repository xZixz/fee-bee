package com.cardes.feebee.ui.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardes.domain.entity.Spending
import com.cardes.domain.usecase.getallspendings.GetAllSpendingsUseCase
import com.cardes.domain.usecase.getspendingsbycategories.GetSpendingsByCategoriesUseCase
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class TotalSpentViewModel @Inject constructor(
    private val getSpendingsByCategoriesUseCase: GetSpendingsByCategoriesUseCase,
    private val getAllSpendingsUseCase: GetAllSpendingsUseCase,
) : ViewModel() {
    private val spendingList = MutableStateFlow(listOf<Spending>())
    private val selectedYear = MutableStateFlow(Calendar.getInstance().get(Calendar.YEAR))

    val spendingsChartModelProducer = CartesianChartModelProducer()

    val spendingsByMonthData = combine(spendingList, selectedYear) { spendings, selectedYear ->
        spendings
            .filter { spending ->
                val date = Calendar.getInstance().apply { timeInMillis = spending.time }
                date.get(Calendar.YEAR) == selectedYear
            }.groupBy { spending ->
                Calendar
                    .getInstance()
                    .run {
                        timeInMillis = spending.time
                        get(Calendar.MONTH)
                    }
            }.mapValues { element ->
                element.value.sumOf { it.amount }
            }.also {
                spendingsChartModelProducer.runTransaction {
                    columnSeries {
                        series(
                            x = it.keys,
                            y = it.values,
                        )
                    }
                }
            }
    }.stateIn(
        initialValue = hashMapOf(),
        started = SharingStarted.WhileSubscribed(500L),
        scope = viewModelScope,
    )

    init {
        viewModelScope.launch {
            getAllSpendingsUseCase
                .invoke()
                .onSuccess { spendings ->
                    spendingList.value = spendings
                }.onFailure {
                    // TODO: handle failure case later
                }
        }
    }

    fun getSpendingsByCategories(categoryIds: List<Long>) {
        viewModelScope.launch {
            getSpendingsByCategoriesUseCase
                .invoke(categoryIds)
                .onSuccess { spendings ->
                    spendingList.value = spendings
                }.onFailure {
                    // TODO: handle failure case later
                }
        }
    }
}
