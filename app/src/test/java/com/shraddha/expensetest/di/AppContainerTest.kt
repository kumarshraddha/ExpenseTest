package com.shraddha.expensetest.di

import android.content.Context
import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class AppContainerTest {
    private lateinit var container: AppContainer
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = mockk(relaxed = true)
        container = AppContainer(context)
    }

    @Test
    fun `container provides all required use cases`() {
        assertNotNull(container.getExpensesUseCase)
        assertNotNull(container.addExpenseUseCase)
        assertNotNull(container.getDashboardStatsUseCase)
        assertNotNull(container.getTrendsUseCase)
    }
}
