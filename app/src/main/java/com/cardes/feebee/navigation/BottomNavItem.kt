package com.cardes.feebee.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.cardes.navigation.TopLevelDestination
import com.cardes.analytics.impl.R as analyticsR
import com.cardes.categories.impl.R as categoriesR
import com.cardes.spendings.impl.R as spendingsR

enum class BottomNavItem(
    val route: TopLevelDestination,
    val icon: ImageVector,
    val labelResourceId: Int,
) {
    SPENDINGS_LIST(
        route = TopLevelDestination.SpendingsRoute,
        icon = Icons.Default.Home,
        labelResourceId = spendingsR.string.spendings,
    ),
    ANALYTICS(
        route = TopLevelDestination.AnalyticsRoute,
        icon = Icons.Default.DataUsage,
        labelResourceId = analyticsR.string.analytics,
    ),
    CATEGORIES_LIST(
        route = TopLevelDestination.CategoriesRoute,
        icon = Icons.Default.Settings,
        labelResourceId = categoriesR.string.categories,
    ),
}
