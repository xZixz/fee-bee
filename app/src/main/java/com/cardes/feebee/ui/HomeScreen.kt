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
import com.cardes.analytics.impl.navigation.analyticsEntry
import com.cardes.categories.navigation.categoriesEntry
import com.cardes.editcategory.navigation.editCategoryEntry
import com.cardes.editspending.navigation.editSpendingEntry
import com.cardes.feebee.navigation.BottomNavItem
import com.cardes.navigation.Navigator
import com.cardes.navigation.rememberNavigationState
import com.cardes.navigation.toEntries
import com.cardes.spendingdetail.navigation.spendingDetailEntry
import com.cardes.spendings.api.SpendingsNavKey
import com.cardes.spendings.navigation.spendingsEntry

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
                        spendingsEntry(navigator = navigator)
                        categoriesEntry(navigator = navigator)
                        analyticsEntry()
                        spendingDetailEntry(navigator = navigator)
                        editSpendingEntry(navigator = navigator)
                        editCategoryEntry(navigator = navigator)
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
