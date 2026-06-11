package com.cardes.editspending.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.cardes.editspending.api.EditSpendingNavKey
import com.cardes.editspending.impl.EditSpendingRoute
import com.cardes.navigation.Navigator
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

fun EntryProviderScope<NavKey>.editSpendingEntry(navigator: Navigator) {
    entry<EditSpendingNavKey> { key ->
        EditSpendingRoute(
            navUp = { navigator.goBack() },
            onSpendingRemoved = { navigator.goBackToTopRoute() },
            editSpendingViewModel = koinViewModel(
                key = "${key.spendingId}",
                parameters = { parametersOf(key.spendingId) },
            ),
        )
    }
}
