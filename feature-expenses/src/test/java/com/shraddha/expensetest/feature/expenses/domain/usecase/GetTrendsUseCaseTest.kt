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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GetTrendsUseCaseTest {
    private lateinit var useCase: GetTrendsUseCase
    private lateinit var repository: ExpenseRepository
    private val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetTrendsUseCase(repository)
    }

    @Test
    fun `when repository returns expenses, correctly groups by date`() = runTest {
        val date1 = 1672531200000L // Jan 01 2023
        val date2 = 1672617600000L // Jan 02 2023
        
        val expenses = listOf(
            Expense(1L, 100.0, "Food", "Lunch", date1),
            Expense(2L, 50.0, "Food", "Dinner", date1),
            Expense(3L, 200.0, "Transport", "Bus", date2)
        )
        every { repository.getAllExpenses() } returns flowOf(expenses)

        val trends = useCase().first()

        assertEquals(2, trends.size)
        
        // Reversed as per use case implementation
        val jan02 = trends[0] 
        val jan01 = trends[1]

        assertEquals(dateFormat.format(Date(date2)), jan02.label)
        assertEquals(200.0, jan02.totalAmount, 0.0)
        assertEquals(200.0, jan02.categoryAmounts["Transport"] ?: 0.0, 0.0)

        assertEquals(dateFormat.format(Date(date1)), jan01.label)
        assertEquals(150.0, jan01.totalAmount, 0.0)
        assertEquals(150.0, jan01.categoryAmounts["Food"] ?: 0.0, 0.0)
    }

    @Test
    fun `when repository returns empty list, returns empty trends`() = runTest {
        every { repository.getAllExpenses() } returns flowOf(emptyList())

        val trends = useCase().first()

        assertEquals(0, trends.size)
    }
}
