package com.cardes.feebee.ui.spendingdetails

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.cardes.data.fake.Fake
import com.cardes.feebee.ui.editspending.spendingDateDisplayFormat

class SpendingDetailsUiStatePreviewParameter : PreviewParameterProvider<SpendingUiState.Success> {
    override val values: Sequence<SpendingUiState.Success>
        get() = sequenceOf(
            Fake.spendings.first().run {
                SpendingUiState.Success(
                    description = content,
                    cost = amount.toString(),
                    date = spendingDateDisplayFormat.format(time),
                    categories = Fake.categories,
                )
            },
        )
}
