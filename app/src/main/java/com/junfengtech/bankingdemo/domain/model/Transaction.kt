package com.junfengtech.bankingdemo.domain.model

import com.junfengtech.bankingdemo.ui.dashboard.TransactionUiModel

data class Transaction(
    val id: String,
    val title: String,
    val date: String,
    val amount: String,
    val status: String,
    val merchant: String,
    val category: String
) {
}