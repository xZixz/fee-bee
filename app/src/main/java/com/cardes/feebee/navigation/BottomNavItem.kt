package com.cardes.feebee.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.cardes.analytics.api.AnalyticsNavKey
import com.cardes.categories.api.CategoriesNavKey
import com.cardes.spendings.api.SpendingsNavKey
import com.cardes.analytics.impl.R as analyticsR
import com.cardes.categories.impl.R as categoriesR
import com.cardes.spendings.impl.R as spendingsR

enum class BottomNavItem(
    val route: NavKey,
    val icon: ImageVector,
    val labelResourceId: Int,
) {
    SPENDINGS_LIST(
        route = SpendingsNavKey,
        icon = Icons.Default.Home,
        labelResourceId = spendingsR.string.spendings,
    ),
    ANALYTICS(
        route = AnalyticsNavKey,
        icon = Icons.Default.DataUsage,
        labelResourceId = analyticsR.string.analytics,
    ),
    CATEGORIES_LIST(
        route = CategoriesNavKey,
        icon = Icons.Default.Settings,
        labelResourceId = categoriesR.string.categories,
    ),
}
