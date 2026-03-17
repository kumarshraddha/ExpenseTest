package com.shraddha.expensetest.feature.expenses.domain.repository

import com.shraddha.expensetest.feature.expenses.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getAllExpenses(): Flow<List<Expense>>
    fun getExpensesBetweenDates(startDate: Long, endDate: Long): Flow<List<Expense>>
    suspend fun insertExpense(expense: Expense)
    suspend fun deleteExpense(expense: Expense)
}
