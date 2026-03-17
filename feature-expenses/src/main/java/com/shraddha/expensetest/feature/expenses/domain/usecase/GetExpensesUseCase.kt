package com.shraddha.expensetest.feature.expenses.domain.usecase

import com.shraddha.expensetest.feature.expenses.domain.model.Expense
import com.shraddha.expensetest.feature.expenses.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetExpensesUseCase(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<Expense>> {
        return repository.getAllExpenses()
    }
}
