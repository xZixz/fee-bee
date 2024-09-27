package com.cardes.feebee.ui.editspending

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cardes.feebee.navigation.EDIT_SPENDING_ROUTE
import com.cardes.feebee.navigation.NavRoutes
import com.cardes.feebee.navigation.SPENDING_ID_ARG
import com.cardes.feebee.ui.common.UiSetting

fun NavController.navigateToEditSpending(spendingId: Long) {
    navigate("${EDIT_SPENDING_ROUTE}/$spendingId")
}

fun NavController.navigateToCreateSpending() {
    navigate("${EDIT_SPENDING_ROUTE}/0")
}

fun NavGraphBuilder.editSpending(
    onNavUp: () -> Unit,
    onSpendingRemoved: () -> Unit,
) {
    composable(
        route = NavRoutes.Main.EditSpending.name,
        arguments = listOf(
            navArgument(SPENDING_ID_ARG) { type = NavType.LongType },
        ),
        exitTransition = {
            return@composable fadeOut(tween(UiSetting.SCREEN_TRANSITION_DURATION))
        },
        enterTransition = {
            return@composable slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(UiSetting.SCREEN_TRANSITION_DURATION),
            )
        },
    ) {
        EditSpendingRoute(
            onNavUp = onNavUp,
            onSpendingRemoved = onSpendingRemoved,
        )
    }
}
