package com.cardes.editcategory.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.cardes.editcategory.EditCategoryRoute
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data class EditCategoryRoute(
    val categoryId: Long,
)

fun NavController.navigateToEditCategory(
    categoryId: Long,
    navOptions: NavOptionsBuilder.() -> Unit = {},
) = navigate(route = EditCategoryRoute(categoryId = categoryId)) {
    navOptions()
}

fun NavGraphBuilder.editCategory(onFinishRemovingCategory: () -> Unit) {
    composable<EditCategoryRoute> {
        EditCategoryRoute(
            onFinishRemovingCategory = onFinishRemovingCategory,
            editCategoryViewModel = koinViewModel(),
        )
    }
}
