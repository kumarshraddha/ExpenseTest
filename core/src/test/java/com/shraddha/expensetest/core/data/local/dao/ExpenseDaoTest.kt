package com.shraddha.expensetest.core.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shraddha.expensetest.core.data.local.ExpenseDatabase
import com.shraddha.expensetest.core.data.local.entity.ExpenseEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ExpenseDaoTest {
    private lateinit var expenseDao: ExpenseDao
    private lateinit var db: ExpenseDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ExpenseDatabase::class.java).allowMainThreadQueries().build()
        expenseDao = db.expenseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        if (::db.isInitialized) {
            db.close()
        }
    }

    @Test
    @Throws(Exception::class)
    fun writeExpenseAndReadInList() = runBlocking {
        val expense = ExpenseEntity(
            amount = 15.50,
            category = "Food",
            date = 1672531200000L, // Jan 1 2023
            description = "Lunch"
        )
        expenseDao.insertExpense(expense)
        
        val expenses = expenseDao.getAllExpenses().first()
        assertEquals(1, expenses.size)
        assertEquals("Food", expenses[0].category)
        assertEquals(15.50, expenses[0].amount, 0.0)
    }

    @Test
    @Throws(Exception::class)
    fun getExpensesBetweenDates() = runBlocking {
        val e1 = ExpenseEntity(amount = 10.0, category = "Food", date = 1672531200000L, description = "") // Jan 1 2023
        val e2 = ExpenseEntity(amount = 20.0, category = "Transport", date = 1675209600000L, description = "") // Feb 1 2023
        val e3 = ExpenseEntity(amount = 30.0, category = "Utilities", date = 1677628800000L, description = "") // Mar 1 2023
        
        expenseDao.insertExpense(e1)
        expenseDao.insertExpense(e2)
        expenseDao.insertExpense(e3)
        
        // Query for Feb only
        val febExpenses = expenseDao.getExpensesBetweenDates(1675209600000L, 1677628799999L).first()
        assertEquals(1, febExpenses.size)
        assertEquals("Transport", febExpenses[0].category)
    }
}
