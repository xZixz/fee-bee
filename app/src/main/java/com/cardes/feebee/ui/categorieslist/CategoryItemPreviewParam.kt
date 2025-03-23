package com.cardes.feebee.ui.categorieslist

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.cardes.data.fake.Fake
import com.cardes.domain.entity.Category

class CategoryItemPreviewParam : PreviewParameterProvider<Category> {
    override val values: Sequence<Category>
        get() = sequenceOf(
            Fake.categories.first(),
            Fake.categories[3],
        )
}
