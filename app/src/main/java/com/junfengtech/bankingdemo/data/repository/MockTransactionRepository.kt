package com.junfengtech.bankingdemo.data.repository

import com.junfengtech.bankingdemo.domain.model.Transaction
import com.junfengtech.bankingdemo.domain.repository.TransactionRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class MockTransactionRepository @Inject constructor() : TransactionRepository {

    private val transactions = listOf(
        Transaction(
            id = "tx_salary",
            title = "Salary",
            date = "Today",
            amount = "+€3,200.00",
            status = "Completed",
            merchant = "Employer GmbH",
            category = "Income"
        ),
        Transaction(
            id = "tx_rent",
            title = "Rent",
            date = "Yesterday",
            amount = "-€1,250.00",
            status = "Completed",
            merchant = "Landlord",
            category = "Housing"
        ),
        Transaction(
            id = "tx_rewe",
            title = "REWE",
            date = "May 22",
            amount = "-€46.80",
            status = "Completed",
            merchant = "REWE Markt",
            category = "Groceries"
        ),
        Transaction(
            id = "tx_netflix",
            title = "Netflix",
            date = "May 20",
            amount = "-€12.99",
            status = "Completed",
            merchant = "Netflix",
            category = "Subscription"
        )
    )

    override suspend fun getRecentTransactions(): List<Transaction> {
        delay(500)
        return transactions
    }

    override suspend fun getTransactionById(id: String): Transaction? {
        delay(500)
        return transactions.firstOrNull { it.id == id }
    }
}