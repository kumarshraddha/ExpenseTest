package com.shraddha.expensetest.feature.expenses.domain.model

data class Expense(
    val id: Long? = null,
    val amount: Double,
    val category: String,
    val description: String,
    val date: Long
)
