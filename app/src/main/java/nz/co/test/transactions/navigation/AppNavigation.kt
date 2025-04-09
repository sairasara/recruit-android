package nz.co.test.transactions.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import nz.co.test.transactions.ui.features.transactions.TransactionListScreen
import nz.co.test.transactions.ui.viewmodel.TransactionViewModel

@Composable
fun AppNavigation(navController: NavHostController,viewModel: TransactionViewModel = hiltViewModel()) {

    NavHost(navController = navController, startDestination = NavigationItem.TransactionList.route) {

        composable(NavigationItem.TransactionList.route) {
            TransactionListScreen(viewModel, navController)
        }
    }
}