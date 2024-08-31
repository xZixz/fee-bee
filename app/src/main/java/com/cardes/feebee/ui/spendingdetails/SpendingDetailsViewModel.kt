package com.cardes.feebee.ui.spendingdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardes.domain.usecase.getspending.GetSpendingUseCase
import com.cardes.domain.usecase.removespending.RemoveSpendingUseCase
import com.cardes.feebee.ui.createspending.spendingDateDisplayFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpendingDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSpendingUseCase: GetSpendingUseCase,
    private val removeSpendingUseCase: RemoveSpendingUseCase,
) : ViewModel() {
    private val spendingId: Long = checkNotNull(savedStateHandle[SPENDING_ID_ARG])
    private val _spendingUiState = MutableStateFlow<SpendingUiState>(SpendingUiState.Loading)
    val spendingUiState = _spendingUiState.asStateFlow()
    private val _removeSpendingDialogState = MutableStateFlow(false)
    val removeSpendingDialogState = _removeSpendingDialogState.asStateFlow()

    init {
        viewModelScope.launch {
            getSpendingUseCase(spendingId = spendingId)
                .onSuccess { spending ->
                    _spendingUiState.emit(
                        SpendingUiState.Success(
                            description = spending.content,
                            date = spendingDateDisplayFormat.format(spending.time),
                            categories = spending.categories,
                            cost = spending.amount.toString(),
                            viewMode = SpendingUiState.ViewMode.VIEW_ONLY,
                        ),
                    )
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

    fun onRemoveSpendingClick() {
        _removeSpendingDialogState.value = true
    }

    fun closeRemoveSpendingDialog() {
        _removeSpendingDialogState.value = false
    }

    fun onEditClick() {
        val successState = _spendingUiState.value as? SpendingUiState.Success ?: return

        _spendingUiState.update { successState.copy(viewMode = SpendingUiState.ViewMode.EDIT) }
    }

    fun exitEditMode() {
        val successState = _spendingUiState.value as? SpendingUiState.Success ?: return

        _spendingUiState.update { successState.copy(viewMode = SpendingUiState.ViewMode.VIEW_ONLY) }
    }
}
