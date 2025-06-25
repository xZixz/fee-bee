package com.cardes.analytics.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.cardes.analytics.AnalyticsRoute
import kotlinx.serialization.Serializable

@Serializable
object AnalyticsRoute

fun NavController.toAnalytics(navOptions: NavOptions) = navigate(route = AnalyticsRoute, navOptions = navOptions)

fun NavGraphBuilder.analytics() {
    composable<AnalyticsRoute> {
        AnalyticsRoute()
    }
}
