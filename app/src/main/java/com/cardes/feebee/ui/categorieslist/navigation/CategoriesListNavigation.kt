package com.cardes.feebee.ui.categorieslist.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cardes.feebee.ui.categorieslist.CategoriesListRoute
import kotlinx.serialization.Serializable

@Serializable
object CategoriesRoute

fun NavGraphBuilder.categoriesList(onCategoryClick: (Long) -> Unit) {
    composable<CategoriesRoute> {
        CategoriesListRoute(onCategoryClick = onCategoryClick)
    }
}
