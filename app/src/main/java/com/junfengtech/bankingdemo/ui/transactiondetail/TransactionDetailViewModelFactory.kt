package com.junfengtech.bankingdemo.ui.transactiondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.junfengtech.bankingdemo.domain.repository.TransactionRepository

class TransactionDetailViewModelFactory(
    private val repository: TransactionRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        if (modelClass.isAssignableFrom(TransactionDetailViewModel::class.java)) {
            val savedStateHandle: SavedStateHandle = extras.createSavedStateHandle()

            return TransactionDetailViewModel(
                savedStateHandle = savedStateHandle,
                repository = repository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}