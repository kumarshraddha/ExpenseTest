package com.shraddha.expensetest.feature.expenses.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.material3.CardDefaults
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shraddha.expensetest.feature.expenses.domain.model.Expense
import com.shraddha.expensetest.feature.expenses.domain.usecase.DashboardStats
import com.shraddha.expensetest.core.ui.theme.ExpenseTestTheme

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onAddExpense: () -> Unit
) {
    val stats by viewModel.stats.collectAsState()
    val expenses by viewModel.recentExpenses.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddExpense) {
                Icon(Icons.Default.Add, contentDescription = "Add Expense")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            stats?.let {
                SummaryCard(stats = it)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Recent Expenses",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(expenses) { expense ->
                    ExpenseItem(expense = expense)
                }
            }
        }
    }
}

@Composable
fun SummaryCard(stats: DashboardStats) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .graphicsLayer(rotationZ = 15f)
            .padding(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Red)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.Warning, contentDescription = null, tint = Color.White)
            Text(
                text = "STOP SPENDING!",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
            Text(
                text = "$${stats.totalExpense}",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Yellow
            )
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Green)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = expense.category.uppercase(),
                style = MaterialTheme.typography.displayLarge,
                color = Color.Blue
            )
            Text(
                text = "You bought: ${expense.description}",
                fontSize = 24.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    ExpenseTestTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            SummaryCard(stats = DashboardStats(1250.50, mapOf("Food" to 500.0, "Transport" to 200.0)))
            Spacer(modifier = Modifier.height(16.dp))
            ExpenseItem(expense = Expense(1, 15.5, "Food", "Lunch", System.currentTimeMillis()))
        }
    }
}
