package com.shraddha.expensetest.core.di

import android.content.Context
import androidx.room.Room
import com.shraddha.expensetest.core.data.local.ExpenseDatabase
import com.shraddha.expensetest.core.data.local.dao.ExpenseDao

object DatabaseModule {
    @Volatile
    private var INSTANCE: ExpenseDatabase? = null

    fun getDatabase(context: Context): ExpenseDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                ExpenseDatabase::class.java,
                "expense_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }

    fun provideExpenseDao(database: ExpenseDatabase): ExpenseDao {
        return database.expenseDao()
    }
}
