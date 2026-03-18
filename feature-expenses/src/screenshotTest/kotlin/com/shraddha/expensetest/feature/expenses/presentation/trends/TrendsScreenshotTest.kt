package com.shraddha.expensetest.feature.expenses.presentation.trends

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.tools.screenshot.PreviewTest
import com.shraddha.expensetest.core.ui.theme.ExpenseTestTheme
import com.shraddha.expensetest.feature.expenses.domain.usecase.TrendPoint

class TrendsScreenshotTest {
    @PreviewTest
    @Preview(showBackground = true)
    @Composable
    fun SimpleBarChartPreview() {
        ExpenseTestTheme {
            SimpleBarChart(
                data = listOf(
                    TrendPoint("Jan 01", 50.0),
                    TrendPoint("Jan 02", 20.0),
                    TrendPoint("Jan 03", 80.0),
                    TrendPoint("Jan 04", 40.0),
                    TrendPoint("Jan 05", 10.0)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp)
            )
        }
    }
}
