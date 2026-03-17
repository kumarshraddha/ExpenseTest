package com.shraddha.expensetest.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shraddha.expensetest.core.data.local.dao.ExpenseDao
import com.shraddha.expensetest.core.data.local.entity.ExpenseEntity

@Database(entities = [ExpenseEntity::class], version = 1, exportSchema = false)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}
