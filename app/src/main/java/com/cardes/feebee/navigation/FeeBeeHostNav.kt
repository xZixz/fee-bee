package com.cardes.feebee.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.cardes.editcategory.navigation.editCategory
import com.cardes.editcategory.navigation.navigateToEditCategory
import com.cardes.feebee.ui.editspending.editSpending
import com.cardes.feebee.ui.editspending.navigateToCreateSpending
import com.cardes.feebee.ui.editspending.navigateToEditSpending
import com.cardes.feebee.ui.home.navigation.home
import com.cardes.spendingdetail.navigation.navigateToSpendingDetail
import com.cardes.spendingdetail.navigation.spendingDetail

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
                onSpendingClick = navController::navigateToSpendingDetail,
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
            spendingDetail(
                onEditClick = { spendingId ->
                    navController.navigateToEditSpending(spendingId = spendingId)
                },
            )
            editCategory(
                onFinishRemovingCategory = navController::navigateUp,
            )
        }
    }
}
