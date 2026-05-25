package com.junfengtech.bankingdemo.ui.transactiondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingdemo.navigation.Routes
import com.junfengtech.bankingdemo.data.repository.MockTransactionRepository
import com.junfengtech.bankingdemo.domain.model.Transaction
import com.junfengtech.bankingdemo.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TransactionRepository
) : ViewModel() {
    private val transactionId: String =
        savedStateHandle[Routes.TransactionIdArg] ?: ""

    private val _uiState = MutableStateFlow(
        TransactionDetailUiState(
            isLoading = true,
            transactionId = transactionId
        )
    )

    val uiState: StateFlow<TransactionDetailUiState> = _uiState.asStateFlow()

    init {
        loadTransaction()
    }
    private fun loadTransaction() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = ""
                )
            }

            val transaction = repository.getTransactionById(transactionId)

            _uiState.update {
                transaction?.toUiState()
                    ?: it.copy(
                        isLoading = false,
                        errorMessage = "Transaction not found."
                    )
            }
        }
    }

    private fun Transaction.toUiState(): TransactionDetailUiState {
        return TransactionDetailUiState(
            isLoading = false,
            errorMessage = "",
            transactionId = id,
            title = title,
            date = date,
            amount = amount,
            status = status,
            merchant = merchant,
            category = category
        )
    }
}