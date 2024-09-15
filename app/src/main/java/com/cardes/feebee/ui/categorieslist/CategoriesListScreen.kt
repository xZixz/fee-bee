package com.cardes.feebee.ui.categorieslist

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cardes.domain.entity.Category
import com.cardes.feebee.R
import com.cardes.feebee.ui.common.BasePage
import com.cardes.feebee.ui.theme.FeeBeeTheme

@Composable
fun CategoriesListRoute(categoriesListViewModel: CategoriesListViewModel = hiltViewModel()) {
    val categories by categoriesListViewModel.categories.collectAsState()
    CategoriesListScreen(categories = categories)
}

@Composable
fun CategoriesListScreen(categories: List<Category>) {
    BasePage(title = stringResource(id = R.string.categories)) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(categories) { category ->
                CategoryItem(category = category)
            }
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    widthDp = 250,
    heightDp = 400,
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    widthDp = 250,
    heightDp = 400,
)
@Composable
private fun CategoriesListPreview(
    @PreviewParameter(CategoriesListPreviewParam::class) categories: List<Category>,
) {
    FeeBeeTheme {
        Surface {
            CategoriesListScreen(categories = categories)
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RectangleShape,
        modifier = modifier,
        elevation = CardDefaults.cardElevation(),
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = category.name,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}
