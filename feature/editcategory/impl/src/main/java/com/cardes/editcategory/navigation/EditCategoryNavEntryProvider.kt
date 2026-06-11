package com.cardes.editcategory.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.cardes.editcategory.api.EditCategoryNavKey
import com.cardes.editcategory.impl.EditCategoryRoute
import com.cardes.navigation.Navigator
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

fun EntryProviderScope<NavKey>.editCategoryEntry(navigator: Navigator) {
    entry<EditCategoryNavKey> { key ->
        EditCategoryRoute(
            onFinishRemovingCategory = { navigator.goBack() },
            editCategoryViewModel = koinViewModel(
                key = "${key.categoryId}",
                parameters = { parametersOf(key.categoryId) },
            ),
        )
    }
}
