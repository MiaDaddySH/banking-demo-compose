package com.junfengtech.bankingdemo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bankingdemo.navigation.Routes
import com.junfengtech.bankingdemo.ui.dashboard.DashboardScreen
import com.junfengtech.bankingdemo.ui.login.LoginScreen
import com.junfengtech.bankingdemo.ui.transactiondetail.TransactionDetailScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Login
    ) {
        composable(Routes.Login) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.Dashboard) {
                        popUpTo(Routes.Login) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.Dashboard) {
            DashboardScreen(
                onLogout = {
                    navController.navigate(Routes.Login) {
                        popUpTo(Routes.Dashboard) {
                            inclusive = true
                        }
                    }
                },
                onTransactionClick = { transactionId ->
                    navController.navigate(
                        Routes.transactionDetail(transactionId)
                    )
                }
            )
        }

        composable(
            route = Routes.TransactionDetailRoute,
            arguments = listOf(
                navArgument(Routes.TransactionIdArg) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments
                ?.getString(Routes.TransactionIdArg)
                .orEmpty()

            TransactionDetailScreen(
                transactionId = transactionId
            )
        }
    }
}