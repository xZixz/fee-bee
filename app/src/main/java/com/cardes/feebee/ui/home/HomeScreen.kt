package com.cardes.feebee.ui.home

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
import com.cardes.analytics.AnalyticsRoute
import com.cardes.categories.CategoriesListRoute
import com.cardes.editcategory.EditCategoryRoute
import com.cardes.editspending.EditSpendingRoute
import com.cardes.feebee.navigation.BottomNavItem
import com.cardes.navigation.CategoriesDestination
import com.cardes.navigation.Navigator
import com.cardes.navigation.SpendingsDestination
import com.cardes.navigation.TopLevelDestination
import com.cardes.navigation.rememberNavigationState
import com.cardes.navigation.toEntries
import com.cardes.spendingdetail.SpendingDetailRoute
import com.cardes.spendings.SpendingsRoute
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun HomeRoute(modifier: Modifier = Modifier) {
    HomeScreen(modifier = modifier)
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val navigateState = rememberNavigationState(
        startRoute = TopLevelDestination.SpendingsRoute,
        topLevelRoutes = setOf(
            TopLevelDestination.SpendingsRoute,
            TopLevelDestination.AnalyticsRoute,
            TopLevelDestination.CategoriesRoute,
        ),
    )

    val navigator = remember(navigateState) { Navigator(navigateState) }

    Column {
        Box(modifier = modifier.weight(1.0f)) {
            NavDisplay(
                entries = navigateState.toEntries(
                    entryProvider = entryProvider {
                        entry<TopLevelDestination.SpendingsRoute> {
                            SpendingsRoute(
                                onCreateSpendingClick = { navigator.navigate(SpendingsDestination.EditSpending(0)) },
                                onSpendingClick = { spendingId ->
                                    navigator.navigate(SpendingsDestination.SpendingDetails(spendingId))
                                },
                            )
                        }
                        entry<TopLevelDestination.CategoriesRoute> {
                            CategoriesListRoute(
                                onCategoryClick = { categoryId ->
                                    navigator.navigate(CategoriesDestination.EditCategory(categoryId))
                                },
                            )
                        }
                        entry<TopLevelDestination.AnalyticsRoute> {
                            AnalyticsRoute()
                        }
                        entry<SpendingsDestination.SpendingDetails> { key ->
                            SpendingDetailRoute(
                                onEditClick = { spendingId -> navigator.navigate(SpendingsDestination.EditSpending(spendingId)) },
                                spendingDetailViewModel = koinViewModel(
                                    key = "${key.spendingId}",
                                    parameters = { parametersOf(key.spendingId) },
                                ),
                            )
                        }
                        entry<SpendingsDestination.EditSpending> { key ->
                            EditSpendingRoute(
                                navUp = { navigator.goBack() },
                                onSpendingRemoved = { navigator.goBack() },
                                editSpendingViewModel = koinViewModel(
                                    key = "${key.spendingId}",
                                    parameters = { parametersOf(key.spendingId) },
                                ),
                            )
                        }
                        entry<CategoriesDestination.EditCategory> { key ->
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
