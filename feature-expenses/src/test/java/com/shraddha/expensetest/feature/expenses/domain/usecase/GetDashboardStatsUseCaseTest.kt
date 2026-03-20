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

class GetDashboardStatsUseCaseTest {
    private lateinit var useCase: GetDashboardStatsUseCase
    private lateinit var repository: ExpenseRepository

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetDashboardStatsUseCase(repository)
    }

    @Test
    fun `when repository returns expenses, correctly calculates stats`() = runTest {
        val expenses = listOf(
            Expense(1L, 100.0, "Food", "Lunch", 1L),
            Expense(2L, 50.0, "Food", "Dinner", 2L),
            Expense(3L, 200.0, "Transport", "Bus", 3L)
        )
        every { repository.getAllExpenses() } returns flowOf(expenses)

        val stats = useCase().first()

        assertEquals(350.0, stats.totalExpense, 0.0)
        assertEquals(2, stats.categoryBreakdown.size)
        assertEquals(150.0, stats.categoryBreakdown["Food"] ?: 0.0, 0.0)
        assertEquals(200.0, stats.categoryBreakdown["Transport"] ?: 0.0, 0.0)
    }

    @Test
    fun `when repository returns empty list, returns zero stats`() = runTest {
        every { repository.getAllExpenses() } returns flowOf(emptyList())

        val stats = useCase().first()

        assertEquals(0.0, stats.totalExpense, 0.0)
        assertEquals(0, stats.categoryBreakdown.size)
    }
}
