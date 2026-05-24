package com.junfengtech.bankingdemo.ui.dashboard


data class DashboardUiState(
    val userName: String = "Alex",
    val accountName: String = "Main Checking Account",
    val balance: String = "€8,420.50",
    val transactions: List<TransactionUiModel> = emptyList()
)