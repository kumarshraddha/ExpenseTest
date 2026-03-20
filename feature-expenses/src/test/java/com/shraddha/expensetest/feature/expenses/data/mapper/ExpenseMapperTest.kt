package com.shraddha.expensetest.feature.expenses.data.mapper

import com.shraddha.expensetest.core.data.local.entity.ExpenseEntity
import com.shraddha.expensetest.feature.expenses.domain.model.Expense
import org.junit.Assert.assertEquals
import org.junit.Test

class ExpenseMapperTest {

    @Test
    fun `toDomain maps correctly`() {
        val entity = ExpenseEntity(id = 1, amount = 10.0, category = "Food", description = "Lunch", date = 123L)
        val domain = entity.toDomain()
        
        assertEquals(entity.id, domain.id)
        assertEquals(entity.amount, domain.amount, 0.0)
        assertEquals(entity.category, domain.category)
        assertEquals(entity.description, domain.description)
        assertEquals(entity.date, domain.date)
    }

    @Test
    fun `toEntity maps correctly`() {
        val domain = Expense(id = 1L, amount = 10.0, category = "Food", description = "Lunch", date = 123L)
        val entity = domain.toEntity()
        
        assertEquals(domain.id, entity.id)
        assertEquals(domain.amount, entity.amount, 0.0)
        assertEquals(domain.category, entity.category)
        assertEquals(domain.description, entity.description)
        assertEquals(domain.date, entity.date)
    }
}
