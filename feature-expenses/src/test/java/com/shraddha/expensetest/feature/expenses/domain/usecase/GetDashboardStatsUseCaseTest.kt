package com.shraddha.expensetest.feature.expenses.domain.usecase

import com.shraddha.expensetest.feature.expenses.domain.model.Expense
import com.shraddha.expensetest.feature.expenses.domain.repository.ExpenseRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetDashboardStatsUseCaseTest {
    private lateinit var getDashboardStatsUseCase: GetDashboardStatsUseCase
    private lateinit var repository: ExpenseRepository

    @Before
    fun setUp() {
        repository = mockk()
        getDashboardStatsUseCase = GetDashboardStatsUseCase(repository)
    }

    @Test
    fun `invoke returns correct stats`() = runBlocking {
        val expenses = listOf(
            Expense(amount = 10.0, category = "Food", description = "", date = 1L),
            Expense(amount = 20.0, category = "Food", description = "", date = 2L),
            Expense(amount = 30.0, category = "Transport", description = "", date = 3L)
        )
        every { repository.getAllExpenses() } returns flowOf(expenses)

        val stats = getDashboardStatsUseCase().first()

        assertEquals(60.0, stats.totalExpense, 0.0)
        assertEquals(2, stats.categoryBreakdown.size)
        assertEquals(30.0, stats.categoryBreakdown["Food"]!!, 0.0)
        assertEquals(30.0, stats.categoryBreakdown["Transport"]!!, 0.0)
    }
}
