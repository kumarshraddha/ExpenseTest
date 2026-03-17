package com.shraddha.expensetest.feature.expenses.domain.usecase

import com.shraddha.expensetest.feature.expenses.domain.model.Expense
import com.shraddha.expensetest.feature.expenses.domain.repository.ExpenseRepository

class AddExpenseUseCase(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense) {
        if (expense.amount <= 0) {
            throw IllegalArgumentException("Amount must be greater than zero")
        }
        repository.insertExpense(expense)
    }
}
