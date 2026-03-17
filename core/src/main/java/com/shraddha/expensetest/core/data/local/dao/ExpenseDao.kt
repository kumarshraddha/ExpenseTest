package com.shraddha.expensetest.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shraddha.expensetest.core.data.local.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity): Long

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getExpensesBetweenDates(startDate: Long, endDate: Long): Flow<List<ExpenseEntity>>
    
    @Query("SELECT * FROM expenses WHERE date >= :startDate AND date <= :endDate")
    suspend fun getExpensesBetweenDatesSync(startDate: Long, endDate: Long): List<ExpenseEntity>

    @androidx.room.Delete
    suspend fun deleteExpense(expense: ExpenseEntity)
}
