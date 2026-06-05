package com.cardes.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class TopLevelDestination : NavKey {
    @Serializable
    data object SpendingsRoute : TopLevelDestination()

    @Serializable
    data object AnalyticsRoute : TopLevelDestination()

    @Serializable
    data object CategoriesRoute : TopLevelDestination()
}
