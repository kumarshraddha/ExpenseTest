package com.shraddha.expensetest.feature.expenses.domain.usecase

import com.shraddha.expensetest.feature.expenses.domain.model.Expense
import com.shraddha.expensetest.feature.expenses.domain.repository.ExpenseRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class AddExpenseUseCaseTest {
    private lateinit var useCase: AddExpenseUseCase
    private lateinit var repository: ExpenseRepository

    @Before
    fun setUp() {
        repository = mockk()
        useCase = AddExpenseUseCase(repository)
    }

    @Test
    fun `when amount is positive, expense is inserted`() = runTest {
        val expense = Expense(id = 1L, amount = 10.0, category = "Food", description = "Lunch", date = 123L)
        coEvery { repository.insertExpense(expense) } returns Unit

        useCase(expense)

        coVerify { repository.insertExpense(expense) }
    }

    @Test
    fun `when amount is zero, throws exception`() = runTest {
        val expense = Expense(id = 1L, amount = 0.0, category = "Food", description = "Lunch", date = 123L)
        
        assertThrows(IllegalArgumentException::class.java) {
            // Remove nested runTest
            kotlinx.coroutines.runBlocking { useCase(expense) }
        }
    }

    @Test
    fun `when amount is negative, throws exception`() = runTest {
        val expense = Expense(id = 1L, amount = -10.0, category = "Food", description = "Lunch", date = 123L)
        
        assertThrows(IllegalArgumentException::class.java) {
            // Remove nested runTest
            kotlinx.coroutines.runBlocking { useCase(expense) }
        }
    }

    @Test
    fun `INTENTIONAL FAILURE - negative amount should not throw exception`() = runTest {
        val expense = Expense(id = 1L, amount = -50.0, category = "Failure", description = "Should fail", date = 123L)
        coEvery { repository.insertExpense(expense) } returns Unit

        // This should throw IllegalArgumentException but we assert no exception is thrown
        useCase(expense) 
        
        coVerify { repository.insertExpense(expense) }
    }
}
