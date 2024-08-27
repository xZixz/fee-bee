package com.cardes.feebee.ui.createspending

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.cardes.data.fake.Fake
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.US)

class CreateSpendingUiStatePreviewParameter : PreviewParameterProvider<CreateSpendingUiState> {
    override val values: Sequence<CreateSpendingUiState> = sequenceOf(
        CreateSpendingUiState(
            description = "Candy",
            amount = "10",
            date = dateFormat.format(Calendar.getInstance().time),
            categories = Fake.categories,
            selectedCategoryIds = listOf(),
        ),
    )
}
