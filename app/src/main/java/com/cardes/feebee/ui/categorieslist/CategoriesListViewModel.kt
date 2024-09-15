package com.cardes.feebee.ui.categorieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardes.domain.usecase.observecategories.ObserveCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CategoriesListViewModel @Inject constructor(
    observeCategoriesUseCase: ObserveCategoriesUseCase,
) : ViewModel() {
    val categories = observeCategoriesUseCase.invoke()
        .stateIn(
            initialValue = listOf(),
            started = SharingStarted.WhileSubscribed(500),
            scope = viewModelScope,
        )
}
