package com.junfengtech.bankingdemo.ui.dashboard

sealed interface DashboardAction {
    data object LogoutClicked : DashboardAction
    data class TransactionClicked(val transactionId: String) : DashboardAction
}