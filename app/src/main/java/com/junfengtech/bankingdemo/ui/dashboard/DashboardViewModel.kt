package com.junfengtech.bankingdemo.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junfengtech.bankingdemo.domain.model.Transaction
import com.junfengtech.bankingdemo.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        DashboardUiState(isLoading = true)
    )
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<DashboardEvent>()
    val event: SharedFlow<DashboardEvent> = _event.asSharedFlow()

    init {
        loadRecentTransactions()
    }

    fun onAction(action: DashboardAction) {
        when (action) {
            DashboardAction.LogoutClicked -> {
                logout()
            }

            is DashboardAction.TransactionClicked -> {
                openTransactionDetail(action.transactionId)
            }
        }
    }
    private fun loadRecentTransactions() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = ""
                )
            }

            val transactions = repository.getRecentTransactions()

            _uiState.update {
                it.copy(
                    isLoading = false,
                    transactions = transactions.map { transaction ->
                        transaction.toUiModel()
                    }
                )
            }
        }
    }
    private fun openTransactionDetail(transactionId: String) {
        viewModelScope.launch {
            _event.emit(
                DashboardEvent.NavigateToTransactionDetail(
                    transactionId = transactionId
                )
            )
        }
    }

    private fun logout() {
        viewModelScope.launch {
            // Later:
            // authRepository.logout()
            // sessionManager.clear()
            _event.emit(DashboardEvent.LoggedOut)
        }
    }

    private fun Transaction.toUiModel(): TransactionUiModel {
        return TransactionUiModel(
            id = id,
            title = title,
            subtitle = date,
            amount = amount
        )
    }
}