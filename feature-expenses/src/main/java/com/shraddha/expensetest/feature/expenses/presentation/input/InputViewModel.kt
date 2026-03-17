package com.shraddha.expensetest.feature.expenses.presentation.input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shraddha.expensetest.feature.expenses.domain.model.Expense
import com.shraddha.expensetest.feature.expenses.domain.usecase.AddExpenseUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class InputViewModel(
    private val addExpenseUseCase: AddExpenseUseCase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onSaveExpense(amount: String, category: String, description: String) {
        viewModelScope.launch {
            try {
                val amountDouble = amount.toDoubleOrNull() ?: 0.0
                val expense = Expense(
                    amount = amountDouble,
                    category = category,
                    description = description,
                    date = System.currentTimeMillis()
                )
                addExpenseUseCase(expense)
                _eventFlow.emit(UiEvent.SaveSuccess)
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ShowError(e.message ?: "Unknown error"))
            }
        }
    }

    sealed class UiEvent {
        object SaveSuccess : UiEvent()
        data class ShowError(val message: String) : UiEvent()
    }
}
