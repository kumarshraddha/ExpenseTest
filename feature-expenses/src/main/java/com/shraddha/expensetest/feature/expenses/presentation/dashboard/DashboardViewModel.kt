package com.shraddha.expensetest.feature.expenses.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shraddha.expensetest.feature.expenses.domain.model.Expense
import com.shraddha.expensetest.feature.expenses.domain.usecase.DashboardStats
import com.shraddha.expensetest.feature.expenses.domain.usecase.GetDashboardStatsUseCase
import com.shraddha.expensetest.feature.expenses.domain.usecase.GetExpensesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class DashboardViewModel(
    private val getDashboardStatsUseCase: GetDashboardStatsUseCase,
    private val getExpensesUseCase: GetExpensesUseCase
) : ViewModel() {

    val stats: StateFlow<DashboardStats?> = getDashboardStatsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val recentExpenses: StateFlow<List<Expense>> = getExpensesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
