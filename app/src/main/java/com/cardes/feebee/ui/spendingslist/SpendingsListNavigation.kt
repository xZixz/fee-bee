package com.cardes.feebee.ui.spendingslist

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cardes.feebee.ui.common.UiSetting

const val SPENDINGS_LIST_ROUTE = "spendings_list_route"

fun NavGraphBuilder.spendingsList(
    onCreateSpendingClick: () -> Unit,
    onSpendingClick: (Long) -> Unit,
) {
    composable(
        route = SPENDINGS_LIST_ROUTE,
        exitTransition = {
            return@composable slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(UiSetting.SCREEN_TRANSITION_DURATION),
            )
        },
        popEnterTransition = {
            return@composable slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(UiSetting.SCREEN_TRANSITION_DURATION),
            )
        },
    ) {
        SpendingsListRoute(
            onCreateSpendingClick = onCreateSpendingClick,
            onSpendingClick = onSpendingClick,
        )
    }
}
