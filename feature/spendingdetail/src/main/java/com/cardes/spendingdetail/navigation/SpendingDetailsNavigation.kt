package com.cardes.spendingdetail.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.cardes.spendingdetail.SpendingDetailRoute
import com.cardes.ui.shared.UiSetting
import kotlinx.serialization.Serializable

@Serializable
data class SpendingDetailRoute(
    val spendingId: Long,
)

fun NavController.navigateToSpendingDetail(
    spendingId: Long,
    navOptions: NavOptionsBuilder.() -> Unit = {},
) {
    navigate(route = SpendingDetailRoute(spendingId = spendingId)) {
        navOptions()
    }
}

fun NavGraphBuilder.spendingDetail(onEditClick: (Long) -> Unit) {
    composable<SpendingDetailRoute>(
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
    ) {
        SpendingDetailRoute(onEditClick = onEditClick)
    }
}
