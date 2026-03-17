package com.shraddha.expensetest.feature.expenses.presentation.trends

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shraddha.expensetest.core.ui.theme.ExpenseTestTheme
import com.shraddha.expensetest.feature.expenses.domain.usecase.TrendPoint

@Composable
fun TrendsScreen(
    viewModel: TrendsViewModel
) {
    val trends by viewModel.trends.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Expense Trends",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))
        
        if (trends.isNotEmpty()) {
            SimpleBarChart(
                data = trends,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        } else {
            Text(text = "No data available for trends")
        }
    }
}

@Composable
fun SimpleBarChart(
    data: List<TrendPoint>,
    modifier: Modifier = Modifier
) {
    val maxAmount = data.maxOfOrNull { it.amount } ?: 1.0
    val barColor = MaterialTheme.colorScheme.primary

    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val barWidth = canvasWidth / (data.size * 2)
        val spaceWidth = canvasWidth / (data.size * 2)

        data.forEachIndexed { index, point ->
            val barHeight = (point.amount / maxAmount) * canvasHeight
            val x = (index * (barWidth + spaceWidth)) + (spaceWidth / 2)
            val y = canvasHeight - barHeight.toFloat()

            drawRect(
                color = barColor,
                topLeft = Offset(x, y),
                size = Size(barWidth, barHeight.toFloat())
            )
            
            // Labels could be drawn here with drawText, but it's complex without NativeCanvas
            // For simple chart, just bars is a good start.
        }
        
        // Draw baseline
        drawLine(
            color = Color.Gray,
            start = Offset(0f, canvasHeight),
            end = Offset(canvasWidth, canvasHeight),
            strokeWidth = 2f
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TrendsScreenPreview() {
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
