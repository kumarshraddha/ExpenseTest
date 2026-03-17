package com.shraddha.expensetest.feature.expenses.presentation.trends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shraddha.expensetest.feature.expenses.domain.usecase.GetTrendsUseCase
import com.shraddha.expensetest.feature.expenses.domain.usecase.TrendPoint
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class TrendsViewModel(
    private val getTrendsUseCase: GetTrendsUseCase
) : ViewModel() {

    val trends: StateFlow<List<TrendPoint>> = getTrendsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
