package com.cardes.feebee.ui.createspending

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cardes.feebee.ui.common.UiSetting

const val CREATE_SPENDING_ROUTE = "create_spending_route"

fun NavController.navigateToCreateSpending() {
    navigate(CREATE_SPENDING_ROUTE)
}

fun NavGraphBuilder.createSpending(onNavUp: () -> Unit) {
    composable(
        route = CREATE_SPENDING_ROUTE,
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
        CreateSpendingRoute(onNavUp)
    }
}
