package com.shraddha.expensetest.feature.expenses.presentation.dashboard

import com.shraddha.expensetest.feature.expenses.domain.model.Expense
import com.shraddha.expensetest.feature.expenses.domain.usecase.DashboardStats
import com.shraddha.expensetest.feature.expenses.domain.usecase.GetDashboardStatsUseCase
import com.shraddha.expensetest.feature.expenses.domain.usecase.GetExpensesUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {
    private lateinit var viewModel: DashboardViewModel
    private lateinit var getDashboardStatsUseCase: GetDashboardStatsUseCase
    private lateinit var getExpensesUseCase: GetExpensesUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getDashboardStatsUseCase = mockk()
        getExpensesUseCase = mockk()
        
        val stats = DashboardStats(100.0, mapOf("Food" to 100.0))
        val expenses = listOf(Expense(1, 100.0, "Food", "", 123L))
        
        every { getDashboardStatsUseCase() } returns flowOf(stats)
        every { getExpensesUseCase() } returns flowOf(expenses)
        
        viewModel = DashboardViewModel(getDashboardStatsUseCase, getExpensesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state reflects use case data`() = runTest {
        val stats = viewModel.stats.first { it != null }
        val expenses = viewModel.recentExpenses.first { it.isNotEmpty() }
        
        assertEquals(100.0, stats?.totalExpense ?: 0.0, 0.0)
        assertEquals(1, expenses.size)
    }
}
