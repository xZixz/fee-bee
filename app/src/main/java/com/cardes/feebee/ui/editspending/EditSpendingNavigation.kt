package com.cardes.feebee.ui.editspending

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cardes.feebee.ui.spendingdetails.SPENDING_ID_ARG

const val EDIT_SPENDING_ROUTE = "edit_spending_route"

fun NavController.navigateToEditSpendingRoute(spendingId: Long) {
    navigate("$EDIT_SPENDING_ROUTE/$spendingId")
}

fun NavGraphBuilder.editSpending(
    onRemoveSpending: () -> Unit,
    onDoneUpdating: () -> Unit,
) {
    composable(
        route = "$EDIT_SPENDING_ROUTE/{$SPENDING_ID_ARG}",
        arguments = listOf(
            navArgument(SPENDING_ID_ARG) { type = NavType.LongType },
        ),
    ) {
        EditSpendingRoute(
            onRemoveSpending = onRemoveSpending,
            onDoneUpdating = onDoneUpdating,
        )
    }
}
