package com.cardes.spendings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cardes.spendings.SpendingsRoute
import kotlinx.serialization.Serializable

@Serializable
object SpendingsRoute

fun NavGraphBuilder.spendingsList(
    onCreateSpendingClick: () -> Unit,
    onSpendingClick: (Long) -> Unit,
) {
    composable<SpendingsRoute> {
        SpendingsRoute(
            onCreateSpendingClick = onCreateSpendingClick,
            onSpendingClick = onSpendingClick,
        )
    }
}
