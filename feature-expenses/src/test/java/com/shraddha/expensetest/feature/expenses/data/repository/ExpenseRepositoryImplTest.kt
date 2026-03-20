package com.shraddha.expensetest.feature.expenses.data.repository

import com.shraddha.expensetest.core.data.local.dao.ExpenseDao
import com.shraddha.expensetest.core.data.local.entity.ExpenseEntity
import com.shraddha.expensetest.feature.expenses.domain.model.Expense
import com.shraddha.expensetest.feature.expenses.domain.repository.ExpenseRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExpenseRepositoryImplTest {
    private lateinit var repository: ExpenseRepository
    private lateinit var dao: ExpenseDao

    @Before
    fun setUp() {
        dao = mockk()
        repository = ExpenseRepositoryImpl(dao)
    }

    @Test
    fun `getAllExpenses returns mapped domain model`() = runTest {
        val entity = ExpenseEntity(id = 1, amount = 100.0, category = "Food", description = "Lunch", date = 123L)
        every { dao.getAllExpenses() } returns flowOf(listOf(entity))

        val expenses = repository.getAllExpenses().first()

        assertEquals(1, expenses.size)
        assertEquals(1L, expenses[0].id)
        assertEquals(100.0, expenses[0].amount, 0.0)
        assertEquals("Food", expenses[0].category)
        assertEquals("Lunch", expenses[0].description)
        assertEquals(123L, expenses[0].date)
        
        verify { dao.getAllExpenses() }
    }

    @Test
    fun `insertExpense calls dao correctly`() = runTest {
        val domain = Expense(id = 1L, amount = 100.0, category = "Food", description = "Lunch", date = 123L)
        val entity = ExpenseEntity(id = 1, amount = 100.0, category = "Food", description = "Lunch", date = 123L)
        
        coEvery { dao.insertExpense(any()) } returns 1L

        repository.insertExpense(domain)

        coVerify { dao.insertExpense(entity) }
    }

    @Test
    fun `deleteExpense calls dao correctly`() = runTest {
        val domain = Expense(id = 1L, amount = 100.0, category = "Food", description = "Lunch", date = 123L)
        val entity = ExpenseEntity(id = 1, amount = 100.0, category = "Food", description = "Lunch", date = 123L)
        
        coEvery { dao.deleteExpense(any()) } returns Unit

        repository.deleteExpense(domain)

        coVerify { dao.deleteExpense(entity) }
    }
}
