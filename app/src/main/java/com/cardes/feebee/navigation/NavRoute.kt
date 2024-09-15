package com.cardes.feebee.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.cardes.feebee.R
import com.cardes.feebee.ui.categorieslist.CATEGORIES_LIST_ROUTE
import com.cardes.feebee.ui.editspending.EDIT_SPENDING_ROUTE
import com.cardes.feebee.ui.home.HOME_ROUTE
import com.cardes.feebee.ui.spendingdetails.SPENDING_DETAILS_ROUTE
import com.cardes.feebee.ui.spendingdetails.SPENDING_ID_ARG
import com.cardes.feebee.ui.spendingslist.SPENDINGS_LIST_ROUTE
import kotlin.reflect.KProperty

const val MAIN_ROUTE = "main_route"

@JvmInline
value class NavRouteName(private val name: String) {
    companion object {
        val MainRoute = NavRouteName(MAIN_ROUTE)
        val HomeRoute = NavRouteName(HOME_ROUTE)
        val SpendingsList = NavRouteName(SPENDINGS_LIST_ROUTE)
        val EditSpendingRoute = NavRouteName("$EDIT_SPENDING_ROUTE/{$SPENDING_ID_ARG}")
        val SpendingDetails = NavRouteName("$SPENDING_DETAILS_ROUTE/{$SPENDING_ID_ARG}")
        val CategoriesList = NavRouteName(CATEGORIES_LIST_ROUTE)
    }

    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): String = name
}

sealed class NavRoute(routeName: NavRouteName) {
    val name: String by routeName
}

object NavRoutes {
    data object Main : NavRoute(routeName = NavRouteName.MainRoute) {
        data object Home : NavRoute(routeName = NavRouteName.HomeRoute)

        data object SpendingsList : NavRoute(routeName = NavRouteName.SpendingsList)

        data object CategoriesList : NavRoute(routeName = NavRouteName.CategoriesList)

        data object SpendingDetails : NavRoute(routeName = NavRouteName.SpendingDetails)

        data object EditSpending : NavRoute(routeName = NavRouteName.EditSpendingRoute)
    }
}

enum class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val labelResourceId: Int,
) {
    SPENDINGS_LIST(
        route = NavRoutes.Main.SpendingsList.name,
        icon = Icons.Default.Home,
        labelResourceId = R.string.spendings,
    ),

    CATEGORIES_LIST(
        route = NavRoutes.Main.CategoriesList.name,
        icon = Icons.Default.Settings,
        labelResourceId = R.string.categories,
    ),
}
