package com.shraddha.expensetest.feature.expenses.data.repository

import com.shraddha.expensetest.core.data.local.dao.ExpenseDao
import com.shraddha.expensetest.core.data.local.entity.ExpenseEntity
import com.shraddha.expensetest.feature.expenses.domain.model.Expense
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExpenseRepositoryImplTest {
    private lateinit var repository: ExpenseRepositoryImpl
    private lateinit var dao: ExpenseDao

    @Before
    fun setUp() {
        dao = mockk()
        repository = ExpenseRepositoryImpl(dao)
    }

    @Test
    fun `getAllExpenses returns mapped domain models`() = runBlocking {
        val entities = listOf(
            ExpenseEntity(id = 1, amount = 10.0, category = "Food", description = "Lunch", date = 123L)
        )
        every { dao.getAllExpenses() } returns flowOf(entities)

        val result = repository.getAllExpenses().first()

        assertEquals(1, result.size)
        assertEquals(10.0, result[0].amount, 0.0)
        assertEquals("Food", result[0].category)
    }

    @Test
    fun `insertExpense calls dao with mapped entity`() = runBlocking {
        val expense = Expense(id = 1, amount = 10.0, category = "Food", description = "Lunch", date = 123L)
        coEvery { dao.insertExpense(any()) } returns 1L

        repository.insertExpense(expense)

        coVerify { dao.insertExpense(match { it.amount == 10.0 && it.category == "Food" }) }
    }
}
