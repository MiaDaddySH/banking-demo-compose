package com.junfengtech.bankingdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.junfengtech.bankingdemo.navigation.AppNavHost
import com.junfengtech.bankingdemo.ui.theme.BankingDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BankingDemoTheme {
                AppNavHost()
            }
        }
    }
}