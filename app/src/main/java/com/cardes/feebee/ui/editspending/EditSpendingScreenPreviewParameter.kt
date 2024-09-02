package com.cardes.feebee.ui.editspending

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.cardes.data.fake.Fake
import com.cardes.feebee.ui.createspending.spendingDateDisplayFormat

class EditSpendingScreenPreviewParameter : PreviewParameterProvider<EditSpendingScreenUiState> {
    override val values: Sequence<EditSpendingScreenUiState>
        get() = sequenceOf(
            with(Fake.spendings.first()) {
                EditSpendingScreenUiState.Success(
                    EditedSpendingUiState(
                        cost = amount.toString(),
                        description = content,
                        date = spendingDateDisplayFormat.format(time),
                    ),
                )
            },
        )
}
