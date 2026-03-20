package com.shraddha.expensetest.feature.expenses.presentation.input

import com.shraddha.expensetest.feature.expenses.domain.usecase.AddExpenseUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

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

        viewModel.onSaveExpense("10.0", "Food", "Lunch")
        
        val event = viewModel.eventFlow.first()
        assertTrue(event is InputViewModel.UiEvent.SaveSuccess)
    }

    @Test
    fun `onSaveExpense with empty amount emits ShowError`() = runTest {
        viewModel.onSaveExpense("", "Food", "Lunch")
        
        val event = viewModel.eventFlow.first()
        assertTrue(event is InputViewModel.UiEvent.ShowError)
    }
}
