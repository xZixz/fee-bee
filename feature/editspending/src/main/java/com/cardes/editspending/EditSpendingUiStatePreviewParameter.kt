package com.cardes.editspending

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.cardes.data.fake.Fake
import java.util.Calendar

class EditSpendingUiStatePreviewParameter : PreviewParameterProvider<EditSpendingPreviewParam> {
    private val createSpendingUiState: EditSpendingUiState = EditSpendingUiState(
        description = "Candy",
        amount = "10",
        date = Calendar.getInstance().timeInMillis,
        categories = Fake.categories,
        selectedCategoryIds = listOf(1),
    )

    private val editSpendingUiState: EditSpendingUiState = EditSpendingUiState(
        description = "",
        amount = "",
        date = Calendar.getInstance().timeInMillis,
        categories = Fake.categories,
        selectedCategoryIds = listOf(),
    )

    override val values: Sequence<EditSpendingPreviewParam> = sequenceOf(
        EditSpendingPreviewParam(
            createSpendingUiState,
            editMode = EditMode.NEW,
        ),
        EditSpendingPreviewParam(
            editSpendingUiState,
            editMode = EditMode.EDIT,
        ),
    )
}

data class EditSpendingPreviewParam(
    val editSpendingUiState: EditSpendingUiState,
    val editMode: EditMode,
)
