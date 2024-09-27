package com.cardes.feebee.ui.home

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cardes.feebee.ui.common.UiSetting

const val HOME_ROUTE = "home_route"

fun NavGraphBuilder.home(
    onCreateSpendingClick: () -> Unit,
    onSpendingClick: (Long) -> Unit,
    onCategoryClick: (Long) -> Unit,
) {
    composable(
        route = HOME_ROUTE,
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
        HomeRoute(
            onCreateSpendingClick = onCreateSpendingClick,
            onSpendingClick = onSpendingClick,
            onCategoryClick = onCategoryClick,
        )
    }
}
