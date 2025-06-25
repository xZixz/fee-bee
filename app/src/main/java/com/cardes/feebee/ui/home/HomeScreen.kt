package com.cardes.feebee.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cardes.feebee.navigation.BottomNavItem
import com.cardes.feebee.ui.analytics.navigation.analytics
import com.cardes.feebee.ui.categorieslist.navigation.categoriesList
import com.cardes.spendings.navigation.spendingsList

@Composable
fun HomeRoute(
    onCreateSpendingClick: () -> Unit,
    onSpendingClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    onCategoryClick: (Long) -> Unit,
) {
    HomeScreen(
        onCreateSpendingClick = onCreateSpendingClick,
        onSpendingClick = onSpendingClick,
        onCategoryClick = onCategoryClick,
        modifier = modifier,
    )
}

@Composable
fun HomeScreen(
    onCreateSpendingClick: () -> Unit,
    onSpendingClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onCategoryClick: (Long) -> Unit,
) {
    Column {
        Box(modifier = modifier.weight(1.0f)) {
            NavHost(
                navController = navController,
                startDestination = BottomNavItem.SPENDINGS_LIST.route,
            ) {
                spendingsList(
                    onCreateSpendingClick = onCreateSpendingClick,
                    onSpendingClick = onSpendingClick,
                )
                analytics()
                categoriesList(
                    onCategoryClick = onCategoryClick,
                )
            }
        }
        BottomNavigationBar(
            navController = navController,
        )
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        BottomNavItem.entries.toTypedArray().forEach { navItem ->
            NavigationBarItem(
                selected = currentDestination?.hasRoute(route = navItem.route) == true,
                onClick = {
                    if (currentDestination?.hasRoute(route = navItem.route)?.not() == true) {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
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
