package com.shraddha.expensetest.feature.expenses.presentation.trends

import com.shraddha.expensetest.feature.expenses.domain.usecase.GetTrendsUseCase
import com.shraddha.expensetest.feature.expenses.domain.usecase.TrendPoint
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
class TrendsViewModelTest {
    private lateinit var viewModel: TrendsViewModel
    private lateinit var getTrendsUseCase: GetTrendsUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getTrendsUseCase = mockk()
        
        val trends = listOf(TrendPoint("Jan 01", 100.0))
        every { getTrendsUseCase() } returns flowOf(trends)
        
        viewModel = TrendsViewModel(getTrendsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state reflects trends data`() = runTest {
        val trends = viewModel.trends.first { it.isNotEmpty() }
        assertEquals(1, trends.size)
        assertEquals("Jan 01", trends[0].label)
    }
}
