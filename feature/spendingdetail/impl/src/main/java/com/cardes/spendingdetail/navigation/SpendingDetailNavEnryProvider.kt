package com.cardes.spendingdetail.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.cardes.editspending.api.EditSpendingNavKey
import com.cardes.navigation.Navigator
import com.cardes.spendingdetail.api.SpendingDetailNavKey
import com.cardes.spendingdetail.impl.SpendingDetailRoute
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

fun EntryProviderScope<NavKey>.spendingDetailEntry(navigator: Navigator) {
    entry<SpendingDetailNavKey> { key ->
        SpendingDetailRoute(
            onEditClick = { navigator.navigate(EditSpendingNavKey(key.spendingId)) },
            spendingDetailViewModel = koinViewModel(
                key = "${key.spendingId}",
                parameters = { parametersOf(key.spendingId) },
            ),
        )
    }
}
