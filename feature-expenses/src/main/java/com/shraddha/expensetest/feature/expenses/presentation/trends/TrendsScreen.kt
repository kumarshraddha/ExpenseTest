package com.shraddha.expensetest.feature.expenses.presentation.trends

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            val categories = remember(trends) {
                trends.flatMap { it.categoryAmounts.keys }.distinct().sorted()
            }
            val colorMap = remember(categories) {
                val colors = listOf(
                    Color(0xFF42A5F5), // Blue
                    Color(0xFF66BB6A), // Green
                    Color(0xFFFFA726), // Orange
                    Color(0xFFEF5350), // Red
                    Color(0xFFAB47BC), // Purple
                    Color(0xFF26C6DA), // Cyan
                    Color(0xFFFFEE58)  // Yellow
                )
                categories.mapIndexed { index, category ->
                    category to colors[index % colors.size]
                }.toMap()
            }

            StackedBarChart(
                data = trends,
                colorMap = colorMap,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
            
            ChartLegend(colorMap = colorMap)
        } else {
            Text(
                text = "No data available for trends",
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }
}

@Composable
fun StackedBarChart(
    data: List<TrendPoint>,
    colorMap: Map<String, Color>,
    modifier: Modifier = Modifier
) {
    val maxAmount = (data.maxOfOrNull { it.totalAmount } ?: 1.0).coerceAtLeast(1.0)
    val textMeasurer = rememberTextMeasurer()
    val labelStyle = TextStyle(
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
        fontWeight = FontWeight.Medium
    )
    val totalLabelStyle = TextStyle(
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold
    )

    Canvas(modifier = modifier) {
        val yLabelWidth = 60.dp.toPx()
        val xAxisHeight = 40.dp.toPx()
        val topPadding = 30.dp.toPx()
        
        val chartWidth = size.width - yLabelWidth
        val chartHeight = size.height - xAxisHeight - topPadding
        
        val barGroupWidth = chartWidth / data.size
        val barWidth = barGroupWidth * 0.5f
        val spaceWidth = barGroupWidth * 0.5f

        // Y-axis steps
        val ySteps = 5
        for (i in 0..ySteps) {
            val amount = (maxAmount / ySteps) * i
            val yPos = topPadding + chartHeight - (amount / maxAmount) * chartHeight
            
            val yLabelText = "$${amount.toInt()}"
            val yLabelLayout = textMeasurer.measure(yLabelText, labelStyle)
            
            drawText(
                textLayoutResult = yLabelLayout,
                topLeft = Offset(yLabelWidth - yLabelLayout.size.width - 15f, yPos.toFloat() - (yLabelLayout.size.height / 2f))
            )
            
            if (i > 0) {
                drawLine(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    start = Offset(yLabelWidth, yPos.toFloat()),
                    end = Offset(size.width, yPos.toFloat()),
                    strokeWidth = 1f
                )
            }
        }

        data.forEachIndexed { index, point ->
            val x = yLabelWidth + (index * barGroupWidth) + (spaceWidth / 2)
            var currentY = (topPadding + chartHeight).toDouble()

            // Draw stacked segments
            point.categoryAmounts.forEach { (category, amount) ->
                val segmentHeight = (amount / maxAmount) * chartHeight
                val segmentY = currentY - segmentHeight
                
                drawRect(
                    color = colorMap[category] ?: Color.Gray,
                    topLeft = Offset(x, segmentY.toFloat()),
                    size = Size(barWidth, segmentHeight.toFloat())
                )
                currentY -= segmentHeight
            }
            
            // X-axis label (Date)
            val xLabelLayout = textMeasurer.measure(point.label, labelStyle)
            drawText(
                textLayoutResult = xLabelLayout,
                topLeft = Offset(x + (barWidth / 2) - (xLabelLayout.size.width / 2), topPadding + chartHeight + 10f)
            )
            
            // Total Amount Label
            val totalLabel = "$${point.totalAmount.toInt()}"
            val totalLabelLayout = textMeasurer.measure(totalLabel, totalLabelStyle)
            drawText(
                textLayoutResult = totalLabelLayout,
                topLeft = Offset(x + (barWidth / 2) - (totalLabelLayout.size.width / 2), currentY.toFloat() - totalLabelLayout.size.height - 5f)
            )
        }
        
        // Axes
        drawLine(
            color = Color.Gray,
            start = Offset(yLabelWidth, topPadding + chartHeight),
            end = Offset(size.width, topPadding + chartHeight),
            strokeWidth = 2f
        )
        drawLine(
            color = Color.Gray,
            start = Offset(yLabelWidth, topPadding),
            end = Offset(yLabelWidth, topPadding + chartHeight),
            strokeWidth = 2f
        )
    }
}

@Composable
fun ChartLegend(colorMap: Map<String, Color>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(colorMap.toList()) { (category, color) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(color)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = category,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrendsScreenPreview() {
    ExpenseTestTheme {
        val colorMap = mapOf(
            "Food" to Color(0xFF42A5F5),
            "Transport" to Color(0xFF66BB6A),
            "Entertainment" to Color(0xFFFFA726)
        )
        Column(modifier = Modifier.padding(16.dp)) {
            StackedBarChart(
                data = listOf(
                    TrendPoint("01 Jan", 150.0, mapOf("Food" to 50.0, "Transport" to 100.0)),
                    TrendPoint("02 Jan", 80.0, mapOf("Food" to 30.0, "Entertainment" to 50.0)),
                    TrendPoint("03 Jan", 200.0, mapOf("Food" to 80.0, "Transport" to 70.0, "Entertainment" to 50.0)),
                    TrendPoint("04 Jan", 120.0, mapOf("Transport" to 40.0, "Entertainment" to 80.0)),
                    TrendPoint("05 Jan", 40.0, mapOf("Food" to 40.0))
                ),
                colorMap = colorMap,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            ChartLegend(colorMap = colorMap)
        }
    }
}
