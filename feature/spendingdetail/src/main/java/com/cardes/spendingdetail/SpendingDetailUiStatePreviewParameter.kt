package com.cardes.spendingdetail

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.cardes.data.fake.Fake
import com.cardes.designsystem.common.spendingDateDisplayFormat

class SpendingDetailUiStatePreviewParameter : PreviewParameterProvider<SpendingUiState.Success> {
    override val values: Sequence<SpendingUiState.Success>
        get() = sequenceOf(
            Fake.spendings.first().run {
                SpendingUiState.Success(
                    spendingId = 1,
                    description = content,
                    cost = amount.toString(),
                    date = spendingDateDisplayFormat.format(time),
                    categories = Fake.categories,
                )
            },
        )
}
