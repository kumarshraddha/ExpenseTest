package com.shraddha.expensetest.feature.expenses.presentation.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest
import com.shraddha.expensetest.core.ui.theme.ExpenseTestTheme
import com.shraddha.expensetest.feature.expenses.domain.model.Expense
import com.shraddha.expensetest.feature.expenses.domain.usecase.DashboardStats

class DashboardScreenshotTest {
    @PreviewTest
    @Preview(showBackground = true)
    @Composable
    fun SummaryCardPreview() {
        ExpenseTestTheme {
            SummaryCard(
                stats = DashboardStats(
                    totalExpense = 1250.50,
                    categoryBreakdown = mapOf("Food" to 500.0, "Transport" to 200.0)
                )
            )
        }
    }

    @PreviewTest
    @Preview(showBackground = true)
    @Composable
    fun ExpenseItemPreview() {
        ExpenseTestTheme {
            ExpenseItem(
                expense = Expense(
                    id = 1,
                    amount = 15.5,
                    category = "Food",
                    description = "Lunch",
                    date = 1672531200000L
                )
            )
        }
    }
}
