package com.cardes.categories

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.cardes.data.fake.Fake
import com.cardes.domain.entity.Category

class CategoriesListPreviewParam : PreviewParameterProvider<List<Category>> {
    override val values: Sequence<List<Category>> = sequenceOf(Fake.categories)
}
