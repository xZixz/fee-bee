package com.cardes.feebee.ui.editspending

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardes.domain.usecase.getspending.GetSpendingUseCase
import com.cardes.domain.usecase.removespending.RemoveSpendingUseCase
import com.cardes.domain.usecase.updatespending.UpdateSpendingUseCase
import com.cardes.feebee.ui.common.StringUtil
import com.cardes.feebee.ui.createspending.nonBigDecimalCharRegex
import com.cardes.feebee.ui.createspending.spendingDateDisplayFormat
import com.cardes.feebee.ui.spendingdetails.SPENDING_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

@HiltViewModel
class EditSpendingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val removeSpendingUseCase: RemoveSpendingUseCase,
    private val getSpendingUseCase: GetSpendingUseCase,
    private val updateSpendingUseCase: UpdateSpendingUseCase,
) : ViewModel() {
    private val spendingId: Long = checkNotNull(savedStateHandle[SPENDING_ID_ARG])
    private val _removeSpendingDialogState = MutableStateFlow(false)
    val removeSpendingDialogState = _removeSpendingDialogState.asStateFlow()
    private val editingSpending = MutableSharedFlow<EditedSpendingUiState>(replay = 1)
    private val _showDatePicker = MutableStateFlow(false)
    val showDatePicker = _showDatePicker.asStateFlow()

    val editSpendingScreenUiState: StateFlow<EditSpendingScreenUiState> = editingSpending
        .map { editedSpending ->
            EditSpendingScreenUiState.Success(editedSpendingUiState = editedSpending)
        }
        .stateIn(
            initialValue = EditSpendingScreenUiState.Loading,
            started = SharingStarted.WhileSubscribed(500L),
            scope = viewModelScope,
        )

    init {
        viewModelScope.launch {
            getSpendingUseCase.invoke(spendingId)
                .onSuccess { spending ->
                    editingSpending.emit(
                        EditedSpendingUiState(
                            description = spending.content,
                            cost = spending.amount.toString(),
                            date = spendingDateDisplayFormat.format(spending.time),
                        ),
                    )
                }
                .onFailure {
                    // TODO: error handling later
                }
        }
    }

    fun removeSpending(onSpendingRemoved: () -> Unit) {
        viewModelScope.launch {
            removeSpendingUseCase(spendingId = spendingId)
                .onSuccess {
                    closeRemoveSpendingDialog()
                    onSpendingRemoved()
                }
                .onFailure {
                    // TODO: error handling later
                }
        }
    }

    fun closeRemoveSpendingDialog() {
        _removeSpendingDialogState.value = false
    }

    fun onRemoveSpendingClick() {
        _removeSpendingDialogState.value = true
    }

    fun onSpendingNameChanged(name: String) {
        viewModelScope.launch {
            editingSpending.update { it.copy(description = name) }
        }
    }

    fun onCostChanged(cost: String) {
        viewModelScope.launch {
            editingSpending.update {
                it.copy(cost = cost.replace(nonBigDecimalCharRegex, ""))
            }
        }
    }

    fun showDatePicker() {
        _showDatePicker.value = true
    }

    fun hideDatePicker() {
        _showDatePicker.value = false
    }

    fun onDatePicked(time: Long) {
        viewModelScope.launch {
            _showDatePicker.value = false
            editingSpending.update { it.copy(date = spendingDateDisplayFormat.format(time)) }
        }
    }

    fun update(onDoneUpdating: () -> Unit) {
        viewModelScope.launch {
            val editedSpending = editingSpending.first()
            updateSpendingUseCase.invoke(
                id = spendingId,
                content = editedSpending.description,
                amount = StringUtil.parseBigDecimalString(editedSpending.cost),
                time = spendingDateDisplayFormat.parse(editedSpending.date)?.time ?: 0L,
            )
                .onSuccess {
                    onDoneUpdating()
                }
                .onFailure {
                    // TODO handle error states later
                }
        }
    }
}

suspend fun <T> MutableSharedFlow<T>.update(block: (T) -> T) {
    val value = withTimeoutOrNull(1_000) {
        first()
    } ?: return

    emit(block(value))
}

sealed class EditSpendingScreenUiState {
    data object Loading : EditSpendingScreenUiState()

    data class Success(val editedSpendingUiState: EditedSpendingUiState) : EditSpendingScreenUiState()
}

data class EditedSpendingUiState(
    val description: String,
    val date: String,
    val cost: String,
)
