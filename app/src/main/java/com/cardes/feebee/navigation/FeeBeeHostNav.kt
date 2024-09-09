package com.cardes.feebee.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.cardes.feebee.ui.editspending.editSpending
import com.cardes.feebee.ui.editspending.navigateToCreateSpending
import com.cardes.feebee.ui.editspending.navigateToEditSpending
import com.cardes.feebee.ui.spendingdetails.navigateToSpendingDetails
import com.cardes.feebee.ui.spendingdetails.spendingDetails
import com.cardes.feebee.ui.spendingslist.spendingsList

@Composable
fun FeeBeeHostNav(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavRoutes.Main.name,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        navigation(
            startDestination = NavRoutes.Main.SpendingsList.name,
            route = NavRoutes.Main.name,
        ) {
            spendingsList(
                onCreateSpendingClick = {
                    navController.navigateToCreateSpending()
                },
                onSpendingClick = navController::navigateToSpendingDetails,
            )
            editSpending(
                onNavUp = navController::navigateUp,
                onSpendingRemoved = {
                    navController.popBackStack(
                        NavRoutes.Main.SpendingsList.name,
                        inclusive = false,
                    )
                },
            )
            spendingDetails(
                onEditClick = { spendingId ->
                    navController.navigateToEditSpending(spendingId = spendingId)
                },
            )
        }
    }
}

