package com.cardes.feebee.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.cardes.analytics.navigation.AnalyticsRoute
import com.cardes.categories.navigation.CategoriesRoute
import com.cardes.feebee.R
import com.cardes.feebee.ui.home.navigation.HOME_ROUTE
import com.cardes.spendings.navigation.SpendingsRoute
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import com.cardes.categories.R as categoriesR
import com.cardes.spendings.R as spendingsR

const val MAIN_ROUTE = "main_route"
const val EDIT_CATEGORY_ROUTE = "edit_category_route"
const val CATEGORY_ID_ARG = "category_id_arg"
const val SPENDING_DETAILS_ROUTE = "spending_details_route"
const val SPENDING_ID_ARG = "spending_id_arg"
const val EDIT_SPENDING_ROUTE = "edit_spending_route"

@JvmInline
value class NavRouteName(
    private val name: String,
) {
    companion object {
        val MainRoute = NavRouteName(MAIN_ROUTE)
        val HomeRoute = NavRouteName(HOME_ROUTE)
        val EditSpendingRoute = NavRouteName("$EDIT_SPENDING_ROUTE/{$SPENDING_ID_ARG}")
        val SpendingDetails = NavRouteName("$SPENDING_DETAILS_ROUTE/{$SPENDING_ID_ARG}")
        val EditCategory = NavRouteName("$EDIT_CATEGORY_ROUTE/{$CATEGORY_ID_ARG}")
    }

    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): String = name
}

sealed class NavRoute(
    routeName: NavRouteName,
) {
    val name: String by routeName
}

object NavRoutes {
    data object Main : NavRoute(routeName = NavRouteName.MainRoute) {
        data object Home : NavRoute(routeName = NavRouteName.HomeRoute)

        data object SpendingDetails : NavRoute(routeName = NavRouteName.SpendingDetails)

        data object EditSpending : NavRoute(routeName = NavRouteName.EditSpendingRoute)

        data object EditCategory : NavRoute(routeName = NavRouteName.EditCategory)
    }
}

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
        labelResourceId = R.string.analytics,
    ),
    CATEGORIES_LIST(
        route = CategoriesRoute::class,
        icon = Icons.Default.Settings,
        labelResourceId = categoriesR.string.categories,
    ),
}
