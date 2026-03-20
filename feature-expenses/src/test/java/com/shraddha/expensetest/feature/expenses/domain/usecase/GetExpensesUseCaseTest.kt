package com.shraddha.expensetest.feature.expenses.domain.usecase

import com.shraddha.expensetest.feature.expenses.domain.model.Expense
import com.shraddha.expensetest.feature.expenses.domain.repository.ExpenseRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetExpensesUseCaseTest {
    private lateinit var useCase: GetExpensesUseCase
    private lateinit var repository: ExpenseRepository

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetExpensesUseCase(repository)
    }

    @Test
    fun `invoke returns flow of expenses from repository`() = runTest {
        val expenses = listOf(Expense(1L, 10.0, "Food", "", 123L))
        every { repository.getAllExpenses() } returns flowOf(expenses)

        val result = useCase().first()

        assertEquals(expenses, result)
    }
}
