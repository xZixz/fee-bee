package com.cardes.categories

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cardes.designsystem.component.BasePage
import com.cardes.designsystem.theme.FeeBeeTheme
import com.cardes.domain.entity.Category
import com.cardes.ui.CreateCategoryDialog
import com.cardes.ui.R as uiR

@Composable
fun CategoriesListRoute(
    onCategoryClick: (Long) -> Unit,
    categoriesListViewModel: CategoriesListViewModel = hiltViewModel(),
) {
    val categories by categoriesListViewModel.categories.collectAsStateWithLifecycle()
    val showAddCategoryDialog by categoriesListViewModel.showDatePickerDialog.collectAsStateWithLifecycle()
    CategoriesListScreen(
        categories = categories,
        onCreateCategoryClick = categoriesListViewModel::onCreateCategoryClick,
        onAddCategoryDismiss = categoriesListViewModel::dismissNewCategoryDialog,
        onAddCategory = categoriesListViewModel::onCreateCategory,
        showAddCategoryDialog = showAddCategoryDialog,
        onCategoryClick = onCategoryClick,
    )
}

@Composable
fun CategoriesListScreen(
    categories: List<Category>,
    onCreateCategoryClick: () -> Unit,
    onAddCategoryDismiss: () -> Unit,
    onAddCategory: (String) -> Unit,
    showAddCategoryDialog: Boolean,
    onCategoryClick: (Long) -> Unit,
) {
    BasePage(title = stringResource(id = uiR.string.categories)) {
        if (showAddCategoryDialog) {
            CreateCategoryDialog(
                onDismiss = { onAddCategoryDismiss() },
                onConfirm = { categoryName -> onAddCategory(categoryName) },
            )
        }

        LazyVerticalGrid(
            modifier = Modifier.padding(top = 15.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(
                items = categories,
                key = { category -> category.id },
            ) { category ->
                CategoryItem(
                    category = category,
                    onCategoryClick = onCategoryClick,
                )
            }
            item {
                AddCategoryItem(
                    onItemClick = onCreateCategoryClick,
                )
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
                onAddCategoryDismiss = {},
                onAddCategory = {},
                showAddCategoryDialog = false,
                onCategoryClick = {},
            )
        }
    }
}
