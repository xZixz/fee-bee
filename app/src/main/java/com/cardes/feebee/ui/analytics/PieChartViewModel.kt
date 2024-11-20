package com.cardes.feebee.ui.analytics

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardes.domain.entity.Category
import com.cardes.domain.usecase.GetSpendingsByCategoriesByDateRange
import com.cardes.domain.usecase.observecategories.ObserveCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PieChartViewModel @Inject constructor(
    private val getSpendingsByCategoriesByDateRange: GetSpendingsByCategoriesByDateRange,
    observeCategoriesUseCase: ObserveCategoriesUseCase,
) : ViewModel() {

    private val selectedMonth = MutableStateFlow(
        MonthYear(
            month = Calendar.getInstance().get(Calendar.MONTH) + 1,
            year = Calendar.getInstance().get(Calendar.YEAR),
        ),
    )

    private val selectedCategoryId = MutableStateFlow<Long?>(null)
    val pieChartViewState = combine(
        observeCategoriesUseCase.invoke(),
        selectedMonth,
        selectedCategoryId,
    ) { categories, selectedMonth, selectedCategoryId ->
        val selectedId = when {
            categories.isEmpty() -> 0L
            selectedCategoryId == null -> categories.first().id
            else -> selectedCategoryId
        }
        PieChartViewState(
            selectCategoryViewState = PieChartViewState.SelectCategoryViewState(
                selectedCategoryId = selectedId,
                categories = categories,
            ),
            selectedMonth = selectedMonth,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = PieChartViewState(
            selectCategoryViewState = PieChartViewState.SelectCategoryViewState(
                selectedCategoryId = 0,
                categories = listOf(),
            ),
            selectedMonth = selectedMonth.value,
        ),
    )

    fun onCategoryClick(categoryId: Long) {
        selectedCategoryId.value = categoryId
    }

    fun onMonthPicked(month: Int, year: Int) {
        selectedMonth.value = MonthYear(month, year)
    }
}

data class PieChartViewState(
    val selectedMonth: MonthYear,
    val selectCategoryViewState: SelectCategoryViewState,
) {
    data class SelectCategoryViewState(
        val selectedCategoryId: Long,
        val categories: List<Category>,
    )
}

data class MonthYear(
    val month: Int,
    val year: Int,
)
