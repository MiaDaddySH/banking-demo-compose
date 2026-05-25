package com.junfengtech.bankingdemo.domain.repository

import com.junfengtech.bankingdemo.domain.model.Transaction

interface TransactionRepository {
    suspend fun getRecentTransactions(): List<Transaction>
    suspend fun getTransactionById(id: String): Transaction?
}