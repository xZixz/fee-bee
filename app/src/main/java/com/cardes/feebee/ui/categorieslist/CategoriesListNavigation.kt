package com.cardes.feebee.ui.categorieslist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cardes.feebee.navigation.BottomNavItem

fun NavGraphBuilder.categoriesList(onCategoryClick: (Long) -> Unit) {
    composable(BottomNavItem.CATEGORIES_LIST.route) {
        CategoriesListRoute(onCategoryClick = onCategoryClick)
    }
}
