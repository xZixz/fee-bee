package com.cardes.categories.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.cardes.categories.CategoriesListRoute
import kotlinx.serialization.Serializable

@Serializable
object CategoriesRoute

fun NavController.toCategories(navOptions: NavOptions) = navigate(route = CategoriesRoute, navOptions = navOptions)

fun NavGraphBuilder.categoriesList(onCategoryClick: (Long) -> Unit) {
    composable<CategoriesRoute> {
        CategoriesListRoute(onCategoryClick = onCategoryClick)
    }
}
