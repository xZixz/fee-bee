package com.cardes.feebee.ui.categorieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardes.domain.usecase.addcategory.AddCategoryUseCase
import com.cardes.domain.usecase.observecategories.ObserveCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesListViewModel @Inject constructor(
    private val addCategoryUseCase: AddCategoryUseCase,
    observeCategoriesUseCase: ObserveCategoriesUseCase,
) : ViewModel() {
    private val _showDatePickerDialog = MutableStateFlow(false)
    val showDatePickerDialog = _showDatePickerDialog.asStateFlow()

    fun onCreateCategoryClick() {
        _showDatePickerDialog.value = true
    }

    fun onCreateCategory(categoryName: String) {
        viewModelScope.launch {
            addCategoryUseCase.invoke(categoryName)
                .onSuccess {
                    dismissNewCategoryDialog()
                }
                .onFailure {
                    // TODO: handle error case later
                }
        }
    }

    fun dismissNewCategoryDialog() {
        _showDatePickerDialog.value = false
    }

    val categories = observeCategoriesUseCase.invoke()
        .stateIn(
            initialValue = listOf(),
            started = SharingStarted.WhileSubscribed(500),
            scope = viewModelScope,
        )
}
