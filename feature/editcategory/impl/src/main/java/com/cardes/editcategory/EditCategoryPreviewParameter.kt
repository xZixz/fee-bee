package com.cardes.editcategory

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.cardes.data.fake.Fake

class EditCategoryPreviewParameter : PreviewParameterProvider<FetchingCategoryUiState> {
    override val values: Sequence<FetchingCategoryUiState> = sequenceOf(
        FetchingCategoryUiState.Success(
            editCategoryUiState = EditCategoryUiState(
                categoryName = Fake.categories.first().name,
                editingCategoryName = Fake.categories.first().name,
                emoji = "🍔",
            ),
        ),
    )
}
