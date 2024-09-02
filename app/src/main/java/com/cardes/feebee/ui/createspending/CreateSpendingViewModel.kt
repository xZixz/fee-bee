package com.cardes.feebee.ui.createspending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardes.domain.usecase.addcategory.AddCategoryUseCase
import com.cardes.domain.usecase.createspending.CreateSpendingUseCase
import com.cardes.domain.usecase.observecategories.ObserveCategoriesUseCase
import com.cardes.feebee.ui.common.StringUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

val nonBigDecimalCharRegex = "[^0-9,.]".toRegex()
val spendingDateDisplayFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.US)

@HiltViewModel
class CreateSpendingViewModel @Inject constructor(
    private val createSpendingUseCase: CreateSpendingUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val observeCategoriesUseCase: ObserveCategoriesUseCase,
) : ViewModel() {
    private val _createSpendingUiState = MutableStateFlow(
        CreateSpendingUiState(
            description = "",
            amount = "",
            date = spendingDateDisplayFormat.format(Calendar.getInstance().time),
            categories = listOf(),
            selectedCategoryIds = listOf(),
        ),
    )
    val createSpendingUiState = _createSpendingUiState.asStateFlow()

    private val _showDatePickerDialog = MutableStateFlow(false)
    val showDatePickerDialog = _showDatePickerDialog.asStateFlow()

    private val _showAddCategoryDialog = MutableStateFlow(false)
    val showAddCategoryDialog = _showAddCategoryDialog.asStateFlow()

    init {
        viewModelScope.launch {
            observeCategoriesUseCase.invoke()
                .collect { categories ->
                    _createSpendingUiState.update { it.copy(categories = categories) }
                }
        }
    }

    fun onDescriptionChanged(content: String) {
        _createSpendingUiState.update { it.copy(description = content) }
    }

    fun onCostChanged(cost: String) {
        _createSpendingUiState.update {
            it.copy(amount = cost.replace(nonBigDecimalCharRegex, ""))
        }
    }

    fun createSpending(onFinishedCreating: () -> Unit) {
        viewModelScope.launch {
            _createSpendingUiState.value.run {
                createSpendingUseCase.invoke(
                    content = description,
                    amount = StringUtil.parseBigDecimalString(amount),
                    time = spendingDateDisplayFormat.parse(date)?.time ?: 0L,
                    categoryIds = selectedCategoryIds,
                )
            }.onSuccess {
                onFinishedCreating()
            }.onFailure {
                // TODO: error handling later
            }
        }
    }

    fun showDatePickerDialog() {
        _showDatePickerDialog.update { true }
    }

    fun hideDatePickerDialog() {
        _showDatePickerDialog.update { false }
    }

    fun onDatePicked(timeInMillis: Long) {
        val pickedDate = Calendar.getInstance().apply { this.timeInMillis = timeInMillis }.time
        _createSpendingUiState.update { it.copy(date = spendingDateDisplayFormat.format(pickedDate)) }
        hideDatePickerDialog()
    }

    fun onCategoryClick(categoryId: Long) {
        _createSpendingUiState.update {
            with(it.selectedCategoryIds.toMutableList()) {
                if (remove(categoryId).not()) add(categoryId)
                it.copy(selectedCategoryIds = this)
            }
        }
    }

    fun onAddCategoryClick() {
        _showAddCategoryDialog.value = true
    }

    fun onAddCategoryDialogDismiss() {
        _showAddCategoryDialog.value = false
    }

    fun onAddCategory(categoryName: String) {
        _showAddCategoryDialog.value = false
        viewModelScope.launch {
            addCategoryUseCase.invoke(name = categoryName)
        }
    }
}
