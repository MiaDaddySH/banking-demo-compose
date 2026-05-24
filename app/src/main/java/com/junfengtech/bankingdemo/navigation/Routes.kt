package com.example.bankingdemo.navigation

object Routes {
    const val Login = "login"
    const val Dashboard = "dashboard"

    const val TransactionDetail = "transaction_detail"
    const val TransactionIdArg = "transactionId"

    const val TransactionDetailRoute = "$TransactionDetail/{$TransactionIdArg}"

    fun transactionDetail(transactionId: String): String {
        return "$TransactionDetail/$transactionId"
    }
}