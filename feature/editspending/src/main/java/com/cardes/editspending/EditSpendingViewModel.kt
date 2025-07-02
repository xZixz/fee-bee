package com.cardes.editspending

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.cardes.designsystem.common.nonBigDecimalCharRegex
import com.cardes.domain.entity.Category
import com.cardes.domain.usecase.addcategory.AddCategoryUseCase
import com.cardes.domain.usecase.createspending.CreateSpendingUseCase
import com.cardes.domain.usecase.getspending.GetSpendingUseCase
import com.cardes.domain.usecase.observecategories.ObserveCategoriesUseCase
import com.cardes.domain.usecase.removespending.RemoveSpendingUseCase
import com.cardes.domain.usecase.updatespending.UpdateSpendingUseCase
import com.cardes.editspending.navigation.EditSpendingRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class EditSpendingViewModel @Inject constructor(
    private val createSpendingUseCase: CreateSpendingUseCase,
    private val updateSpendingUseCase: UpdateSpendingUseCase,
    private val removeSpendingUseCase: RemoveSpendingUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val getSpendingUseCase: GetSpendingUseCase,
    private val observeCategoriesUseCase: ObserveCategoriesUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val spendingId: Long = savedStateHandle.toRoute<EditSpendingRoute>().spendingId
    val editMode: EditMode
        get() = if (spendingId == 0L) EditMode.NEW else EditMode.EDIT

    private val _editSpendingUiState = MutableStateFlow(
        EditSpendingUiState(
            description = "",
            amount = "",
            date = Calendar.getInstance().timeInMillis,
            categories = listOf(),
            selectedCategoryIds = listOf(),
        ),
    )

    val editSpendingUiState = _editSpendingUiState.asStateFlow()

    private val _showAddCategoryDialog = MutableStateFlow(false)
    val showAddCategoryDialog = _showAddCategoryDialog.asStateFlow()

    private val _removeSpendingDialogState = MutableStateFlow(false)
    val removeSpendingDialogState = _removeSpendingDialogState.asStateFlow()

    init {
        viewModelScope.launch {
            observeCategoriesUseCase
                .invoke()
                .collect { categories ->
                    _editSpendingUiState.update { it.copy(categories = categories) }
                }
        }

        viewModelScope.launch {
            getSpendingUseCase
                .invoke(spendingId)
                .onSuccess { spending ->
                    _editSpendingUiState.update {
                        it.copy(
                            description = spending.content,
                            amount = spending.amount.toString(),
                            date = spending.time,
                            selectedCategoryIds = spending.categories.map(Category::id),
                        )
                    }
                }.onFailure {
                    // TODO handle error state later
                }
        }
    }

    fun onDescriptionChanged(content: String) {
        _editSpendingUiState.update { it.copy(description = content) }
    }

    fun onCostChanged(cost: String) {
        _editSpendingUiState.update {
            it.copy(amount = cost.replace(nonBigDecimalCharRegex, ""))
        }
    }

    private fun createSpending(onFinishUpdating: () -> Unit) {
        viewModelScope.launch {
            _editSpendingUiState.value
                .run {
                    createSpendingUseCase.invoke(
                        content = description,
                        amount = parseBigDecimalString(amount),
                        time = date,
                        categoryIds = selectedCategoryIds,
                    )
                }.onSuccess {
                    onFinishUpdating()
                }.onFailure {
                    // TODO: error handling later
                }
        }
    }

    fun onCtaButtonClick(onFinishedCreating: () -> Unit) {
        when (editMode) {
            EditMode.NEW -> createSpending(onFinishedCreating)
            EditMode.EDIT -> updateSpending(onFinishedCreating)
        }
    }

    private fun updateSpending(onFinishUpdating: () -> Unit) {
        viewModelScope.launch {
            _editSpendingUiState.value.run {
                updateSpendingUseCase
                    .invoke(
                        id = spendingId,
                        content = description,
                        amount = parseBigDecimalString(amount),
                        time = date,
                        categoryIds = selectedCategoryIds,
                    ).onSuccess {
                        onFinishUpdating()
                    }.onFailure {
                        // TODO: error handling later
                    }
            }
        }
    }

    fun onDatePicked(timeInMillis: Long) {
        _editSpendingUiState.update { it.copy(date = timeInMillis) }
    }

    fun onCategoryClick(categoryId: Long) {
        _editSpendingUiState.update {
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

    fun onRemoveSpendingClick() {
        _removeSpendingDialogState.value = true
    }

    fun closeRemoveSpendingDialog() {
        _removeSpendingDialogState.value = false
    }

    fun removeSpending(onSpendingRemoved: () -> Unit) {
        viewModelScope.launch {
            removeSpendingUseCase
                .invoke(spendingId)
                .onSuccess {
                    closeRemoveSpendingDialog()
                    onSpendingRemoved()
                }.onFailure {
                    // TODO Handle error later
                }
        }
    }

    private fun parseBigDecimalString(bigDecimalString: String): BigDecimal {
        val decimalFormatSymbols = DecimalFormatSymbols().apply {
            groupingSeparator = ','
            decimalSeparator = '.'
        }
        val pattern = "#,##0.0#"
        val decimalFormat = DecimalFormat(pattern, decimalFormatSymbols).apply { isParseBigDecimal = true }
        return decimalFormat.parse(bigDecimalString) as BigDecimal
    }
}

enum class EditMode {
    NEW,
    EDIT,
}
