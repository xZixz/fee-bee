package com.cardes.editcategory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.cardes.domain.usecase.observecategory.ObserveCategoryUseCase
import com.cardes.domain.usecase.removecategory.RemoveCategoryUseCase
import com.cardes.domain.usecase.removecategoryemoji.RemoveCategoryEmojiUseCase
import com.cardes.domain.usecase.updatecategoryemoji.UpdateCategoryEmojiUseCase
import com.cardes.domain.usecase.updatecategoryname.UpdateCategoryNameUseCase
import com.cardes.editcategory.navigation.EditCategoryRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCategoryViewModel @Inject constructor(
    private val updateCategoryNameUseCase: UpdateCategoryNameUseCase,
    private val removeCategoryUseCase: RemoveCategoryUseCase,
    private val updateCategoryEmojiUseCase: UpdateCategoryEmojiUseCase,
    private val removeCategoryEmojiUseCase: RemoveCategoryEmojiUseCase,
    observeCategoryUseCase: ObserveCategoryUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _events = MutableSharedFlow<EditCategoryEvent>(
        replay = 0,
        extraBufferCapacity = 1,
    )
    val events = _events.asSharedFlow()

    fun onConfirmUpdateCategoryName(newCategoryName: String) {
        viewModelScope.launch {
            updateCategoryNameUseCase.invoke(
                categoryId = categoryId,
                categoryName = newCategoryName,
            )
        }
    }

    fun onEditingCategoryNameChanged(editingCategoryName: String) {
        editingCategoryNameFlow.value = editingCategoryName
    }

    private val categoryId: Long = savedStateHandle.toRoute<EditCategoryRoute>().categoryId

    private val editingCategoryNameFlow = MutableStateFlow("")

    private val categoryFlow = observeCategoryUseCase
        .invoke(categoryId = categoryId)
        .filterNotNull()
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
                emoji = category.emoji,
                editingCategoryName = editingCategoryName,
            ),
        )
    }.stateIn(
        initialValue = FetchingCategoryUiState.Loading,
        started = SharingStarted.WhileSubscribed(500L),
        scope = viewModelScope,
    )

    fun removeCategory() {
        viewModelScope.launch {
            removeCategoryUseCase
                .invoke(categoryId = categoryId)
                .onSuccess {
                    _events.emit(EditCategoryEvent.RemovingFinished)
                }.onFailure {
                    // TODO: handle error case later
                }
        }
    }

    fun onEmojiPicked(emojiString: String) {
        viewModelScope.launch {
            updateCategoryEmojiUseCase.invoke(
                categoryId = categoryId,
                emoji = emojiString,
            )
        }
    }

    fun onRemoveEmoji() {
        viewModelScope.launch {
            removeCategoryEmojiUseCase.invoke(categoryId = categoryId)
        }
    }
}

sealed class FetchingCategoryUiState {
    data object Loading : FetchingCategoryUiState()

    data class Success(
        val editCategoryUiState: EditCategoryUiState,
    ) : FetchingCategoryUiState()
}

data class EditCategoryUiState(
    val categoryName: String,
    val emoji: String,
    val editingCategoryName: String,
)

sealed class EditCategoryEvent {
    data object RemovingFinished : EditCategoryEvent()
}
