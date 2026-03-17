package com.shraddha.expensetest.feature.expenses.presentation.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shraddha.expensetest.core.ui.components.CategoryDropdown
import com.shraddha.expensetest.core.ui.components.ExpenseTextField
import com.shraddha.expensetest.core.ui.theme.ExpenseTestTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun InputScreen(
    viewModel: InputViewModel,
    onBack: () -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Food") }
    var description by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    val categories = listOf("Food", "Transport", "Shopping", "Utilities", "Other")

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is InputViewModel.UiEvent.SaveSuccess -> {
                    onBack()
                }
                is InputViewModel.UiEvent.ShowError -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            ExpenseTextField(
                value = amount,
                onValueChange = { amount = it },
                label = "Amount",
                keyboardType = KeyboardType.Decimal
            )
            Spacer(modifier = Modifier.height(16.dp))
            CategoryDropdown(
                categories = categories,
                selectedCategory = category,
                onCategorySelected = { category = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            ExpenseTextField(
                value = description,
                onValueChange = { description = it },
                label = "Description"
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { viewModel.onSaveExpense(amount, category, description) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Expense")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputScreenPreview() {
    ExpenseTestTheme {
        // Mocking ViewModel or using a simpler version for preview
        // For now just showing the UI layout
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ExpenseTextField(
                value = "10.50",
                onValueChange = { },
                label = "Amount",
                keyboardType = KeyboardType.Decimal
            )
            Spacer(modifier = Modifier.height(16.dp))
            CategoryDropdown(
                categories = listOf("Food", "Transport"),
                selectedCategory = "Food",
                onCategorySelected = { }
            )
            Spacer(modifier = Modifier.height(16.dp))
            ExpenseTextField(
                value = "Lunch",
                onValueChange = { },
                label = "Description"
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Expense")
            }
        }
    }
}
