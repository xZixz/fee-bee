package com.cardes.feebee.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.cardes.feebee.ui.editcategory.editCategory
import com.cardes.feebee.ui.editcategory.navigateToEditCategory
import com.cardes.feebee.ui.editspending.editSpending
import com.cardes.feebee.ui.editspending.navigateToCreateSpending
import com.cardes.feebee.ui.editspending.navigateToEditSpending
import com.cardes.feebee.ui.home.home
import com.cardes.feebee.ui.spendingdetails.navigateToSpendingDetails
import com.cardes.feebee.ui.spendingdetails.spendingDetails

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
            startDestination = NavRoutes.Main.Home.name,
            route = NavRoutes.Main.name,
        ) {
            home(
                onCreateSpendingClick = {
                    navController.navigateToCreateSpending()
                },
                onSpendingClick = navController::navigateToSpendingDetails,
                onCategoryClick = navController::navigateToEditCategory,
            )
            editSpending(
                onNavUp = navController::navigateUp,
                onSpendingRemoved = {
                    navController.popBackStack(
                        NavRoutes.Main.Home.name,
                        inclusive = false,
                    )
                },
            )
            spendingDetails(
                onEditClick = { spendingId ->
                    navController.navigateToEditSpending(spendingId = spendingId)
                },
            )
            editCategory()
        }
    }
}

