package com.cardes.feebee.ui.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardes.domain.entity.Category
import com.cardes.domain.entity.Spending
import com.cardes.domain.usecase.getallspendings.GetAllSpendingsUseCase
import com.cardes.domain.usecase.getspendingsbycategories.GetSpendingsByCategoriesUseCase
import com.cardes.domain.usecase.observecategories.ObserveCategoriesUseCase
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class TotalSpentViewModel @Inject constructor(
    private val getSpendingsByCategoriesUseCase: GetSpendingsByCategoriesUseCase,
    private val getAllSpendingsUseCase: GetAllSpendingsUseCase,
    observeCategoriesUseCase: ObserveCategoriesUseCase,
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

    private val selectedCategoryIds = MutableStateFlow<List<Long>>(listOf())

    val selectCategoryViewStates = combine(
        observeCategoriesUseCase.invoke(),
        selectedCategoryIds,
    ) { categories, selectedIds ->
        categories.map { category ->
            SelectCategoryViewState(
                category = category,
                isSelected = selectedIds.contains(category.id),
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = listOf(),
    )

    init {
        viewModelScope.launch {
            getAllSpendings()
        }

        selectedCategoryIds
            .onEach { selectedIds ->
                when {
                    selectedIds.isEmpty() -> getAllSpendings()
                    else -> getSpendingsByCategories(selectedIds)
                }
            }.launchIn(viewModelScope)
    }

    private fun getAllSpendings() {
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

    private fun getSpendingsByCategories(categoryIds: List<Long>) {
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

    fun onCategoryClick(categoryId: Long) {
        val selectedIds = selectedCategoryIds.value.toMutableList()
        if (selectedIds.contains(categoryId)) {
            selectedCategoryIds.update { selectedIds.apply { remove(categoryId) } }
        } else {
            selectedCategoryIds.update { selectedIds.apply { add(categoryId) } }
        }
    }
}

data class SelectCategoryViewState(
    val category: Category,
    val isSelected: Boolean,
)
