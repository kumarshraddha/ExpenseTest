package com.shraddha.expensetest.di

import android.content.Context
import com.shraddha.expensetest.core.di.DatabaseModule
import com.shraddha.expensetest.feature.expenses.data.repository.ExpenseRepositoryImpl
import com.shraddha.expensetest.feature.expenses.domain.repository.ExpenseRepository
import com.shraddha.expensetest.feature.expenses.domain.usecase.AddExpenseUseCase
import com.shraddha.expensetest.feature.expenses.domain.usecase.GetDashboardStatsUseCase
import com.shraddha.expensetest.feature.expenses.domain.usecase.GetExpensesUseCase
import com.shraddha.expensetest.feature.expenses.domain.usecase.GetTrendsUseCase

class AppContainer(context: Context) {
    private val database = DatabaseModule.getDatabase(context)
    private val expenseDao = DatabaseModule.provideExpenseDao(database)
    
    val expenseRepository: ExpenseRepository by lazy {
        ExpenseRepositoryImpl(expenseDao)
    }
    
    val addExpenseUseCase: AddExpenseUseCase by lazy {
        AddExpenseUseCase(expenseRepository)
    }
    
    val getExpensesUseCase: GetExpensesUseCase by lazy {
        GetExpensesUseCase(expenseRepository)
    }
    
    val getDashboardStatsUseCase: GetDashboardStatsUseCase by lazy {
        GetDashboardStatsUseCase(expenseRepository)
    }
    
    val getTrendsUseCase: GetTrendsUseCase by lazy {
        GetTrendsUseCase(expenseRepository)
    }
}
