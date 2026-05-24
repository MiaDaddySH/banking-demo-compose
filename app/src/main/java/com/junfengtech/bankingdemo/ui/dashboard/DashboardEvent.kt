package com.junfengtech.bankingdemo.ui.dashboard

sealed interface DashboardEvent {
    data object LoggedOut : DashboardEvent
    data class NavigateToTransactionDetail(val transactionId: String) : DashboardEvent
}