package com.cardes.editspending.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.cardes.editspending.EditSpendingRoute
import com.cardes.ui.shared.UiSetting
import kotlinx.serialization.Serializable

@Serializable
data class EditSpendingRoute(
    val spendingId: Long,
)

fun NavController.navigateToEditSpending(
    spendingId: Long,
    navOptions: NavOptionsBuilder.() -> Unit = {},
) = navigate(route = EditSpendingRoute(spendingId = spendingId)) {
    navOptions()
}

fun NavController.navigateToCreateSpending() {
    navigate(route = EditSpendingRoute(spendingId = 0))
}

fun NavGraphBuilder.editSpending(
    onNavUp: () -> Unit,
    onSpendingRemoved: () -> Unit,
) {
    composable<EditSpendingRoute>(
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
