package com.shraddha.expensetest.feature.expenses.domain.usecase

import com.shraddha.expensetest.feature.expenses.domain.model.Expense
import com.shraddha.expensetest.feature.expenses.domain.repository.ExpenseRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddExpenseUseCaseTest {
    private lateinit var addExpenseUseCase: AddExpenseUseCase
    private lateinit var repository: ExpenseRepository

    @Before
    fun setUp() {
        repository = mockk()
        addExpenseUseCase = AddExpenseUseCase(repository)
    }

    @Test
    fun `invoke with valid expense calls repository`() = runBlocking {
        val expense = Expense(amount = 10.0, category = "Food", description = "Lunch", date = 123L)
        coEvery { repository.insertExpense(any()) } returns Unit

        addExpenseUseCase(expense)

        coVerify { repository.insertExpense(expense) }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `invoke with zero amount throws exception`() = runBlocking {
        val expense = Expense(amount = 0.0, category = "Food", description = "Lunch", date = 123L)
        addExpenseUseCase(expense)
    }
}
