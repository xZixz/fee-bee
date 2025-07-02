package com.cardes.feebee.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.cardes.analytics.navigation.AnalyticsRoute
import com.cardes.categories.navigation.CategoriesRoute
import com.cardes.spendings.navigation.SpendingsRoute
import kotlin.reflect.KClass
import com.cardes.analytics.R as analyticsR
import com.cardes.categories.R as categoriesR
import com.cardes.spendings.R as spendingsR

enum class BottomNavItem(
    val route: KClass<*>,
    val icon: ImageVector,
    val labelResourceId: Int,
) {
    SPENDINGS_LIST(
        route = SpendingsRoute::class,
        icon = Icons.Default.Home,
        labelResourceId = spendingsR.string.spendings,
    ),
    ANALYTICS(
        route = AnalyticsRoute::class,
        icon = Icons.Default.DataUsage,
        labelResourceId = analyticsR.string.analytics,
    ),
    CATEGORIES_LIST(
        route = CategoriesRoute::class,
        icon = Icons.Default.Settings,
        labelResourceId = categoriesR.string.categories,
    ),
}
