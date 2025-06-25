package com.cardes.feebee.ui.analytics.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cardes.feebee.ui.analytics.AnalyticsRoute
import kotlinx.serialization.Serializable

@Serializable
object AnalyticsRoute

fun NavGraphBuilder.analytics() {
    composable<AnalyticsRoute> {
        AnalyticsRoute()
    }
}
