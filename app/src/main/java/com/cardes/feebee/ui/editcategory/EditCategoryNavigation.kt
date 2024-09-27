package com.cardes.feebee.ui.editcategory

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cardes.feebee.navigation.CATEGORY_ID_ARG
import com.cardes.feebee.navigation.EDIT_CATEGORY_ROUTE
import com.cardes.feebee.navigation.NavRoutes

fun NavController.navigateToEditCategory(categoryId: Long) {
    navigate("$EDIT_CATEGORY_ROUTE/$categoryId")
}

fun NavGraphBuilder.editCategory(onFinishRemovingCategory: () -> Unit) {
    composable(
        route = NavRoutes.Main.EditCategory.name,
        arguments = listOf(
            navArgument(CATEGORY_ID_ARG) { type = NavType.LongType },
        ),
    ) {
        EditCategoryRoute(
            onFinishRemovingCategory = onFinishRemovingCategory,
        )
    }
}
