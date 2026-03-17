package com.shraddha.expensetest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shraddha.expensetest.ExpenseApplication
import com.shraddha.expensetest.feature.expenses.presentation.dashboard.DashboardScreen
import com.shraddha.expensetest.feature.expenses.presentation.dashboard.DashboardViewModel
import com.shraddha.expensetest.feature.expenses.presentation.input.InputScreen
import com.shraddha.expensetest.feature.expenses.presentation.input.InputViewModel
import com.shraddha.expensetest.feature.expenses.presentation.trends.TrendsScreen
import com.shraddha.expensetest.feature.expenses.presentation.trends.TrendsViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val appContainer = (context.applicationContext as ExpenseApplication).container

    NavHost(
        navController = navController,
        startDestination = "dashboard",
        modifier = modifier
    ) {
        composable("dashboard") {
            val viewModel: DashboardViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return DashboardViewModel(
                            appContainer.getDashboardStatsUseCase,
                            appContainer.getExpensesUseCase
                        ) as T
                    }
                }
            )
            DashboardScreen(
                viewModel = viewModel,
                onAddExpense = { navController.navigate("input") }
            )
        }
        composable("input") {
            val viewModel: InputViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return InputViewModel(appContainer.addExpenseUseCase) as T
                    }
                }
            )
            InputScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable("trends") {
            val viewModel: TrendsViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return TrendsViewModel(appContainer.getTrendsUseCase) as T
                    }
                }
            )
            TrendsScreen(viewModel = viewModel)
        }
    }
}
