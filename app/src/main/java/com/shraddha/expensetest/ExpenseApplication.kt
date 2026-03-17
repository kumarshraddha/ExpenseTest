package com.shraddha.expensetest

import android.app.Application
import com.shraddha.expensetest.di.AppContainer

class ExpenseApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
