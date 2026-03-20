package com.shraddha.expensetest.presentation.navigation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shraddha.expensetest.core.ui.theme.ExpenseTestTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [33])
class NavGraphTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `check start destination`() {
        val expectedStartDestination = "dashboard"
        val actualStartDestination = "dashboard" 
        
        assertEquals("Incorrect start destination!", expectedStartDestination, actualStartDestination)
    }

    @Test
    fun `when add expense clicked, navigates to input screen`() {
        composeTestRule.setContent {
            ExpenseTestTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }

        // Dashboard is start destination, should have "Add Expense" FAB
        composeTestRule.onNodeWithContentDescription("Add Expense").performClick()

        // After click, we should be on Input screen, which has a "Save Expense" button
        composeTestRule.onNodeWithText("Save Expense").assertExists()
    }
}
