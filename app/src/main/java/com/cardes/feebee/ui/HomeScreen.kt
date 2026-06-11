package com.cardes.feebee.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.cardes.analytics.api.AnalyticsNavKey
import com.cardes.analytics.impl.AnalyticsRoute
import com.cardes.categories.api.CategoriesNavKey
import com.cardes.categories.impl.CategoriesListRoute
import com.cardes.editcategory.api.EditCategoryNavKey
import com.cardes.editcategory.impl.EditCategoryRoute
import com.cardes.editspending.api.EditSpendingNavKey
import com.cardes.editspending.impl.EditSpendingRoute
import com.cardes.feebee.navigation.BottomNavItem
import com.cardes.navigation.Navigator
import com.cardes.navigation.rememberNavigationState
import com.cardes.navigation.toEntries
import com.cardes.spendingdetail.api.SpendingDetailNavKey
import com.cardes.spendingdetail.impl.SpendingDetailRoute
import com.cardes.spendings.api.SpendingsNavKey
import com.cardes.spendings.impl.SpendingsRoute
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun HomeRoute(modifier: Modifier = Modifier) {
    HomeScreen(modifier = modifier)
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val navigateState = rememberNavigationState(
        startRoute = SpendingsNavKey,
        topLevelRoutes = BottomNavItem.entries.map { it.route }.toSet(),
    )

    val navigator = remember(navigateState) { Navigator(navigateState) }

    Column {
        Box(modifier = modifier.weight(1.0f)) {
            NavDisplay(
                entries = navigateState.toEntries(
                    entryProvider = entryProvider {
                        entry<SpendingsNavKey> {
                            SpendingsRoute(
                                onCreateSpendingClick = {
                                    navigator.navigate(EditSpendingNavKey(0))
                                },
                                onSpendingClick = { spendingId ->
                                    navigator.navigate(SpendingDetailNavKey(spendingId))
                                },
                            )
                        }
                        entry<CategoriesNavKey> {
                            CategoriesListRoute(
                                onCategoryClick = { categoryId ->
                                    navigator.navigate(EditCategoryNavKey(categoryId))
                                },
                            )
                        }
                        entry<AnalyticsNavKey> {
                            AnalyticsRoute()
                        }
                        entry<SpendingDetailNavKey> { key ->
                            SpendingDetailRoute(
                                onEditClick = { spendingId ->
                                    navigator.navigate(EditSpendingNavKey(spendingId))
                                },
                                spendingDetailViewModel = koinViewModel(
                                    key = "${key.spendingId}",
                                    parameters = { parametersOf(key.spendingId) },
                                ),
                            )
                        }
                        entry<EditSpendingNavKey> { key ->
                            EditSpendingRoute(
                                navUp = { navigator.goBack() },
                                onSpendingRemoved = { navigator.goBackToTopRoute() },
                                editSpendingViewModel = koinViewModel(
                                    key = "${key.spendingId}",
                                    parameters = { parametersOf(key.spendingId) },
                                ),
                            )
                        }
                        entry<EditCategoryNavKey> { key ->
                            EditCategoryRoute(
                                onFinishRemovingCategory = { navigator.goBack() },
                                editCategoryViewModel = koinViewModel(
                                    key = "${key.categoryId}",
                                    parameters = { parametersOf(key.categoryId) },
                                ),
                            )
                        }
                    },
                ),
                onBack = { navigator.goBack() },
            )
        }
        BottomNavigationBar(navigator = navigator)
    }
}

@Composable
fun BottomNavigationBar(
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        BottomNavItem.entries.toTypedArray().forEach { navItem ->
            val route = navItem.route
            NavigationBarItem(
                selected = navigator.state.topLevelRoute == navItem.route,
                onClick = { navigator.navigate(route) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = stringResource(navItem.labelResourceId),
                    )
                },
                label = { Text(text = stringResource(id = navItem.labelResourceId)) },
            )
        }
    }
}
