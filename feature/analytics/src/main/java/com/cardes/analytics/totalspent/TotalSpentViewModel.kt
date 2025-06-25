package com.cardes.analytics.totalspent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardes.domain.entity.Category
import com.cardes.domain.entity.Spending
import com.cardes.domain.usecase.GetSpendingsByCategoriesByDateRangeUseCase
import com.cardes.domain.usecase.getspendingsbydaterange.GetSpendingsByDateRangeUseCase
import com.cardes.domain.usecase.observecategories.ObserveCategoriesUseCase
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.Calendar
import javax.inject.Inject

private const val DEFAULT_TIME_RANGE_IN_MONTHS = 12

@HiltViewModel
class TotalSpentViewModel @Inject constructor(
    private val getSpendingsByDateRangeUseCase: GetSpendingsByDateRangeUseCase,
    private val getSpendingsByCategoriesByDateRangeUseCase: GetSpendingsByCategoriesByDateRangeUseCase,
    observeCategoriesUseCase: ObserveCategoriesUseCase,
) : ViewModel() {
    private val spendingList = MutableStateFlow(listOf<Spending>())
    private val selectedCategoryIds = MutableStateFlow<List<Long>>(listOf())

    private val initialFromDate = Calendar
        .getInstance()
        .apply {
            add(Calendar.MONTH, -DEFAULT_TIME_RANGE_IN_MONTHS)
        }.timeInMillis
    private val initialToDate = Calendar.getInstance().timeInMillis

    private val toDate = MutableStateFlow(initialToDate)
    private val fromDate = MutableStateFlow(initialFromDate)

    val spendingsChartModelProducer = CartesianChartModelProducer()

    val spendingsData = spendingList
        .map { spendings ->
            spendings
                .groupBy { spending ->
                    Calendar
                        .getInstance()
                        .run {
                            timeInMillis = spending.time
                            get(Calendar.MONTH)
                        }
                }.mapValues { element ->
                    element.value.sumOf { it.amount }
                }
        }.onEach { spendingsData ->
            if (spendingsData.isNotEmpty()) {
                spendingsChartModelProducer.runTransaction {
                    lineSeries {
                        series(
                            x = spendingsData.keys,
                            y = spendingsData.values,
                        )
                    }
                }
            }
        }.stateIn(
            initialValue = hashMapOf(),
            started = SharingStarted.WhileSubscribed(500L),
            scope = viewModelScope,
        )

    val totalSpentViewState = combine(
        observeCategoriesUseCase.invoke(),
        selectedCategoryIds,
        fromDate,
        toDate,
    ) { categories, selectedIds, fromDate, toDate ->
        val selectCategoryViewStates = categories.map { category ->
            SelectCategoryViewState(
                category = category,
                isSelected = selectedIds.contains(category.id),
            )
        }
        TotalSpentViewState(
            selectCategoryViewStates = selectCategoryViewStates,
            fromDate = fromDate,
            toDate = toDate,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = TotalSpentViewState(
            selectCategoryViewStates = listOf(),
            fromDate = 0,
            toDate = 0,
        ),
    )

    init {
        combine(
            selectedCategoryIds,
            fromDate,
            toDate,
        ) { selectedCategoryIds, fromDate, toDate ->
            when {
                selectedCategoryIds.isEmpty() -> {
                    getSpendingsByDateRangeUseCase
                        .invoke(
                            from = fromDate,
                            to = toDate,
                        ).onSuccess { spendings ->
                            spendingList.value = spendings
                        }.onFailure {
                            // TODO handle errors later
                        }
                }

                else -> {
                    getSpendingsByCategoriesByDateRangeUseCase
                        .invoke(
                            categoryIds = selectedCategoryIds,
                            from = fromDate,
                            to = toDate,
                        ).onSuccess { spendings ->
                            spendingList.value = spendings
                        }.onFailure {
                            // TODO handle errors later
                        }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onCategoryClick(categoryId: Long) {
        val selectedIds = selectedCategoryIds.value.toMutableList()
        if (selectedIds.contains(categoryId)) {
            selectedCategoryIds.update { selectedIds.apply { remove(categoryId) } }
        } else {
            selectedCategoryIds.update { selectedIds.apply { add(categoryId) } }
        }
    }

    fun onFromDatePicked(time: Long) {
        fromDate.value = time
        if (time > toDate.value) toDate.value = time
    }

    fun onToDatePicked(time: Long) {
        toDate.value = time
        if (time < fromDate.value) fromDate.value = time
    }
}

data class TotalSpentViewState(
    val selectCategoryViewStates: List<SelectCategoryViewState>,
    val fromDate: Long,
    val toDate: Long,
)

data class SelectCategoryViewState(
    val category: Category,
    val isSelected: Boolean,
)
