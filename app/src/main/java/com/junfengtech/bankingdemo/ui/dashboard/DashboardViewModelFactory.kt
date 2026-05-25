package com.junfengtech.bankingdemo.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.junfengtech.bankingdemo.domain.repository.TransactionRepository

class DashboardViewModelFactory(
    private val repository: TransactionRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(
                repository = repository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}