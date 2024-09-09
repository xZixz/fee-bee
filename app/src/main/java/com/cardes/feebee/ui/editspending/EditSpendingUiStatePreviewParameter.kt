package com.cardes.feebee.ui.editspending

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.cardes.data.fake.Fake
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.US)

object EditSpendingUiStatePreviewParameter : PreviewParameterProvider<EditSpendingPreviewParam> {
    private val createSpendingUiState: EditSpendingUiState = EditSpendingUiState(
        description = "Candy",
        amount = "10",
        date = dateFormat.format(Calendar.getInstance().time),
        categories = Fake.categories,
        selectedCategoryIds = listOf(1),
    )

    private val editSpendingUiState: EditSpendingUiState = EditSpendingUiState(
        description = "",
        amount = "",
        date = dateFormat.format(Calendar.getInstance().time),
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
