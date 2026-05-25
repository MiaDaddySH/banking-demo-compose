package com.junfengtech.bankingdemo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bankingdemo.navigation.Routes
import com.junfengtech.bankingdemo.di.AppContainer
import com.junfengtech.bankingdemo.ui.dashboard.DashboardScreen
import com.junfengtech.bankingdemo.ui.login.LoginScreen
import com.junfengtech.bankingdemo.ui.transactiondetail.TransactionDetailScreen

@Composable
fun AppNavHost(
    appContainer: AppContainer
) {
    val navController = rememberNavController()

    fun navigateToDashboard() {
        navController.navigate(Routes.Dashboard) {
            popUpTo(Routes.Login) {
                inclusive = true
            }
        }
    }

    fun navigateToLogin() {
        navController.navigate(Routes.Login) {
            popUpTo(Routes.Dashboard) {
                inclusive = true
            }
        }
    }

    fun navigateToTransactionDetail(transactionId: String) {
        navController.navigate(
            Routes.transactionDetail(transactionId)
        )
    }

    NavHost(
        navController = navController,
        startDestination = Routes.Login
    ) {
        composable(Routes.Login) {
            LoginScreen(
                onLoginSuccess = {
                    navigateToDashboard()
                }
            )
        }

        composable(Routes.Dashboard) {
            DashboardScreen(
                onLogout = {
                    navigateToLogin()
                },
                onTransactionClick = { transactionId ->
                    navigateToTransactionDetail(transactionId)
                },
                transactionRepository = appContainer.transactionRepository
            )
        }

        composable(
            route = Routes.TransactionDetailRoute,
            arguments = listOf(
                navArgument(Routes.TransactionIdArg) {
                    type = NavType.StringType
                }
            )
        ) {
            TransactionDetailScreen(
                onBack = {
                    navController.popBackStack()
                },
                transactionRepository = appContainer.transactionRepository
            )
        }
    }
}