package com.cardes.feebee.ui.spendingdetails

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cardes.feebee.navigation.NavRoutes
import com.cardes.feebee.navigation.SPENDING_DETAILS_ROUTE
import com.cardes.feebee.navigation.SPENDING_ID_ARG
import com.cardes.feebee.ui.common.UiSetting

fun NavController.navigateToSpendingDetails(spendingId: Long) {
    navigate("${SPENDING_DETAILS_ROUTE}/$spendingId")
}

fun NavGraphBuilder.spendingDetails(onEditClick: (Long) -> Unit) {
    composable(
        route = NavRoutes.Main.SpendingDetails.name,
        arguments = listOf(
            navArgument(SPENDING_ID_ARG) { type = NavType.LongType },
        ),
        exitTransition = {
            return@composable fadeOut(tween(UiSetting.SCREEN_TRANSITION_DURATION))
        },
        popEnterTransition = {
            return@composable slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(UiSetting.SCREEN_TRANSITION_DURATION),
            )
        },
        enterTransition = {
            return@composable slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(UiSetting.SCREEN_TRANSITION_DURATION),
            )
        },
    ) { navBackStackEntry ->
        val spendingId = navBackStackEntry.arguments?.getLong(SPENDING_ID_ARG) ?: 0L
        SpendingDetailsRoute(onEditClick = { onEditClick(spendingId) })
    }
}
