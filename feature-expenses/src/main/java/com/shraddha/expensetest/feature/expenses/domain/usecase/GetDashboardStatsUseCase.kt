package com.shraddha.expensetest.feature.expenses.domain.usecase

import com.shraddha.expensetest.feature.expenses.domain.model.Expense
import com.shraddha.expensetest.feature.expenses.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class DashboardStats(
    val totalExpense: Double,
    val categoryBreakdown: Map<String, Double>
)

class GetDashboardStatsUseCase(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): Flow<DashboardStats> {
        return repository.getAllExpenses().map { expenses ->
            val total = expenses.sumOf { it.amount }
            val breakdown = expenses.groupBy { it.category }
                .mapValues { entry -> entry.value.sumOf { it.amount } }
            DashboardStats(total, breakdown)
        }
    }
}
