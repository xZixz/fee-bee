package com.cardes.feebee.ui.spendingslist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cardes.feebee.navigation.BottomNavItem

const val SPENDINGS_LIST_ROUTE = "spendings_list_route"

fun NavGraphBuilder.spendingsList(
    onCreateSpendingClick: () -> Unit,
    onSpendingClick: (Long) -> Unit,
) {
    composable(route = BottomNavItem.SPENDINGS_LIST.route) {
        SpendingsListRoute(
            onCreateSpendingClick = onCreateSpendingClick,
            onSpendingClick = onSpendingClick,
        )
    }
}
