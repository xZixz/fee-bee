package com.cardes.feebee.ui.categorieslist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cardes.feebee.navigation.BottomNavItem

const val CATEGORIES_LIST_ROUTE = "categories_list_route"

fun NavGraphBuilder.categoriesList() {
    composable(BottomNavItem.CATEGORIES_LIST.route) {
        CategoriesListRoute()
    }
}
