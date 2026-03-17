package com.shraddha.expensetest.feature.expenses.data.repository

import com.shraddha.expensetest.core.data.local.dao.ExpenseDao
import com.shraddha.expensetest.feature.expenses.data.mapper.toDomain
import com.shraddha.expensetest.feature.expenses.data.mapper.toEntity
import com.shraddha.expensetest.feature.expenses.domain.model.Expense
import com.shraddha.expensetest.feature.expenses.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseRepositoryImpl(
    private val dao: ExpenseDao
) : ExpenseRepository {

    override fun getAllExpenses(): Flow<List<Expense>> {
        return dao.getAllExpenses().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getExpensesBetweenDates(startDate: Long, endDate: Long): Flow<List<Expense>> {
        return dao.getExpensesBetweenDates(startDate, endDate).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertExpense(expense: Expense) {
        dao.insertExpense(expense.toEntity())
    }

    override suspend fun deleteExpense(expense: Expense) {
        dao.deleteExpense(expense.toEntity())
    }
}
