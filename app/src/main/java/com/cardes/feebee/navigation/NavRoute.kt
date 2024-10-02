package com.cardes.feebee.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.cardes.feebee.R
import com.cardes.feebee.ui.categorieslist.CATEGORIES_LIST_ROUTE
import com.cardes.feebee.ui.home.HOME_ROUTE
import com.cardes.feebee.ui.spendingslist.SPENDINGS_LIST_ROUTE
import kotlin.reflect.KProperty

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
        val SpendingsList = NavRouteName(SPENDINGS_LIST_ROUTE)
        val EditSpendingRoute = NavRouteName("$EDIT_SPENDING_ROUTE/{$SPENDING_ID_ARG}")
        val SpendingDetails = NavRouteName("$SPENDING_DETAILS_ROUTE/{$SPENDING_ID_ARG}")
        val CategoriesList = NavRouteName(CATEGORIES_LIST_ROUTE)
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

        data object SpendingsList : NavRoute(routeName = NavRouteName.SpendingsList)

        data object CategoriesList : NavRoute(routeName = NavRouteName.CategoriesList)

        data object SpendingDetails : NavRoute(routeName = NavRouteName.SpendingDetails)

        data object EditSpending : NavRoute(routeName = NavRouteName.EditSpendingRoute)

        data object EditCategory : NavRoute(routeName = NavRouteName.EditCategory)
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
