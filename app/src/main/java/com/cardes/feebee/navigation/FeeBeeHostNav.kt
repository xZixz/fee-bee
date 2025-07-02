package com.cardes.feebee.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.cardes.editcategory.navigation.editCategory
import com.cardes.editcategory.navigation.navigateToEditCategory
import com.cardes.editspending.navigation.editSpending
import com.cardes.editspending.navigation.navigateToCreateSpending
import com.cardes.editspending.navigation.navigateToEditSpending
import com.cardes.feebee.ui.home.navigation.HomeRoute
import com.cardes.feebee.ui.home.navigation.home
import com.cardes.spendingdetail.navigation.navigateToSpendingDetail
import com.cardes.spendingdetail.navigation.spendingDetail

@Composable
fun FeeBeeHostNav(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier,
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
                    HomeRoute,
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
