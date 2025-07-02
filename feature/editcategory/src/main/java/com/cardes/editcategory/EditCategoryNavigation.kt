package com.cardes.editcategory

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

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
        )
    }
}
