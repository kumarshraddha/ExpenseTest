package com.shraddha.expensetest.feature.expenses.presentation.input

import com.shraddha.expensetest.feature.expenses.domain.usecase.AddExpenseUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class InputViewModelTest {
    private lateinit var viewModel: InputViewModel
    private lateinit var addExpenseUseCase: AddExpenseUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        addExpenseUseCase = mockk()
        viewModel = InputViewModel(addExpenseUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onSaveExpense with valid input emits SaveSuccess`() = runTest {
        coEvery { addExpenseUseCase(any()) } returns Unit

        val events = mutableListOf<InputViewModel.UiEvent>()
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.eventFlow.collect { events.add(it) }
        }

        viewModel.onSaveExpense("10.50", "Food", "Lunch")
        testDispatcher.scheduler.advanceUntilIdle()
        
        assertTrue(events.first() is InputViewModel.UiEvent.SaveSuccess)
        collectJob.cancel()
    }

    @Test
    fun `onSaveExpense with invalid input emits ShowError`() = runTest {
        coEvery { addExpenseUseCase(any()) } throws IllegalArgumentException("Error")

        val events = mutableListOf<InputViewModel.UiEvent>()
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.eventFlow.collect { events.add(it) }
        }

        viewModel.onSaveExpense("0", "Food", "Lunch")
        testDispatcher.scheduler.advanceUntilIdle()
        
        assertTrue(events.first() is InputViewModel.UiEvent.ShowError)
        assertEquals("Error", (events.first() as InputViewModel.UiEvent.ShowError).message)
        collectJob.cancel()
    }
}
