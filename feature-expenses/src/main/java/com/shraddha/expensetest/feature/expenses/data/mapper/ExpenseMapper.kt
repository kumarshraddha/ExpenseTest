package com.shraddha.expensetest.feature.expenses.data.mapper

import com.shraddha.expensetest.core.data.local.entity.ExpenseEntity
import com.shraddha.expensetest.feature.expenses.domain.model.Expense

fun ExpenseEntity.toDomain(): Expense {
    return Expense(
        id = id,
        amount = amount,
        category = category,
        description = description,
        date = date
    )
}

fun Expense.toEntity(): ExpenseEntity {
    return ExpenseEntity(
        id = id ?: 0,
        amount = amount,
        category = category,
        description = description,
        date = date
    )
}
