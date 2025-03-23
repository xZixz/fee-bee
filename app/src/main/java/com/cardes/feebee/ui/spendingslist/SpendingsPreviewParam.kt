package com.cardes.feebee.ui.spendingslist

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.cardes.data.fake.Fake
import com.cardes.domain.base.MonthYear
import com.cardes.domain.entity.Spending
import java.util.SortedMap

class SpendingsPreviewParam : PreviewParameterProvider<SortedMap<MonthYear, List<Spending>>> {
    override val values: Sequence<SortedMap<MonthYear, List<Spending>>>
        get() = sequenceOf(Fake.spendingsData)
}
