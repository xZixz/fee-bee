package com.cardes.feebee.navigation

import com.cardes.feebee.ui.createspending.CREATE_SPENDING_ROUTE
import com.cardes.feebee.ui.spendingslist.SPENDINGS_LIST_ROUTE
import kotlin.reflect.KProperty

const val MAIN_ROUTE = "main_route"

@JvmInline
value class NavRouteName(private val name: String) {
    companion object {
        val MainRoute = NavRouteName(MAIN_ROUTE)
        val SpendingsList = NavRouteName(SPENDINGS_LIST_ROUTE)
        val CreateSpendingRoute = NavRouteName(CREATE_SPENDING_ROUTE)
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
        data object SpendingsList : NavRoute(routeName = NavRouteName.SpendingsList)

        data object CreateSpending : NavRoute(routeName = NavRouteName.CreateSpendingRoute)
    }
}
