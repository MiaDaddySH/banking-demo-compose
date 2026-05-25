package com.junfengtech.bankingdemo


import android.app.Application
import com.junfengtech.bankingdemo.di.AppContainer

class BankingDemoApplication : Application() {
    val appContainer: AppContainer by lazy {
        AppContainer()
    }
}