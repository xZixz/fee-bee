package com.cardes.feebee.ui.analytics

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

    private val selectedCategoryId = MutableStateFlow<Long?>(null)
    val pieChartViewState = combine(
        observeCategoriesUseCase.invoke(),
        selectedCategoryId,
    ) { categories, selectedCategoryId ->
        val selectedId = when {
            categories.isEmpty() -> 0L
            selectedCategoryId == null -> categories.first().id
            else -> selectedCategoryId
        }
        PieChartViewState(
            selectedCategoryId = selectedId,
            categories = categories,
            selectedMonthTime = 0,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = PieChartViewState(
            selectedCategoryId = 0,
            categories = listOf(),
            selectedMonthTime = 0,
        ),
    )

    fun onCategoryClick(categoryId: Long) {
        selectedCategoryId.value = categoryId
    }
}

data class PieChartViewState(
    val selectedCategoryId: Long,
    val categories: List<Category>,
    val selectedMonthTime: Long,
)
