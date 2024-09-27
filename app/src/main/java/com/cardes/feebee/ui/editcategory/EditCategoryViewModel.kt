package com.cardes.feebee.ui.editcategory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardes.domain.usecase.observecategory.ObserveCategoryUseCase
import com.cardes.domain.usecase.updatecategoryname.UpdateCategoryNameUseCase
import com.cardes.feebee.navigation.CATEGORY_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCategoryViewModel @Inject constructor(
    private val updateCategoryNameUseCase: UpdateCategoryNameUseCase,
    observeCategoryUseCase: ObserveCategoryUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _showCategoryNameEditDialog = MutableStateFlow(false)
    val showCategoryNameEditDialog = _showCategoryNameEditDialog.asStateFlow()

    fun onCategoryNameEditClick() {
        _showCategoryNameEditDialog.value = true
    }

    fun onConfirmUpdateCategoryName(newCategoryName: String) {
        dismissEditCategoryNameDialog()
        viewModelScope.launch {
            updateCategoryNameUseCase.invoke(
                categoryId = categoryId,
                categoryName = newCategoryName,
            )
        }
    }

    fun dismissEditCategoryNameDialog() {
        _showCategoryNameEditDialog.value = false
    }

    fun onEditingCategoryNameChanged(editingCategoryName: String) {
        editingCategoryNameFlow.value = editingCategoryName
    }

    private val categoryId: Long = savedStateHandle[CATEGORY_ID_ARG] ?: 0L

    private val editingCategoryNameFlow = MutableStateFlow("")

    private val categoryFlow = observeCategoryUseCase
        .invoke(categoryId = categoryId)
        .onEach { category ->
            editingCategoryNameFlow.emit(category.name)
        }

    val fetchingCategoryUiState = combine(
        categoryFlow,
        editingCategoryNameFlow,
    ) { category, editingCategoryName ->
        FetchingCategoryUiState.Success(
            editCategoryUiState = EditCategoryUiState(
                categoryName = category.name,
                editingCategoryName = editingCategoryName,
            ),
        )
    }.stateIn(
        initialValue = FetchingCategoryUiState.Loading,
        started = SharingStarted.WhileSubscribed(500L),
        scope = viewModelScope,
    )
}

sealed class FetchingCategoryUiState {
    data object Loading : FetchingCategoryUiState()

    data class Success(
        val editCategoryUiState: EditCategoryUiState,
    ) : FetchingCategoryUiState()
}

data class EditCategoryUiState(
    val categoryName: String,
    val editingCategoryName: String,
)
