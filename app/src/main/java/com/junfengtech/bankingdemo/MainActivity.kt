package com.junfengtech.bankingdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.junfengtech.bankingdemo.navigation.AppNavHost
import com.junfengtech.bankingdemo.ui.login.LoginScreen
import com.junfengtech.bankingdemo.ui.theme.BankingDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BankingDemoTheme {
                AppNavHost()
            }
        }
    }
}
