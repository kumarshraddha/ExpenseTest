package com.shraddha.expensetest.core.di

import android.content.Context
import com.shraddha.expensetest.core.data.local.ExpenseDatabase
import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Test

class DatabaseModuleTest {

    @Test
    fun `getDatabase returns database instance`() {
        val context = mockk<Context>(relaxed = true)
        val database = DatabaseModule.getDatabase(context)
        assertNotNull(database)
    }

    @Test
    fun `provideExpenseDao returns dao instance`() {
        val database = mockk<ExpenseDatabase>(relaxed = true)
        val dao = DatabaseModule.provideExpenseDao(database)
        assertNotNull(dao)
    }
}
