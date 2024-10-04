package com.cardes.feebee.ui.analytics

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cardes.feebee.navigation.BottomNavItem

fun NavGraphBuilder.analytics() {
    composable(BottomNavItem.ANALYTICS.route) {
        AnalyticsRoute()
    }
}
