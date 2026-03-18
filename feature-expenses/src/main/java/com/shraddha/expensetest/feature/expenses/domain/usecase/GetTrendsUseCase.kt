package com.shraddha.expensetest.feature.expenses.domain.usecase

import com.shraddha.expensetest.feature.expenses.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class TrendPoint(
    val label: String,
    val totalAmount: Double,
    val categoryAmounts: Map<String, Double>
)

class GetTrendsUseCase(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<TrendPoint>> {
        val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
        return repository.getAllExpenses().map { expenses ->
            expenses.groupBy { 
                dateFormat.format(Date(it.date))
            }.map { (label, items) ->
                val total = items.sumOf { it.amount }
                val categoryMap = items.groupBy { it.category }
                    .mapValues { (_, categoryItems) -> categoryItems.sumOf { it.amount } }
                
                TrendPoint(label, total, categoryMap)
            }.reversed() // Sort by date ascending (assuming getAllExpenses is DESC)
        }
    }
}
