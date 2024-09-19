package com.cardes.feebee.ui.categorieslist

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cardes.data.fake.Fake
import com.cardes.domain.entity.Category
import com.cardes.feebee.R
import com.cardes.feebee.ui.common.BasePage
import com.cardes.feebee.ui.editspending.CreateCategoryDialog
import com.cardes.feebee.ui.theme.FeeBeeTheme

@Composable
fun CategoriesListRoute(categoriesListViewModel: CategoriesListViewModel = hiltViewModel()) {
    val categories by categoriesListViewModel.categories.collectAsStateWithLifecycle()
    val showAddCategoryDialog by categoriesListViewModel.showDatePickerDialog.collectAsStateWithLifecycle()
    CategoriesListScreen(
        categories = categories,
        onCreateCategoryClick = categoriesListViewModel::onCreateCategoryClick,
        onAddCategory = categoriesListViewModel::onCreateCategory,
        onAddCategoryDismiss = categoriesListViewModel::dismissNewCategoryDialog,
        showAddCategoryDialog = showAddCategoryDialog,
    )
}

@Composable
fun CategoriesListScreen(
    categories: List<Category>,
    onCreateCategoryClick: () -> Unit,
    onAddCategoryDismiss: () -> Unit,
    onAddCategory: (String) -> Unit,
    showAddCategoryDialog: Boolean,
) {
    BasePage(
        title = stringResource(id = R.string.categories),
        titleAction = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add new category",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 5.dp)
                    .clickable(onClick = onCreateCategoryClick),
            )
        },
    ) {
        if (showAddCategoryDialog) {
            CreateCategoryDialog(
                onDismiss = { onAddCategoryDismiss() },
                onConfirm = { categoryName -> onAddCategory(categoryName) },
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
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
            CategoriesListScreen(
                categories = categories,
                onCreateCategoryClick = {},
                onAddCategory = {},
                onAddCategoryDismiss = {},
                showAddCategoryDialog = false,
            )
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
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = category.name,
            )
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    widthDp = 250,
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    widthDp = 250,
)
@Composable
private fun CategoryItemPreview() {
    FeeBeeTheme {
        CategoryItem(category = Fake.categories.first())
    }
}
