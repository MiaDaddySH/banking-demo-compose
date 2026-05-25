package com.junfengtech.bankingdemo.ui.transactiondetail

data class TransactionDetailUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val transactionId: String = "",
    val title: String = "",
    val date: String = "",
    val amount: String = "",
    val status: String = "",
    val merchant: String = "",
    val category: String = ""
)