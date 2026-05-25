package com.junfengtech.bankingdemo.di

import com.junfengtech.bankingdemo.data.repository.MockTransactionRepository
import com.junfengtech.bankingdemo.domain.repository.TransactionRepository

class AppContainer {
    val transactionRepository: TransactionRepository = MockTransactionRepository()
}