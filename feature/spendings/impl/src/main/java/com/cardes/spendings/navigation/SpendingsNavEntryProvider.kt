package com.cardes.spendings.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.cardes.editspending.api.EditSpendingNavKey
import com.cardes.navigation.Navigator
import com.cardes.spendingdetail.api.SpendingDetailNavKey
import com.cardes.spendings.api.SpendingsNavKey
import com.cardes.spendings.impl.SpendingsRoute

fun EntryProviderScope<NavKey>.spendingsEntry(navigator: Navigator) {
    entry<SpendingsNavKey> {
        SpendingsRoute(
            onSpendingClick = { spendingId ->
                navigator.navigate(SpendingDetailNavKey(spendingId))
            },
            onCreateSpendingClick = {
                navigator.navigate(EditSpendingNavKey(0))
            },
        )
    }
}
