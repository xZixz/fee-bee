package com.cardes.feebee.ui.spendingdetails

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cardes.feebee.ui.common.UiSetting

const val SPENDING_DETAILS_ROUTE = "spending_details_route"
const val SPENDING_ID_ARG = "spending_id_arg"

fun NavController.navigateToSpendingDetails(spendingId: Long) {
    navigate("${SPENDING_DETAILS_ROUTE}/$spendingId")
}

fun NavGraphBuilder.spendingDetails(onRemoveSpending: () -> Unit) {
    composable(
        route = "$SPENDING_DETAILS_ROUTE/{$SPENDING_ID_ARG}",
        arguments = listOf(
            navArgument(SPENDING_ID_ARG) { type = NavType.IntType },
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
        SpendingDetailsRoute(onSpendingRemoved = onRemoveSpending)
    }
}
