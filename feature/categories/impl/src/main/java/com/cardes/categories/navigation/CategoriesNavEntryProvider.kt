package com.cardes.categories.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.cardes.categories.api.CategoriesNavKey
import com.cardes.categories.impl.CategoriesListRoute
import com.cardes.editcategory.api.EditCategoryNavKey
import com.cardes.navigation.Navigator

fun EntryProviderScope<NavKey>.categoriesEntry(navigator: Navigator) {
    entry<CategoriesNavKey> {
        CategoriesListRoute(
            onCategoryClick = { categoryId ->
                navigator.navigate(EditCategoryNavKey(categoryId = categoryId))
            },
        )
    }
}
