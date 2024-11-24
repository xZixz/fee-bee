package com.cardes.feebee.ui.analytics

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardes.domain.entity.Category
import com.cardes.domain.usecase.GetSpendingsByCategoriesByDateRangeUseCase
import com.cardes.domain.usecase.getspendingsbydaterange.GetSpendingsByDateRangeUseCase
import com.cardes.domain.usecase.observecategories.ObserveCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class PieChartViewModel @Inject constructor(
    private val getSpendingsByCategoriesByDateRangeUseCase: GetSpendingsByCategoriesByDateRangeUseCase,
    private val getSpendingsByDateRangeUseCase: GetSpendingsByDateRangeUseCase,
    observeCategoriesUseCase: ObserveCategoriesUseCase,
) : ViewModel() {
    private val _selectedMonth = MutableStateFlow(
        MonthYear(
            month = Calendar.getInstance().get(Calendar.MONTH) + 1,
            year = Calendar.getInstance().get(Calendar.YEAR),
        ),
    )
    val selectedMonth = _selectedMonth.asStateFlow()

    private val observeCategories = observeCategoriesUseCase.invoke()

    private val categoryIdSelected = MutableStateFlow<Long?>(null)

    private val selectedCategory = combine(
        categoryIdSelected,
        observeCategories,
    ) { categoryIdPicked, categories ->
        when {
            categories.isEmpty() -> null
            categoryIdPicked == null -> categories.first()
            else -> categories.first { it.id == categoryIdPicked }
        }
    }

    val selectCategoryViewState = combine(
        observeCategories,
        selectedCategory,
    ) { categories, selectedCategory ->
        PieChartSelectCategoryViewState(
            selectedCategoryId = selectedCategory?.id,
            categories = categories,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = PieChartSelectCategoryViewState(
            selectedCategoryId = null,
            categories = listOf(),
        ),
    )

    private val totalSpentOfTheMonth = _selectedMonth
        .map { monthYear ->
            val (startOfTheMonth, endOfTheMonth) = getStartAndEndTimeOfAMonth(monthYear)
            return@map getSpendingsByDateRangeUseCase
                .invoke(
                    from = startOfTheMonth,
                    to = endOfTheMonth,
                ).getOrElse { listOf() }
                .sumOf { it.amount }
        }

    private val totalSpentOfACategoryOfTheMonth: Flow<TotalCategorySpentViewState> = combine(
        selectedCategory,
        _selectedMonth,
    ) { selectedCategory, selectedMonth ->
        when {
            selectedCategory == null -> TotalCategorySpentViewState.NoCategoryPicked
            else -> TotalCategorySpentViewState.DataAvailable(
                categoryName = selectedCategory.name,
                totalSpent = getTotalSpentOfACategoryOfAMonth(
                    categoryId = selectedCategory.id,
                    monthYear = selectedMonth,
                ),
            )
        }
    }

    private sealed class TotalCategorySpentViewState {
        object NoCategoryPicked : TotalCategorySpentViewState()

        data class DataAvailable(
            val totalSpent: BigDecimal,
            val categoryName: String,
        ) : TotalCategorySpentViewState()
    }

    val spendingPercentageViewState: StateFlow<SpendingPercentageViewState> = combine(
        totalSpentOfTheMonth,
        totalSpentOfACategoryOfTheMonth,
    ) { totalSpentOfTheMonth, totalCategorySpentViewState ->
        if (totalSpentOfTheMonth == BigDecimal.ZERO) return@combine SpendingPercentageViewState.NoSpendingsInThisMonth

        return@combine when (totalCategorySpentViewState) {
            TotalCategorySpentViewState.NoCategoryPicked -> SpendingPercentageViewState.NoCategories(sumOfMonth = totalSpentOfTheMonth)
            is TotalCategorySpentViewState.DataAvailable -> {
                val percentageValue = totalCategorySpentViewState.totalSpent
                    .divide(totalSpentOfTheMonth, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal(100))
                    .setScale(2)
                SpendingPercentageViewState.DataAvailable(
                    categoryName = totalCategorySpentViewState.categoryName,
                    percentageValue = percentageValue,
                    percentageString = percentageValue.toPlainString(),
                    sumOfMonth = totalSpentOfTheMonth,
                    sumOfCategory = totalCategorySpentViewState.totalSpent,
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = SpendingPercentageViewState.Loading,
    )

    fun onCategoryClick(categoryId: Long) {
        categoryIdSelected.value = categoryId
    }

    fun onMonthPicked(
        month: Int,
        year: Int,
    ) {
        _selectedMonth.value = MonthYear(month, year)
    }

    private fun getStartAndEndTimeOfAMonth(monthYear: MonthYear): Pair<Long, Long> {
        val startOfTheMonth = Calendar
            .getInstance()
            .apply {
                set(Calendar.MONTH, monthYear.month - 1)
                set(Calendar.YEAR, monthYear.year)
                set(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

        val endOfTheMonth = Calendar
            .getInstance()
            .apply {
                set(Calendar.MONTH, monthYear.month)
                set(Calendar.YEAR, monthYear.year)
                set(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis - 1

        return Pair(startOfTheMonth, endOfTheMonth)
    }

    private suspend fun getTotalSpentOfACategoryOfAMonth(
        categoryId: Long,
        monthYear: MonthYear,
    ): BigDecimal {
        val (startOfMonth, endOfMonth) = getStartAndEndTimeOfAMonth(monthYear = monthYear)
        return getSpendingsByCategoriesByDateRangeUseCase
            .invoke(
                categoryIds = listOf(categoryId),
                from = startOfMonth,
                to = endOfMonth,
            ).getOrElse { listOf() }
            .sumOf { it.amount }
    }
}

data class PieChartSelectCategoryViewState(
    val selectedCategoryId: Long?,
    val categories: List<Category>,
)

data class MonthYear(
    val month: Int,
    val year: Int,
)

sealed class SpendingPercentageViewState {
    object Loading : SpendingPercentageViewState()

    data class NoCategories(
        val sumOfMonth: BigDecimal,
    ) : SpendingPercentageViewState()

    object NoSpendingsInThisMonth : SpendingPercentageViewState()

    data class DataAvailable(
        val categoryName: String,
        val percentageString: String,
        val percentageValue: BigDecimal,
        val sumOfMonth: BigDecimal,
        val sumOfCategory: BigDecimal,
    ) : SpendingPercentageViewState()
}
